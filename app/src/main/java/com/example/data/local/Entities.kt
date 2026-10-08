package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val id: Int = 1,
    val studentName: String = "विद्यार्थी",
    val studentClass: String = "कक्षा 10",
    val board: String = "बिहार बोर्ड (BSEB)",
    val streakDays: Int = 3,
    val totalStudyMinutes: Int = 145,
    val overallProgress: Int = 42
)

@Entity(tableName = "test_history")
data class TestHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val testId: String,
    val testTitle: String,
    val subjectId: String,
    val chapterName: String,
    val totalQuestions: Int,
    val score: Int,
    val correctCount: Int,
    val wrongCount: Int,
    val skippedCount: Int,
    val timeSpentSeconds: Long,
    val timestamp: Long = System.currentTimeMillis(),
    val feedbackRating: String? = null,
    val weakTopicsCsv: String = "",
    val performanceLevel: String = "Strong"
)

@Entity(tableName = "active_test_session")
data class ActiveTestSessionEntity(
    @PrimaryKey val testId: String,
    val testTitle: String,
    val subjectId: String,
    val chapterId: String?,
    val questionIdsCsv: String,
    val userAnswersCsv: String,
    val markedIndicesCsv: String,
    val remainingSeconds: Int,
    val totalQuestions: Int,
    val durationMinutes: Int,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "completed_topics")
data class CompletedTopicEntity(
    @PrimaryKey val topicId: String,
    val chapterId: String,
    val subjectId: String,
    val completedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "bookmarked_questions")
data class BookmarkedQuestionEntity(
    @PrimaryKey val questionId: String,
    val subjectId: String,
    val chapterName: String,
    val questionText: String,
    val explanation: String,
    val savedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "student_feedback")
data class StudentFeedbackEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val testId: String,
    val rating: String,
    val feedbackText: String,
    val timestamp: Long = System.currentTimeMillis()
)
