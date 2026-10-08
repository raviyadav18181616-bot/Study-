package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.filled.TrendingDown
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.TestHistoryEntity
import com.example.data.model.QuestionDifficulty
import com.example.data.model.QuestionType
import com.example.data.model.TestConfig
import com.example.data.seed.BiharBoardCurriculumData
import com.example.ui.ScreenDestination
import com.example.ui.theme.AcademicBlue
import com.example.ui.theme.AcademicBlueDark
import com.example.ui.theme.AcademicBlueLight
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CardBackground
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.HindiColor
import com.example.ui.theme.MathsColor
import com.example.ui.theme.PrimaryNavy
import com.example.ui.theme.SSTColor
import com.example.ui.theme.ScienceColor
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextLightSlate
import com.example.ui.theme.TextMutedNavy
import com.example.ui.theme.WarmBackground
import com.example.ui.theme.WarningAmber
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestCenterScreen(
    testHistory: List<TestHistoryEntity>,
    onStartDailyTest: () -> Unit,
    onStartCustomTest: (TestConfig) -> Unit,
    onStartBoardPatternTest: (String) -> Unit,
    onStartWeakTopicTest: () -> Unit,
    onNavigate: (ScreenDestination) -> Unit
) {
    var showChapterTestConfigSheet by remember { mutableStateOf(false) }
    var selectedFilterSubject by remember { mutableStateOf("all") }

    val filteredHistory = remember(testHistory, selectedFilterSubject) {
        if (selectedFilterSubject == "all") testHistory
        else testHistory.filter { it.subjectId == selectedFilterSubject }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmBackground)
            .padding(16.dp)
            .testTag("test_center_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            // Heading & Subtitle
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Test Center",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = PrimaryNavy
                    )
                )
                Text(
                    text = "अपनी तैयारी को परखें",
                    style = MaterialTheme.typography.bodyLarge.copy(color = TextMutedNavy)
                )
            }
        }

        // ONE PROMINENT CARD: "आज का Test"
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("prominent_daily_test_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = PrimaryNavy),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "आज का Test",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(AcademicBlue)
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "दैनिक टेस्ट",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "10 Questions • 15 Minutes • गणित व विज्ञान",
                        fontSize = 13.sp,
                        color = Color(0xFFCBD5E1)
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = onStartDailyTest,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("start_prominent_daily_test_btn"),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AcademicBlue)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Start Test",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // CLEAN LIST OF TEST CATEGORIES
        item {
            Text(
                text = "TEST CATEGORIES",
                style = MaterialTheme.typography.labelLarge.copy(
                    color = TextLightSlate,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            )
        }

        item {
            TestCategoryRowItem(
                title = "Chapter Test",
                subtitle = "अध्याय चुनकर त्वरित टेस्ट दें",
                icon = Icons.Default.Quiz,
                color = AcademicBlue,
                tag = "chapter_test",
                onClick = { showChapterTestConfigSheet = true }
            )
        }

        item {
            TestCategoryRowItem(
                title = "Subject Test",
                subtitle = "पूरे गणित/विज्ञान सिलेबस से संतुलित अभ्यास",
                icon = Icons.Default.Calculate,
                color = MathsColor,
                tag = "subject_test",
                onClick = {
                    onStartCustomTest(
                        TestConfig(
                            testId = UUID.randomUUID().toString(),
                            title = "पूरे गणित सिलेबस का टेस्ट",
                            subjectId = "maths",
                            questionCount = 20,
                            durationMinutes = 20,
                            testCategory = "Subject Test"
                        )
                    )
                }
            )
        }

        item {
            TestCategoryRowItem(
                title = "Board Pattern",
                subtitle = "बिहार बोर्ड 10th परीक्षा पैटर्न के अनुसार अभ्यास",
                icon = Icons.AutoMirrored.Filled.Assignment,
                color = SSTColor,
                tag = "board_pattern_test",
                onClick = { onStartBoardPatternTest("maths") }
            )
        }

        item {
            TestCategoryRowItem(
                title = "Weak Topic Test",
                subtitle = "आपकी पिछली गलतियों के आधार पर विशेष सुधार",
                icon = Icons.AutoMirrored.Filled.TrendingDown,
                color = ErrorRed,
                tag = "weak_topic_test",
                onClick = onStartWeakTopicTest
            )
        }

        item {
            TestCategoryRowItem(
                title = "Revision Test",
                subtitle = "पिछले 7 दिनों की पढ़ाई का त्वरित स्मरण",
                icon = Icons.Default.Repeat,
                color = ScienceColor,
                tag = "revision_test",
                onClick = {
                    onStartCustomTest(
                        TestConfig(
                            testId = UUID.randomUUID().toString(),
                            title = "7-दिवसीय रिवीजन टेस्ट",
                            subjectId = "all",
                            questionCount = 10,
                            durationMinutes = 12,
                            testCategory = "Revision Test"
                        )
                    )
                }
            )
        }

        item {
            TestCategoryRowItem(
                title = "Full Syllabus Mock",
                subtitle = "सम्पूर्ण पाठ्यक्रम आधारित मॉडल पेपर",
                icon = Icons.Default.School,
                color = PrimaryNavy,
                tag = "full_syllabus_test",
                onClick = {
                    onStartCustomTest(
                        TestConfig(
                            testId = UUID.randomUUID().toString(),
                            title = "सम्पूर्ण पाठ्यक्रम मॉडल टेस्ट",
                            subjectId = "all",
                            questionCount = 30,
                            durationMinutes = 30,
                            testCategory = "Full Syllabus"
                        )
                    )
                }
            )
        }

        // PAST TESTS HISTORY SECTION
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "मेरे Tests (History)",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = PrimaryNavy
                    )
                )
                Text(
                    text = "${testHistory.size} टेस्ट दिए",
                    fontSize = 12.sp,
                    color = TextLightSlate
                )
            }
        }

        // Filter chips for history
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                val filters = listOf(
                    "all" to "सभी",
                    "maths" to "गणित",
                    "science" to "विज्ञान",
                    "sst" to "सामाजिक",
                    "hindi" to "हिंदी"
                )
                items(filters) { (id, label) ->
                    val isSelected = selectedFilterSubject == id
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedFilterSubject = id },
                        label = { Text(label) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = AcademicBlue,
                            selectedLabelColor = Color.White
                        ),
                        modifier = Modifier.testTag("history_filter_$id")
                    )
                }
            }
        }

        if (filteredHistory.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = CardBackground),
                    border = CardDefaults.outlinedCardBorder()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.History,
                            contentDescription = null,
                            tint = TextLightSlate,
                            modifier = Modifier.size(36.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "अभी तक कोई टेस्ट नहीं दिया गया है।",
                            style = MaterialTheme.typography.bodyMedium.copy(color = TextMutedNavy)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "आज का टेस्ट देकर अपनी तैयारी की शुरुआत करें!",
                            fontSize = 12.sp,
                            color = TextLightSlate
                        )
                    }
                }
            }
        } else {
            items(filteredHistory) { hist ->
                TestHistoryCard(item = hist)
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    // CHAPTER TEST CONFIGURATION MODAL BOTTOM SHEET
    if (showChapterTestConfigSheet) {
        ChapterTestConfigBottomSheet(
            onDismiss = { showChapterTestConfigSheet = false },
            onStart = { config ->
                showChapterTestConfigSheet = false
                onStartCustomTest(config)
            }
        )
    }
}

@Composable
private fun TestCategoryRowItem(
    title: String,
    subtitle: String,
    icon: ImageVector,
    color: Color,
    tag: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("test_category_$tag"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(color.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = color,
                        modifier = Modifier.size(22.dp)
                    )
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = PrimaryNavy
                        )
                    )
                    Text(
                        text = subtitle,
                        fontSize = 12.sp,
                        color = TextLightSlate
                    )
                }
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = TextLightSlate,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
private fun TestHistoryCard(item: TestHistoryEntity) {
    val dateStr = remember(item.timestamp) {
        val sdf = SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault())
        sdf.format(Date(item.timestamp))
    }

    val accuracy = if (item.totalQuestions > 0) ((item.correctCount.toFloat() / item.totalQuestions) * 100).toInt() else 0

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("history_item_${item.testId}"),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.testTitle,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = PrimaryNavy
                    )
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "$dateStr • ${item.timeSpentSeconds / 60} मिनट",
                    fontSize = 11.sp,
                    color = TextLightSlate
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${item.score} / ${item.totalQuestions}",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = if (accuracy >= 70) SuccessGreen else WarningAmber
                    )
                )
                Text(
                    text = "शुद्धता: $accuracy%",
                    fontSize = 11.sp,
                    color = TextMutedNavy
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChapterTestConfigBottomSheet(
    onDismiss: () -> Unit,
    onStart: (TestConfig) -> Unit
) {
    var selectedSubjectId by remember { mutableStateOf("maths") }
    val chapters = remember(selectedSubjectId) {
        BiharBoardCurriculumData.getAllChaptersForSubject(selectedSubjectId)
    }
    var selectedChapterId by remember { mutableStateOf(chapters.firstOrNull()?.id ?: "m_01") }
    var questionCount by remember { mutableIntStateOf(20) } // Default 20 Questions
    var selectedDifficulty by remember { mutableStateOf(QuestionDifficulty.MIXED) } // Default Mixed
    var selectedType by remember { mutableStateOf(QuestionType.MIXED) } // Default Mixed

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = "Test तैयार है",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = PrimaryNavy
                )
            )

            // 1. Choose Subject
            Text(text = "1. विषय चुनें", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(
                    "maths" to "गणित",
                    "science" to "विज्ञान",
                    "sst" to "सामाजिक",
                    "hindi" to "हिंदी"
                ).forEach { (sId, name) ->
                    val isSel = selectedSubjectId == sId
                    FilterChip(
                        selected = isSel,
                        onClick = {
                            selectedSubjectId = sId
                            selectedChapterId = BiharBoardCurriculumData.getAllChaptersForSubject(sId).firstOrNull()?.id ?: ""
                        },
                        label = { Text(name) }
                    )
                }
            }

            // 2. Choose Chapter
            Text(text = "2. अध्याय चुनें", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(chapters.take(6)) { ch ->
                    val isSel = selectedChapterId == ch.id
                    FilterChip(
                        selected = isSel,
                        onClick = { selectedChapterId = ch.id },
                        label = { Text(ch.titleHindi) }
                    )
                }
            }

            // 3. Question Count: 10, 20, 30
            Text(text = "3. प्रश्नों की संख्या", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(10, 20, 30).forEach { count ->
                    val isSel = questionCount == count
                    FilterChip(
                        selected = isSel,
                        onClick = { questionCount = count },
                        label = { Text("$count प्रश्न") }
                    )
                }
            }

            // 4. Difficulty: Easy, Medium, Hard, Mixed (Default Mixed)
            Text(text = "4. कठिनाई स्तर", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(
                    QuestionDifficulty.EASY to "सरल",
                    QuestionDifficulty.MEDIUM to "मध्यम",
                    QuestionDifficulty.HARD to "कठिन",
                    QuestionDifficulty.MIXED to "मिश्रित (डिफ़ॉल्ट)"
                ).forEach { (diff, label) ->
                    val isSel = selectedDifficulty == diff
                    FilterChip(
                        selected = isSel,
                        onClick = { selectedDifficulty = diff },
                        label = { Text(label) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {
                    val selChapter = chapters.find { it.id == selectedChapterId }
                    onStart(
                        TestConfig(
                            testId = UUID.randomUUID().toString(),
                            title = "${selChapter?.titleHindi ?: "अध्याय"} - टेस्ट",
                            subjectId = selectedSubjectId,
                            chapterId = selectedChapterId,
                            questionCount = questionCount,
                            durationMinutes = (questionCount * 1.2).toInt().coerceAtLeast(10),
                            difficulty = selectedDifficulty,
                            questionType = selectedType,
                            testCategory = "Chapter Test"
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("start_configured_test_button"),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AcademicBlue)
            ) {
                Text(
                    text = "Test शुरू करें",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
