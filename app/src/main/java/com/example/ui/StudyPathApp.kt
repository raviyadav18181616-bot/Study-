package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.model.AppConfig
import com.example.ui.components.StudyBottomNavigationBar
import com.example.ui.components.StudyTopAppBar
import com.example.ui.screens.ActiveTestScreen
import com.example.ui.screens.ChapterDetailScreen
import com.example.ui.screens.ClassBoardSelectionDialog
import com.example.ui.screens.CoursesScreen
import com.example.ui.screens.DoubtSolverScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.MistakeAnalysisScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.SearchScreen
import com.example.ui.screens.SubjectDetailScreen
import com.example.ui.screens.SubjectsScreen
import com.example.ui.screens.TestCenterScreen
import com.example.ui.screens.TestResultScreen
import com.example.ui.screens.TopicLearningScreen

@Composable
fun StudyPathApp(
    viewModel: StudyViewModel = viewModel()
) {
    val currentDestination by viewModel.currentDestination.collectAsStateWithLifecycle()
    val userProfile by viewModel.userProfile.collectAsStateWithLifecycle()
    val activeSession by viewModel.activeSession.collectAsStateWithLifecycle()
    val testHistory by viewModel.testHistory.collectAsStateWithLifecycle()
    val completedTopics by viewModel.completedTopics.collectAsStateWithLifecycle()

    val activeTestQuestions by viewModel.activeTestQuestions.collectAsStateWithLifecycle()
    val currentQuestionIndex by viewModel.currentQuestionIndex.collectAsStateWithLifecycle()
    val userAnswers by viewModel.userAnswers.collectAsStateWithLifecycle()
    val markedQuestions by viewModel.markedQuestions.collectAsStateWithLifecycle()
    val remainingSeconds by viewModel.remainingTimeSeconds.collectAsStateWithLifecycle()
    val activeTestConfig by viewModel.activeTestConfig.collectAsStateWithLifecycle()

    val doubtQuery by viewModel.doubtQuery.collectAsStateWithLifecycle()
    val doubtAnswer by viewModel.doubtAnswer.collectAsStateWithLifecycle()
    val isDoubtLoading by viewModel.isDoubtLoading.collectAsStateWithLifecycle()

    val generatedPracticeQuestion by viewModel.generatedPracticeQuestion.collectAsStateWithLifecycle()
    val isPracticeQuestionLoading by viewModel.isPracticeQuestionLoading.collectAsStateWithLifecycle()

    val showClassBoardDialog by viewModel.showClassBoardDialog.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val feedbackSubmitted by viewModel.feedbackSubmitted.collectAsStateWithLifecycle()

    // Handle back button on sub-screens
    val isRootDestination = currentDestination is ScreenDestination.Home ||
            currentDestination is ScreenDestination.Subjects ||
            currentDestination is ScreenDestination.Tests ||
            currentDestination is ScreenDestination.Courses ||
            currentDestination is ScreenDestination.Profile

    val isExamScreen = currentDestination is ScreenDestination.ActiveTest

    BackHandler(enabled = !isRootDestination && !isExamScreen) {
        viewModel.navigateBack()
    }

    Scaffold(
        topBar = {
            if (!isExamScreen) {
                StudyTopAppBar(
                    title = AppConfig.BRAND_NAME,
                    currentClass = userProfile.studentClass,
                    currentBoard = userProfile.board.replace(" (BSEB)", ""),
                    showBack = !isRootDestination,
                    onBackClick = { viewModel.navigateBack() },
                    onClassBoardClick = { viewModel.openClassBoardDialog() },
                    onSearchClick = { viewModel.navigateTo(ScreenDestination.Search) },
                    onDoubtClick = { viewModel.navigateTo(ScreenDestination.DoubtSolver) }
                )
            }
        },
        bottomBar = {
            if (!isExamScreen && (isRootDestination || currentDestination is ScreenDestination.Search || currentDestination is ScreenDestination.DoubtSolver)) {
                StudyBottomNavigationBar(
                    currentDestination = currentDestination,
                    onSelectTab = { viewModel.selectBottomNav(it) }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (val dest = currentDestination) {
                is ScreenDestination.Home -> {
                    HomeScreen(
                        userProfile = userProfile,
                        activeSession = activeSession,
                        onNavigate = { viewModel.navigateTo(it) },
                        onStartDailyTest = { viewModel.startDailyTest() },
                        onResumeActiveTest = { viewModel.resumeActiveSession() }
                    )
                }
                is ScreenDestination.Subjects -> {
                    SubjectsScreen(
                        onNavigate = { viewModel.navigateTo(it) }
                    )
                }
                is ScreenDestination.Tests -> {
                    TestCenterScreen(
                        testHistory = testHistory,
                        onStartDailyTest = { viewModel.startDailyTest() },
                        onStartCustomTest = { viewModel.startCustomTest(it) },
                        onStartBoardPatternTest = { viewModel.startBoardPatternTest(it) },
                        onStartWeakTopicTest = { viewModel.startWeakTopicTest() },
                        onNavigate = { viewModel.navigateTo(it) }
                    )
                }
                is ScreenDestination.Courses -> {
                    CoursesScreen(
                        onNavigate = { viewModel.navigateTo(it) }
                    )
                }
                is ScreenDestination.Profile -> {
                    ProfileScreen(
                        userProfile = userProfile,
                        testHistory = testHistory,
                        onOpenClassBoardDialog = { viewModel.openClassBoardDialog() },
                        onSaveName = { newName ->
                            viewModel.updateProfileClassAndBoard(newName, userProfile.studentClass, userProfile.board)
                        }
                    )
                }
                is ScreenDestination.SubjectDetail -> {
                    SubjectDetailScreen(
                        subjectId = dest.subjectId,
                        initialSubSubjectId = dest.selectedSubSubjectId,
                        onNavigate = { viewModel.navigateTo(it) },
                        onStartOneClickTest = { chapterId ->
                            viewModel.startOneClickChapterTest(chapterId)
                        }
                    )
                }
                is ScreenDestination.ChapterDetail -> {
                    ChapterDetailScreen(
                        chapterId = dest.chapterId,
                        completedTopics = completedTopics,
                        onNavigate = { viewModel.navigateTo(it) },
                        onStartTest = { chapterId ->
                            viewModel.startOneClickChapterTest(chapterId)
                        }
                    )
                }
                is ScreenDestination.TopicLearning -> {
                    TopicLearningScreen(
                        chapterId = dest.chapterId,
                        topicId = dest.topicId,
                        onNextTopic = { viewModel.navigateBack() },
                        onMarkCompleted = {
                            viewModel.markTopicCompleted(dest.topicId, dest.chapterId, "maths")
                        }
                    )
                }
                is ScreenDestination.ActiveTest -> {
                    val config = activeTestConfig ?: dest.testConfig
                    ActiveTestScreen(
                        testConfig = config,
                        questions = activeTestQuestions,
                        currentIndex = currentQuestionIndex,
                        userAnswers = userAnswers,
                        markedIndices = markedQuestions,
                        remainingSeconds = remainingSeconds,
                        onSelectOption = { qIdx, optIdx -> viewModel.selectOption(qIdx, optIdx) },
                        onToggleMark = { qIdx -> viewModel.toggleMarkForReview(qIdx) },
                        onGoToQuestion = { qIdx -> viewModel.goToQuestion(qIdx) },
                        onNextQuestion = { viewModel.nextQuestion() },
                        onPreviousQuestion = { viewModel.previousQuestion() },
                        onSubmitTest = { viewModel.submitCurrentTest() },
                        onNavigateBack = { viewModel.navigateBack() }
                    )
                }
                is ScreenDestination.TestResult -> {
                    TestResultScreen(
                        summary = dest.summary,
                        feedbackSubmitted = feedbackSubmitted,
                        onViewMistakes = {
                            viewModel.navigateTo(ScreenDestination.MistakeAnalysis(dest.summary))
                        },
                        onSubmitFeedback = { rating, text ->
                            viewModel.submitFeedback(dest.summary.testId, rating, text)
                        },
                        onStartRecommendedTest = { topic ->
                            viewModel.startWeakTopicTest()
                        },
                        onBackToTestCenter = {
                            viewModel.selectBottomNav(ScreenDestination.Tests)
                        }
                    )
                }
                is ScreenDestination.MistakeAnalysis -> {
                    MistakeAnalysisScreen(
                        summary = dest.summary,
                        generatedPracticeQuestion = generatedPracticeQuestion,
                        isPracticeQuestionLoading = isPracticeQuestionLoading,
                        onGenerateSimilarQuestion = { topic, subject ->
                            viewModel.generateSimilarPracticeQuestion(topic, subject)
                        },
                        onBack = { viewModel.navigateBack() }
                    )
                }
                is ScreenDestination.DoubtSolver -> {
                    DoubtSolverScreen(
                        doubtQuery = doubtQuery,
                        doubtAnswer = doubtAnswer,
                        isLoading = isDoubtLoading,
                        onQueryChange = { viewModel.updateDoubtQuery(it) },
                        onAskDoubt = { viewModel.askDoubt(it) }
                    )
                }
                is ScreenDestination.Search -> {
                    SearchScreen(
                        searchQuery = searchQuery,
                        onQueryChange = { viewModel.updateSearchQuery(it) },
                        onNavigate = { viewModel.navigateTo(it) }
                    )
                }
            }
        }

        // Class & Board Selection Dialog
        if (showClassBoardDialog) {
            ClassBoardSelectionDialog(
                currentClass = userProfile.studentClass,
                currentBoard = userProfile.board,
                studentName = userProfile.studentName,
                onDismiss = { viewModel.closeClassBoardDialog() },
                onSave = { name, cls, brd ->
                    viewModel.updateProfileClassAndBoard(name, cls, brd)
                }
            )
        }
    }
}
