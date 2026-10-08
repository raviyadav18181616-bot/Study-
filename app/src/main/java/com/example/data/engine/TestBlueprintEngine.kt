package com.example.data.engine

import com.example.data.model.Question
import com.example.data.model.QuestionDifficulty
import com.example.data.model.QuestionType
import com.example.data.model.TestConfig
import com.example.data.seed.BiharBoardCurriculumData
import com.example.data.seed.QuestionBankData
import java.util.UUID

object TestBlueprintEngine {

    fun generateTest(config: TestConfig): List<Question> {
        val pool = when {
            config.chapterId != null -> {
                QuestionBankData.getQuestionsByChapter(config.chapterId)
            }
            config.subjectId.isNotBlank() && config.subjectId != "all" -> {
                QuestionBankData.getQuestionsBySubject(config.subjectId)
            }
            else -> {
                QuestionBankData.questions
            }
        }

        if (pool.isEmpty()) {
            return QuestionBankData.questions.take(config.questionCount)
        }

        // Filter or balance by difficulty
        val filtered = when (config.difficulty) {
            QuestionDifficulty.EASY -> pool.filter { it.difficulty == QuestionDifficulty.EASY }
            QuestionDifficulty.HARD -> pool.filter { it.difficulty == QuestionDifficulty.HARD }
            QuestionDifficulty.MEDIUM -> pool.filter { it.difficulty == QuestionDifficulty.MEDIUM }
            QuestionDifficulty.MIXED -> pool
        }.ifEmpty { pool }

        // Distinct by concept / topic to avoid repetition
        val distinctByTopic = filtered.distinctBy { it.topicName }

        val resultList = mutableListOf<Question>()
        resultList.addAll(distinctByTopic)

        // If not enough questions, fill from remaining pool
        if (resultList.size < config.questionCount) {
            val remaining = filtered.filterNot { it in resultList }
            resultList.addAll(remaining)
        }

        // If still smaller than target, duplicate with shuffle or return all available
        val finalSelection = if (resultList.size >= config.questionCount) {
            resultList.shuffled().take(config.questionCount)
        } else {
            resultList.shuffled()
        }

        return finalSelection.mapIndexed { index, q ->
            q.copy(id = "${q.id}_idx_${index}")
        }
    }

    fun createOneClickChapterTest(chapterId: String): Pair<TestConfig, List<Question>> {
        val chapter = BiharBoardCurriculumData.getChapterById(chapterId)
        val chapterTitle = chapter?.titleHindi ?: "अध्याय"
        val subjectId = chapter?.subjectId ?: "maths"

        val config = TestConfig(
            testId = UUID.randomUUID().toString(),
            title = "$chapterTitle - अध्याय टेस्ट",
            subjectId = subjectId,
            chapterId = chapterId,
            questionCount = 10,
            durationMinutes = 15,
            difficulty = QuestionDifficulty.MIXED,
            questionType = QuestionType.OBJECTIVE,
            testCategory = "Chapter Test"
        )
        val questions = generateTest(config)
        return Pair(config, questions)
    }

    fun createDailyTest(subjectId: String = "maths"): Pair<TestConfig, List<Question>> {
        val subjectName = when (subjectId) {
            "maths" -> "गणित"
            "science" -> "विज्ञान"
            "sst" -> "सामाजिक विज्ञान"
            "hindi" -> "हिंदी"
            else -> "दैनिक"
        }
        val config = TestConfig(
            testId = UUID.randomUUID().toString(),
            title = "आज का $subjectName Test",
            subjectId = subjectId,
            questionCount = 10,
            durationMinutes = 10,
            difficulty = QuestionDifficulty.MIXED,
            questionType = QuestionType.OBJECTIVE,
            testCategory = "Daily Test"
        )
        val questions = generateTest(config)
        return Pair(config, questions)
    }

    fun createWeakTopicTest(weakTopics: List<String>, subjectId: String = "maths"): Pair<TestConfig, List<Question>> {
        val matchingQuestions = QuestionBankData.questions.filter { q ->
            weakTopics.any { wt -> q.topicName.contains(wt, ignoreCase = true) || q.chapterName.contains(wt, ignoreCase = true) }
        }.ifEmpty {
            QuestionBankData.getQuestionsBySubject(subjectId)
        }

        val config = TestConfig(
            testId = UUID.randomUUID().toString(),
            title = "कमजोर विषयों का सुधार टेस्ट",
            subjectId = subjectId,
            questionCount = minOf(10, matchingQuestions.size.coerceAtLeast(5)),
            durationMinutes = 12,
            difficulty = QuestionDifficulty.MEDIUM,
            questionType = QuestionType.OBJECTIVE,
            testCategory = "Weak Topic"
        )
        val questions = matchingQuestions.shuffled().take(config.questionCount)
        return Pair(config, questions)
    }

    fun createBoardPatternTest(subjectId: String = "maths"): Pair<TestConfig, List<Question>> {
        val subjectName = when (subjectId) {
            "maths" -> "गणित"
            "science" -> "विज्ञान"
            "sst" -> "सामाजिक विज्ञान"
            "hindi" -> "हिंदी"
            else -> "बोर्ड पैटर्न"
        }
        val config = TestConfig(
            testId = UUID.randomUUID().toString(),
            title = "बिहार बोर्ड पैटर्न - $subjectName मॉक टेस्ट",
            subjectId = subjectId,
            questionCount = 20,
            durationMinutes = 20,
            difficulty = QuestionDifficulty.MIXED,
            questionType = QuestionType.MIXED,
            testCategory = "Board Pattern"
        )
        val questions = generateTest(config)
        return Pair(config, questions)
    }
}
