package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.ai.GeminiService
import com.example.data.engine.TestBlueprintEngine
import com.example.data.local.ActiveTestSessionEntity
import com.example.data.local.TestHistoryEntity
import com.example.data.local.UserProfileEntity
import com.example.data.model.AppConfig
import com.example.data.model.Chapter
import com.example.data.model.PracticeMiniQuestion
import com.example.data.model.Question
import com.example.data.model.TestConfig
import com.example.data.model.TestResultSummary
import com.example.data.model.Topic
import com.example.data.repository.StudyRepository
import com.example.data.seed.BiharBoardCurriculumData
import com.example.data.seed.QuestionBankData
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class ScreenDestination {
    object Home : ScreenDestination()
    object Subjects : ScreenDestination()
    object Tests : ScreenDestination()
    object Courses : ScreenDestination()
    object Profile : ScreenDestination()

    data class SubjectDetail(val subjectId: String, val selectedSubSubjectId: String? = null) : ScreenDestination()
    data class ChapterDetail(val chapterId: String) : ScreenDestination()
    data class TopicLearning(val chapterId: String, val topicId: String) : ScreenDestination()
    data class ActiveTest(val testConfig: TestConfig) : ScreenDestination()
    data class TestResult(val summary: TestResultSummary) : ScreenDestination()
    data class MistakeAnalysis(val summary: TestResultSummary) : ScreenDestination()
    object DoubtSolver : ScreenDestination()
    object Search : ScreenDestination()
}

class StudyViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = StudyRepository(application)

    // Current navigation destination with custom backstack
    private val navigationStack = mutableListOf<ScreenDestination>(ScreenDestination.Home)
    private val _currentDestination = MutableStateFlow<ScreenDestination>(ScreenDestination.Home)
    val currentDestination: StateFlow<ScreenDestination> = _currentDestination.asStateFlow()

    // Persistent User Profile
    val userProfile: StateFlow<UserProfileEntity> = repository.userProfile
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserProfileEntity())

    // Test History
    val testHistory: StateFlow<List<TestHistoryEntity>> = repository.testHistory
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Active uncompleted test session
    val activeSession: StateFlow<ActiveTestSessionEntity?> = repository.activeSession
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // Completed topics set
    val completedTopics: StateFlow<List<String>> = repository.completedTopics
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Active Test State
    private val _activeTestQuestions = MutableStateFlow<List<Question>>(emptyList())
    val activeTestQuestions: StateFlow<List<Question>> = _activeTestQuestions.asStateFlow()

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex.asStateFlow()

    private val _userAnswers = MutableStateFlow<Map<Int, Int>>(emptyMap())
    val userAnswers: StateFlow<Map<Int, Int>> = _userAnswers.asStateFlow()

    private val _markedQuestions = MutableStateFlow<Set<Int>>(emptySet())
    val markedQuestions: StateFlow<Set<Int>> = _markedQuestions.asStateFlow()

    private val _remainingTimeSeconds = MutableStateFlow(0)
    val remainingTimeSeconds: StateFlow<Int> = _remainingTimeSeconds.asStateFlow()

    private val _activeTestConfig = MutableStateFlow<TestConfig?>(null)
    val activeTestConfig: StateFlow<TestConfig?> = _activeTestConfig.asStateFlow()

    private var timerJob: Job? = null
    private var testStartTime: Long = 0

    // Doubt Solver State
    private val _doubtQuery = MutableStateFlow("")
    val doubtQuery: StateFlow<String> = _doubtQuery.asStateFlow()

    private val _doubtAnswer = MutableStateFlow<String?>(null)
    val doubtAnswer: StateFlow<String?> = _doubtAnswer.asStateFlow()

    private val _isDoubtLoading = MutableStateFlow(false)
    val isDoubtLoading: StateFlow<Boolean> = _isDoubtLoading.asStateFlow()

    // Generated Similar Practice Question in Mistake Analysis
    private val _generatedPracticeQuestion = MutableStateFlow<PracticeMiniQuestion?>(null)
    val generatedPracticeQuestion: StateFlow<PracticeMiniQuestion?> = _generatedPracticeQuestion.asStateFlow()

    private val _isPracticeQuestionLoading = MutableStateFlow(false)
    val isPracticeQuestionLoading: StateFlow<Boolean> = _isPracticeQuestionLoading.asStateFlow()

    // Class/Board Dialog
    private val _showClassBoardDialog = MutableStateFlow(false)
    val showClassBoardDialog: StateFlow<Boolean> = _showClassBoardDialog.asStateFlow()

    // Search Query & Results
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Feedback State
    private val _feedbackSubmitted = MutableStateFlow(false)
    val feedbackSubmitted: StateFlow<Boolean> = _feedbackSubmitted.asStateFlow()

    fun navigateTo(destination: ScreenDestination) {
        if (_currentDestination.value != destination) {
            navigationStack.add(destination)
            _currentDestination.value = destination
        }
    }

    fun navigateBack(): Boolean {
        if (navigationStack.size > 1) {
            navigationStack.removeAt(navigationStack.size - 1)
            _currentDestination.value = navigationStack.last()
            return true
        }
        return false
    }

    fun selectBottomNav(tab: ScreenDestination) {
        navigationStack.clear()
        navigationStack.add(tab)
        _currentDestination.value = tab
    }

    fun openClassBoardDialog() {
        _showClassBoardDialog.value = true
    }

    fun closeClassBoardDialog() {
        _showClassBoardDialog.value = false
    }

    fun updateProfileClassAndBoard(studentName: String, studentClass: String, board: String) {
        viewModelScope.launch {
            val current = userProfile.value
            repository.saveProfile(
                current.copy(
                    studentName = studentName.ifBlank { current.studentName },
                    studentClass = studentClass,
                    board = board
                )
            )
            _showClassBoardDialog.value = false
        }
    }

    fun markTopicCompleted(topicId: String, chapterId: String, subjectId: String) {
        viewModelScope.launch {
            repository.markTopicCompleted(topicId, chapterId, subjectId)
        }
    }

    // === TEST LIFECYCLE ===

    fun startOneClickChapterTest(chapterId: String) {
        val (config, questions) = TestBlueprintEngine.createOneClickChapterTest(chapterId)
        startTestWithData(config, questions)
    }

    fun startDailyTest(subjectId: String = "maths") {
        val (config, questions) = TestBlueprintEngine.createDailyTest(subjectId)
        startTestWithData(config, questions)
    }

    fun startBoardPatternTest(subjectId: String = "maths") {
        val (config, questions) = TestBlueprintEngine.createBoardPatternTest(subjectId)
        startTestWithData(config, questions)
    }

    fun startWeakTopicTest() {
        val pastTests = testHistory.value
        val weakTopics = pastTests.flatMap { it.weakTopicsCsv.split(",") }
            .map { it.trim() }
            .filter { it.isNotBlank() }
            .distinct()

        val (config, questions) = TestBlueprintEngine.createWeakTopicTest(weakTopics)
        startTestWithData(config, questions)
    }

    fun startCustomTest(config: TestConfig) {
        val questions = TestBlueprintEngine.generateTest(config)
        startTestWithData(config, questions)
    }

    private fun startTestWithData(config: TestConfig, questions: List<Question>) {
        _activeTestConfig.value = config
        _activeTestQuestions.value = questions
        _currentQuestionIndex.value = 0
        _userAnswers.value = emptyMap()
        _markedQuestions.value = emptySet()
        _remainingTimeSeconds.value = config.durationMinutes * 60
        _feedbackSubmitted.value = false
        testStartTime = System.currentTimeMillis()

        startTimer()
        navigateTo(ScreenDestination.ActiveTest(config))

        // Trigger local auto-save
        saveTestSessionLocally()
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_remainingTimeSeconds.value > 0) {
                delay(1000)
                _remainingTimeSeconds.value -= 1
                if (_remainingTimeSeconds.value % 15 == 0) {
                    saveTestSessionLocally()
                }
            }
            // Auto submit when timer runs out
            submitCurrentTest(isAutoSubmit = true)
        }
    }

    fun selectOption(questionIndex: Int, optionIndex: Int) {
        val updated = _userAnswers.value.toMutableMap()
        if (updated[questionIndex] == optionIndex) {
            // Unselect if tapped again
            updated.remove(questionIndex)
        } else {
            updated[questionIndex] = optionIndex
        }
        _userAnswers.value = updated
        saveTestSessionLocally()
    }

    fun toggleMarkForReview(questionIndex: Int) {
        val set = _markedQuestions.value.toMutableSet()
        if (set.contains(questionIndex)) {
            set.remove(questionIndex)
        } else {
            set.add(questionIndex)
        }
        _markedQuestions.value = set
        saveTestSessionLocally()
    }

    fun goToQuestion(index: Int) {
        if (index in 0 until _activeTestQuestions.value.size) {
            _currentQuestionIndex.value = index
        }
    }

    fun nextQuestion() {
        if (_currentQuestionIndex.value < _activeTestQuestions.value.size - 1) {
            _currentQuestionIndex.value += 1
        }
    }

    fun previousQuestion() {
        if (_currentQuestionIndex.value > 0) {
            _currentQuestionIndex.value -= 1
        }
    }

    private fun saveTestSessionLocally() {
        val config = _activeTestConfig.value ?: return
        val questions = _activeTestQuestions.value
        if (questions.isEmpty()) return

        viewModelScope.launch {
            repository.saveActiveTestSession(
                testId = config.testId,
                testTitle = config.title,
                subjectId = config.subjectId,
                chapterId = config.chapterId,
                questions = questions,
                userAnswers = _userAnswers.value,
                markedIndices = _markedQuestions.value,
                remainingSeconds = _remainingTimeSeconds.value,
                durationMinutes = config.durationMinutes
            )
        }
    }

    fun resumeActiveSession() {
        val session = activeSession.value ?: return
        val qIds = session.questionIdsCsv.split(",")
        val restoredQuestions = qIds.mapNotNull { qId ->
            val cleanId = qId.substringBefore("_idx_")
            QuestionBankData.questions.find { it.id == cleanId }?.copy(id = qId)
        }.ifEmpty {
            QuestionBankData.getQuestionsBySubject(session.subjectId).take(session.totalQuestions)
        }

        val restoredAnswers = mutableMapOf<Int, Int>()
        if (session.userAnswersCsv.isNotBlank()) {
            session.userAnswersCsv.split(";").forEach { pair ->
                val parts = pair.split(":")
                if (parts.size == 2) {
                    val k = parts[0].toIntOrNull()
                    val v = parts[1].toIntOrNull()
                    if (k != null && v != null) restoredAnswers[k] = v
                }
            }
        }

        val restoredMarked = session.markedIndicesCsv.split(",")
            .mapNotNull { it.toIntOrNull() }.toSet()

        val config = TestConfig(
            testId = session.testId,
            title = session.testTitle,
            subjectId = session.subjectId,
            chapterId = session.chapterId,
            questionCount = restoredQuestions.size,
            durationMinutes = session.durationMinutes,
            testCategory = "Resumed Test"
        )

        _activeTestConfig.value = config
        _activeTestQuestions.value = restoredQuestions
        _currentQuestionIndex.value = 0
        _userAnswers.value = restoredAnswers
        _markedQuestions.value = restoredMarked
        _remainingTimeSeconds.value = session.remainingSeconds.coerceAtLeast(60)
        _feedbackSubmitted.value = false
        testStartTime = System.currentTimeMillis()

        startTimer()
        navigateTo(ScreenDestination.ActiveTest(config))
    }

    fun submitCurrentTest(isAutoSubmit: Boolean = false) {
        timerJob?.cancel()
        val config = _activeTestConfig.value ?: return
        val questions = _activeTestQuestions.value
        val timeSpent = (System.currentTimeMillis() - testStartTime) / 1000

        viewModelScope.launch {
            val summary = repository.recordTestResult(
                testId = config.testId,
                testTitle = config.title,
                subjectId = config.subjectId,
                chapterName = config.title,
                questions = questions,
                userAnswers = _userAnswers.value,
                markedIndices = _markedQuestions.value,
                timeSpentSeconds = timeSpent
            )
            // Navigate to Result screen
            navigationStack.remove(ScreenDestination.ActiveTest(config))
            _currentDestination.value = ScreenDestination.TestResult(summary)
            navigationStack.add(ScreenDestination.TestResult(summary))
        }
    }

    fun submitFeedback(testId: String, rating: String, text: String) {
        viewModelScope.launch {
            repository.saveFeedback(testId, rating, text)
            _feedbackSubmitted.value = true
        }
    }

    // === DOUBT SOLVING ===

    fun updateDoubtQuery(query: String) {
        _doubtQuery.value = query
    }

    fun askDoubt(query: String) {
        if (query.isBlank()) return
        _isDoubtLoading.value = true
        _doubtAnswer.value = null

        viewModelScope.launch {
            val response = GeminiService.solveDoubt(query)
            _doubtAnswer.value = response
            _isDoubtLoading.value = false
        }
    }

    // === GENERATE SIMILAR PRACTICE QUESTION ===

    fun generateSimilarPracticeQuestion(topicName: String, subjectName: String) {
        _isPracticeQuestionLoading.value = true
        _generatedPracticeQuestion.value = null

        viewModelScope.launch {
            val q = GeminiService.generateSimilarPracticeQuestion(topicName, subjectName)
            _generatedPracticeQuestion.value = q
            _isPracticeQuestionLoading.value = false
        }
    }

    // === SEARCH ===

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
}
