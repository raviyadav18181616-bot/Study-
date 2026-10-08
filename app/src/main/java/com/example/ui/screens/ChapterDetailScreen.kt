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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.seed.BiharBoardCurriculumData
import com.example.ui.ScreenDestination
import com.example.ui.components.CustomProgressBar
import com.example.ui.theme.AcademicBlue
import com.example.ui.theme.AcademicBlueLight
import com.example.ui.theme.CardBackground
import com.example.ui.theme.MathsColor
import com.example.ui.theme.PrimaryNavy
import com.example.ui.theme.SSTColor
import com.example.ui.theme.ScienceColor
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextLightSlate
import com.example.ui.theme.TextMutedNavy
import com.example.ui.theme.WarmBackground
import com.example.ui.theme.WarningAmber

@Composable
fun ChapterDetailScreen(
    chapterId: String,
    completedTopics: List<String>,
    onNavigate: (ScreenDestination) -> Unit,
    onStartTest: (String) -> Unit
) {
    val chapter = remember(chapterId) {
        BiharBoardCurriculumData.getChapterById(chapterId)
            ?: BiharBoardCurriculumData.mathsChapters.first()
    }

    val topics = chapter.topics.ifEmpty {
        listOf(
            com.example.data.model.Topic(
                id = "${chapter.id}_t1",
                title = "मूलभूत संकल्पना एवं परिभाषा",
                shortSummary = "अध्याय के प्रारंभिक सिद्धांत एवं सूत्र",
                explanation = "इस भाग में हम पाठ के महत्वपूर्ण सिद्धांतों और परीक्षा में बार-बार पूछे जाने वाले प्रमुख बिंदुओं को विस्तार से समझेंगे।",
                formulaOrKeyPoint = "मुख्य सूत्र: नियम और उनके अनुप्रयोग"
            ),
            com.example.data.model.Topic(
                id = "${chapter.id}_t2",
                title = "महत्वपूर्ण उदाहरण एवं हल",
                shortSummary = "बोर्ड परीक्षा के पैटर्न पर आधारित हल सहित प्रश्न",
                explanation = "प्रत्येक उदाहरण को चरणबद्ध तरीके से समझें और अभ्यास प्रश्नों को स्वयं हल करने का प्रयास करें।",
                formulaOrKeyPoint = "जाँच विधि: उत्तर की सत्यता की पुष्टि"
            )
        )
    }

    val progressPercent = 45

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmBackground)
            .padding(16.dp)
            .testTag("chapter_detail_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            // Chapter Title & Progress
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "अध्याय ${chapter.number}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = AcademicBlue
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = chapter.titleHindi,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = PrimaryNavy
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                CustomProgressBar(progressPercent = progressPercent, color = AcademicBlue)
            }
        }

        // Four Primary Action Cards: 📖 सीखें, 📝 Test, 🔄 Revision, ⭐ Important
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ChapterActionCard(
                    title = "सीखें",
                    subtitle = "📖 पाठ",
                    color = AcademicBlue,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        val firstTopicId = topics.firstOrNull()?.id ?: "${chapter.id}_t1"
                        onNavigate(ScreenDestination.TopicLearning(chapter.id, firstTopicId))
                    }
                )
                ChapterActionCard(
                    title = "Test",
                    subtitle = "📝 टेस्ट",
                    color = ScienceColor,
                    modifier = Modifier.weight(1f),
                    onClick = { onStartTest(chapter.id) }
                )
                ChapterActionCard(
                    title = "Revision",
                    subtitle = "🔄 दोहराव",
                    color = SSTColor,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        val firstTopicId = topics.firstOrNull()?.id ?: "${chapter.id}_t1"
                        onNavigate(ScreenDestination.TopicLearning(chapter.id, firstTopicId))
                    }
                )
                ChapterActionCard(
                    title = "Important",
                    subtitle = "⭐ मुख्य",
                    color = WarningAmber,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        val firstTopicId = topics.firstOrNull()?.id ?: "${chapter.id}_t1"
                        onNavigate(ScreenDestination.TopicLearning(chapter.id, firstTopicId))
                    }
                )
            }
        }

        item {
            Text(
                text = "TOPICS",
                style = MaterialTheme.typography.labelLarge.copy(
                    color = TextLightSlate,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            )
        }

        // Topics List
        itemsIndexed(topics) { index, topic ->
            val isCompleted = completedTopics.contains(topic.id) || index == 0

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onNavigate(ScreenDestination.TopicLearning(chapter.id, topic.id))
                    }
                    .testTag("topic_item_${topic.id}"),
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(if (isCompleted) SuccessGreen.copy(alpha = 0.15f) else AcademicBlueLight),
                            contentAlignment = Alignment.Center
                        ) {
                            if (isCompleted) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = SuccessGreen,
                                    modifier = Modifier.size(18.dp)
                                )
                            } else {
                                Text(
                                    text = "${index + 1}",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = AcademicBlue
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = topic.title,
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryNavy
                                )
                            )
                            if (topic.shortSummary.isNotBlank()) {
                                Text(
                                    text = topic.shortSummary,
                                    fontSize = 12.sp,
                                    color = TextLightSlate
                                )
                            }
                        }
                    }

                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "पढ़ें",
                        tint = AcademicBlue,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ChapterActionCard(
    title: String,
    subtitle: String,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clickable(onClick = onClick)
            .testTag("chapter_action_${title}"),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = subtitle,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
    }
}
