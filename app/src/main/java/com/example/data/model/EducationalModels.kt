package com.example.data.model

object AppConfig {
    const val BRAND_NAME = "StudyPath"
    const val DEFAULT_CLASS = "कक्षा 10"
    const val DEFAULT_BOARD = "बिहार बोर्ड (BSEB)"
    const val DEFAULT_STUDENT_NAME = "विद्यार्थी"
}

enum class EducationBoard(val displayName: String, val isAvailable: Boolean) {
    BIHAR_BOARD("बिहार बोर्ड (BSEB)", true),
    CBSE("CBSE Board", false),
    UP_BOARD("UP Board", false),
    ICSE("ICSE Board", false)
}

enum class SchoolClass(val displayName: String, val isAvailable: Boolean) {
    CLASS_6("कक्षा 6", false),
    CLASS_7("कक्षा 7", false),
    CLASS_8("कक्षा 8", false),
    CLASS_9("कक्षा 9", false),
    CLASS_10("कक्षा 10", true),
    CLASS_11("कक्षा 11", false),
    CLASS_12("कक्षा 12", false)
}

enum class SubjectCategory(val id: String, val titleHindi: String, val subTitleHindi: String) {
    MATHS("maths", "गणित", "वास्तविक संख्याएं, बीजगणित, त्रिकोणमिति, ज्यामिति"),
    SCIENCE("science", "विज्ञान", "भौतिक विज्ञान, रसायन विज्ञान, जीव विज्ञान"),
    SST("sst", "सामाजिक विज्ञान", "इतिहास, भूगोल, राजनीति, अर्थशास्त्र, आपदा प्रबंधन"),
    HINDI("hindi", "हिंदी", "गोधूलि, वर्णिका, व्याकरण, रचना")
}

data class SubSubject(
    val id: String,
    val nameHindi: String,
    val nameEnglish: String,
    val chaptersCount: Int
)

data class Topic(
    val id: String,
    val title: String,
    val shortSummary: String,
    val explanation: String,
    val formulaOrKeyPoint: String? = null,
    val example: String? = null,
    val practiceQuestion: PracticeMiniQuestion? = null,
    val isCompleted: Boolean = false
)

data class PracticeMiniQuestion(
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

data class Chapter(
    val id: String,
    val number: Int,
    val subjectId: String,
    val subSubjectId: String? = null,
    val titleHindi: String,
    val titleEnglish: String,
    val topicsCount: Int,
    val completedTopicsCount: Int = 0,
    val topics: List<Topic> = emptyList(),
    val revisionSummary: String = "",
    val importantFormulas: List<String> = emptyList()
) {
    val progressPercent: Int
        get() = if (topicsCount > 0) ((completedTopicsCount.toFloat() / topicsCount) * 100).toInt() else 0
}

enum class QuestionDifficulty {
    EASY, MEDIUM, HARD, MIXED
}

enum class QuestionType {
    OBJECTIVE, SUBJECTIVE, NUMERICAL, MIXED
}

data class Question(
    val id: String,
    val subjectId: String,
    val subSubjectId: String? = null,
    val chapterId: String,
    val chapterName: String,
    val topicName: String,
    val questionText: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
    val difficulty: QuestionDifficulty = QuestionDifficulty.MEDIUM,
    val questionType: QuestionType = QuestionType.OBJECTIVE,
    val marks: Int = 1,
    val sourcePattern: String = "बिहार बोर्ड पैटर्न अभ्यास"
)

data class TestConfig(
    val testId: String,
    val title: String,
    val subjectId: String,
    val chapterId: String? = null,
    val questionCount: Int = 20,
    val durationMinutes: Int = 20,
    val difficulty: QuestionDifficulty = QuestionDifficulty.MIXED,
    val questionType: QuestionType = QuestionType.MIXED,
    val testCategory: String = "Chapter Test"
)

data class TestQuestionState(
    val question: Question,
    val selectedOptionIndex: Int? = null,
    val isMarkedForReview: Boolean = false
)

data class TestResultSummary(
    val testId: String,
    val testTitle: String,
    val subjectId: String,
    val chapterName: String,
    val totalQuestions: Int,
    val correctCount: Int,
    val wrongCount: Int,
    val skippedCount: Int,
    val score: Int,
    val percentage: Int,
    val timeSpentSeconds: Long,
    val timestamp: Long,
    val weakTopics: List<String>,
    val performanceLevel: String, // "Strong", "Needs Improvement", "Weak"
    val questionStates: List<TestQuestionState>
)

data class Course(
    val id: String,
    val title: String,
    val subject: String,
    val instructor: String,
    val description: String,
    val totalLessons: Int,
    val completedLessons: Int,
    val highlights: List<String>,
    val isFree: Boolean = true
)
