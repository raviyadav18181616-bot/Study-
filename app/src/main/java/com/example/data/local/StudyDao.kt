package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StudyDao {

    // User Profile
    @Query("SELECT * FROM user_profile WHERE id = 1")
    fun getUserProfile(): Flow<UserProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateProfile(profile: UserProfileEntity)

    // Test History
    @Query("SELECT * FROM test_history ORDER BY timestamp DESC")
    fun getAllTestHistory(): Flow<List<TestHistoryEntity>>

    @Query("SELECT * FROM test_history WHERE subjectId = :subjectId ORDER BY timestamp DESC")
    fun getTestHistoryBySubject(subjectId: String): Flow<List<TestHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTestResult(result: TestHistoryEntity): Long

    @Query("UPDATE test_history SET feedbackRating = :rating WHERE testId = :testId")
    suspend fun updateFeedbackRating(testId: String, rating: String)

    // Active Test Session (Auto-Save & Resume)
    @Query("SELECT * FROM active_test_session LIMIT 1")
    fun getActiveTestSession(): Flow<ActiveTestSessionEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveActiveTestSession(session: ActiveTestSessionEntity)

    @Query("DELETE FROM active_test_session WHERE testId = :testId")
    suspend fun clearActiveTestSession(testId: String)

    @Query("DELETE FROM active_test_session")
    suspend fun clearAllActiveSessions()

    // Completed Topics
    @Query("SELECT * FROM completed_topics")
    fun getAllCompletedTopics(): Flow<List<CompletedTopicEntity>>

    @Query("SELECT topicId FROM completed_topics WHERE chapterId = :chapterId")
    fun getCompletedTopicsByChapter(chapterId: String): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun markTopicCompleted(topic: CompletedTopicEntity)

    // Bookmarks
    @Query("SELECT * FROM bookmarked_questions ORDER BY savedAt DESC")
    fun getAllBookmarks(): Flow<List<BookmarkedQuestionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: BookmarkedQuestionEntity)

    @Query("DELETE FROM bookmarked_questions WHERE questionId = :questionId")
    suspend fun deleteBookmark(questionId: String)

    // Student Feedback
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeedback(feedback: StudentFeedbackEntity)
}
