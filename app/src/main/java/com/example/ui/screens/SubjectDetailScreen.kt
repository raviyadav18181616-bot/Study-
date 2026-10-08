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
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Chapter
import com.example.data.seed.BiharBoardCurriculumData
import com.example.ui.ScreenDestination
import com.example.ui.components.CustomProgressBar
import com.example.ui.theme.AcademicBlue
import com.example.ui.theme.AcademicBlueLight
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CardBackground
import com.example.ui.theme.HindiColor
import com.example.ui.theme.MathsColor
import com.example.ui.theme.PrimaryNavy
import com.example.ui.theme.SSTColor
import com.example.ui.theme.ScienceColor
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.SurfaceVariant
import com.example.ui.theme.TextLightSlate
import com.example.ui.theme.TextMutedNavy
import com.example.ui.theme.WarmBackground

@Composable
fun SubjectDetailScreen(
    subjectId: String,
    initialSubSubjectId: String? = null,
    onNavigate: (ScreenDestination) -> Unit,
    onStartOneClickTest: (String) -> Unit
) {
    val allChapters = remember(subjectId) {
        BiharBoardCurriculumData.getAllChaptersForSubject(subjectId)
    }

    val subjectTitle = when (subjectId) {
        "maths" -> "गणित"
        "science" -> "विज्ञान"
        "sst" -> "सामाजिक विज्ञान"
        "hindi" -> "हिंदी"
        else -> "विषय"
    }

    val themeColor = when (subjectId) {
        "maths" -> MathsColor
        "science" -> ScienceColor
        "sst" -> SSTColor
        "hindi" -> HindiColor
        else -> AcademicBlue
    }

    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("अध्याय", "Practice", "Revision")

    // Sub-subject tabs if applicable
    val subSubjects = when (subjectId) {
        "science" -> BiharBoardCurriculumData.scienceSubSubjects
        "sst" -> BiharBoardCurriculumData.sstSubSubjects
        "hindi" -> BiharBoardCurriculumData.hindiSubSubjects
        else -> emptyList()
    }

    var selectedSubSubjectId by remember {
        mutableStateOf(initialSubSubjectId ?: subSubjects.firstOrNull()?.id)
    }

    val filteredChapters = remember(allChapters, selectedSubSubjectId) {
        if (subSubjects.isNotEmpty() && selectedSubSubjectId != null) {
            allChapters.filter { it.subSubjectId == selectedSubSubjectId }
        } else {
            allChapters
        }
    }

    val completedCount = remember(filteredChapters) {
        (filteredChapters.size * 0.4).toInt().coerceAtLeast(1)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmBackground)
            .padding(16.dp)
            .testTag("subject_detail_screen"),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            // Header & Top Progress
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = subjectTitle,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = PrimaryNavy
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "आपने $completedCount/${filteredChapters.size} अध्याय पूरे किए हैं।",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = themeColor
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { if (filteredChapters.isNotEmpty()) completedCount.toFloat() / filteredChapters.size else 0f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = themeColor,
                    trackColor = BorderSubtle
                )
            }
        }

        // Sub-subject filter row (Physics, Chemistry, Biology etc.)
        if (subSubjects.isNotEmpty()) {
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(subSubjects) { sub ->
                        val isSelected = selectedSubSubjectId == sub.id
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedSubSubjectId = sub.id },
                            label = {
                                Text(
                                    text = sub.nameHindi,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = themeColor,
                                selectedLabelColor = Color.White
                            ),
                            modifier = Modifier.testTag("sub_subject_chip_${sub.id}")
                        )
                    }
                }
            }
        }

        // 3 Simple Tabs (अध्याय, Practice, Revision)
        item {
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = CardBackground,
                contentColor = themeColor
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                text = title,
                                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Medium
                            )
                        },
                        modifier = Modifier.testTag("subject_tab_${title}")
                    )
                }
            }
        }

        when (selectedTab) {
            0 -> {
                // अध्याय LIST
                items(filteredChapters) { chapter ->
                    ChapterCardItem(
                        chapter = chapter,
                        themeColor = themeColor,
                        onLearnClick = { onNavigate(ScreenDestination.ChapterDetail(chapter.id)) },
                        onTestClick = { onStartOneClickTest(chapter.id) }
                    )
                }
            }
            1 -> {
                // Practice Tab
                item {
                    PracticeSection(
                        subjectTitle = subjectTitle,
                        chapters = filteredChapters,
                        themeColor = themeColor,
                        onStartOneClickTest = onStartOneClickTest
                    )
                }
            }
            2 -> {
                // Revision Tab
                item {
                    RevisionSection(
                        subjectTitle = subjectTitle,
                        chapters = filteredChapters,
                        themeColor = themeColor,
                        onOpenChapter = { onNavigate(ScreenDestination.ChapterDetail(it)) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ChapterCardItem(
    chapter: Chapter,
    themeColor: Color,
    onLearnClick: () -> Unit,
    onTestClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onLearnClick)
            .testTag("chapter_card_${chapter.id}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(themeColor.copy(alpha = 0.12f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = String.format("%02d", chapter.number),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = themeColor
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = chapter.titleHindi,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = PrimaryNavy
                            )
                        )
                        Text(
                            text = "${chapter.topicsCount} मुख्य टॉपिक",
                            fontSize = 12.sp,
                            color = TextLightSlate
                        )
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    // Small "Test" button for 1-click test directly!
                    OutlinedButton(
                        onClick = onTestClick,
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                        modifier = Modifier.testTag("one_click_test_btn_${chapter.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Quiz,
                            contentDescription = null,
                            tint = themeColor,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Test",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = themeColor
                        )
                    }

                    Button(
                        onClick = onLearnClick,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = themeColor),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                        modifier = Modifier.testTag("learn_chapter_btn_${chapter.id}")
                    ) {
                        Text(
                            text = "पढ़ें",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            LinearProgressIndicator(
                progress = { 0.5f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp)),
                color = themeColor,
                trackColor = BorderSubtle
            )
        }
    }
}

@Composable
private fun PracticeSection(
    subjectTitle: String,
    chapters: List<Chapter>,
    themeColor: Color,
    onStartOneClickTest: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = CardBackground),
            border = CardDefaults.outlinedCardBorder()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "तीव्र बहुविकल्पीय अभ्यास",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "प्रत्येक अध्याय से 10 महत्वपूर्ण प्रश्न सीधे हल करें।",
                    style = MaterialTheme.typography.bodyMedium.copy(color = TextMutedNavy)
                )
            }
        }

        chapters.take(5).forEach { chapter ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                border = CardDefaults.outlinedCardBorder()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = chapter.titleHindi,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryNavy,
                        modifier = Modifier.weight(1f)
                    )
                    Button(
                        onClick = { onStartOneClickTest(chapter.id) },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = themeColor)
                    ) {
                        Text("Practice दें")
                    }
                }
            }
        }
    }
}

@Composable
private fun RevisionSection(
    subjectTitle: String,
    chapters: List<Chapter>,
    themeColor: Color,
    onOpenChapter: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        chapters.take(4).forEach { chapter ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onOpenChapter(chapter.id) },
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                border = CardDefaults.outlinedCardBorder()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = chapter.titleHindi,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = chapter.revisionSummary.ifBlank { "महत्वपूर्ण परिभाषाएँ, सूत्र एवं त्वरित समीक्षा।" },
                        style = MaterialTheme.typography.bodyMedium.copy(color = TextMutedNavy)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "पूरा दोहराव देखें →",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = themeColor
                    )
                }
            }
        }
    }
}
