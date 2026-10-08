package com.example.data.repository

import android.content.Context
import com.example.data.local.ActiveTestSessionEntity
import com.example.data.local.AppDatabase
import com.example.data.local.BookmarkedQuestionEntity
import com.example.data.local.CompletedTopicEntity
import com.example.data.local.StudentFeedbackEntity
import com.example.data.local.TestHistoryEntity
import com.example.data.local.UserProfileEntity
import com.example.data.model.Question
import com.example.data.model.TestQuestionState
import com.example.data.model.TestResultSummary
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StudyRepository(context: Context) {

    private val db = AppDatabase.getDatabase(context)
    private val dao = db.studyDao()

    val userProfile: Flow<UserProfileEntity> = dao.getUserProfile().map {
        it ?: UserProfileEntity()
    }

    val testHistory: Flow<List<TestHistoryEntity>> = dao.getAllTestHistory()

    val activeSession: Flow<ActiveTestSessionEntity?> = dao.getActiveTestSession()

    val completedTopics: Flow<List<String>> = dao.getAllCompletedTopics().map { list ->
        list.map { it.topicId }
    }

    val bookmarkedQuestions: Flow<List<BookmarkedQuestionEntity>> = dao.getAllBookmarks()

    suspend fun saveProfile(profile: UserProfileEntity) {
        dao.insertOrUpdateProfile(profile)
    }

    suspend fun markTopicCompleted(topicId: String, chapterId: String, subjectId: String) {
        dao.markTopicCompleted(
            CompletedTopicEntity(
                topicId = topicId,
                chapterId = chapterId,
                subjectId = subjectId
            )
        )
    }

    suspend fun saveActiveTestSession(
        testId: String,
        testTitle: String,
        subjectId: String,
        chapterId: String?,
        questions: List<Question>,
        userAnswers: Map<Int, Int>,
        markedIndices: Set<Int>,
        remainingSeconds: Int,
        durationMinutes: Int
    ) {
        val qIds = questions.joinToString(",") { it.id }
        val answersStr = userAnswers.entries.joinToString(";") { "${it.key}:${it.value}" }
        val markedStr = markedIndices.joinToString(",")

        dao.saveActiveTestSession(
            ActiveTestSessionEntity(
                testId = testId,
                testTitle = testTitle,
                subjectId = subjectId,
                chapterId = chapterId,
                questionIdsCsv = qIds,
                userAnswersCsv = answersStr,
                markedIndicesCsv = markedStr,
                remainingSeconds = remainingSeconds,
                totalQuestions = questions.size,
                durationMinutes = durationMinutes
            )
        )
    }

    suspend fun clearActiveTestSession(testId: String) {
        dao.clearActiveTestSession(testId)
    }

    suspend fun recordTestResult(
        testId: String,
        testTitle: String,
        subjectId: String,
        chapterName: String,
        questions: List<Question>,
        userAnswers: Map<Int, Int>,
        markedIndices: Set<Int>,
        timeSpentSeconds: Long
    ): TestResultSummary {
        var correct = 0
        var wrong = 0
        var skipped = 0
        val weakTopics = mutableSetOf<String>()

        val questionStates = questions.mapIndexed { index, q ->
            val selected = userAnswers[index]
            val isMarked = markedIndices.contains(index)
            if (selected == null) {
                skipped++
                weakTopics.add(q.topicName)
            } else if (selected == q.correctIndex) {
                correct++
            } else {
                wrong++
                weakTopics.add(q.topicName)
            }
            TestQuestionState(
                question = q,
                selectedOptionIndex = selected,
                isMarkedForReview = isMarked
            )
        }

        val total = questions.size
        val score = correct
        val percentage = if (total > 0) ((correct.toFloat() / total) * 100).toInt() else 0

        val performanceLevel = when {
            percentage >= 80 -> "Strong"
            percentage >= 50 -> "Needs Improvement"
            else -> "Weak"
        }

        val resultEntity = TestHistoryEntity(
            testId = testId,
            testTitle = testTitle,
            subjectId = subjectId,
            chapterName = chapterName,
            totalQuestions = total,
            score = score,
            correctCount = correct,
            wrongCount = wrong,
            skippedCount = skipped,
            timeSpentSeconds = timeSpentSeconds,
            weakTopicsCsv = weakTopics.joinToString(", "),
            performanceLevel = performanceLevel
        )

        dao.insertTestResult(resultEntity)
        dao.clearActiveTestSession(testId)

        return TestResultSummary(
            testId = testId,
            testTitle = testTitle,
            subjectId = subjectId,
            chapterName = chapterName,
            totalQuestions = total,
            correctCount = correct,
            wrongCount = wrong,
            skippedCount = skipped,
            score = score,
            percentage = percentage,
            timeSpentSeconds = timeSpentSeconds,
            timestamp = System.currentTimeMillis(),
            weakTopics = weakTopics.toList(),
            performanceLevel = performanceLevel,
            questionStates = questionStates
        )
    }

    suspend fun saveFeedback(testId: String, rating: String, text: String) {
        dao.insertFeedback(
            StudentFeedbackEntity(
                testId = testId,
                rating = rating,
                feedbackText = text
            )
        )
        dao.updateFeedbackRating(testId, rating)
    }

    suspend fun bookmarkQuestion(question: Question) {
        dao.insertBookmark(
            BookmarkedQuestionEntity(
                questionId = question.id,
                subjectId = question.subjectId,
                chapterName = question.chapterName,
                questionText = question.questionText,
                explanation = question.explanation
            )
        )
    }

    suspend fun removeBookmark(questionId: String) {
        dao.deleteBookmark(questionId)
    }
}
