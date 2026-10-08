package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.PracticeMiniQuestion
import com.example.data.seed.BiharBoardCurriculumData
import com.example.ui.theme.AcademicBlue
import com.example.ui.theme.AcademicBlueLight
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CardBackground
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.ErrorRedLight
import com.example.ui.theme.MathsColor
import com.example.ui.theme.PrimaryNavy
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.SuccessGreenLight
import com.example.ui.theme.SurfaceVariant
import com.example.ui.theme.TextLightSlate
import com.example.ui.theme.TextMutedNavy
import com.example.ui.theme.WarmBackground
import com.example.ui.theme.WarningAmber

@Composable
fun TopicLearningScreen(
    chapterId: String,
    topicId: String,
    onNextTopic: () -> Unit,
    onMarkCompleted: () -> Unit
) {
    val chapter = remember(chapterId) {
        BiharBoardCurriculumData.getChapterById(chapterId)
            ?: BiharBoardCurriculumData.mathsChapters.first()
    }

    val topic = remember(chapter, topicId) {
        chapter.topics.find { it.id == topicId }
            ?: chapter.topics.firstOrNull()
            ?: com.example.data.model.Topic(
                id = topicId,
                title = "मूलभूत संकल्पना",
                shortSummary = "पाठ का महत्वपूर्ण भाग",
                explanation = "इस संकल्पना का अध्ययन बोर्ड परीक्षा की दृष्टि से अत्यंत आवश्यक है। सभी सूत्रों को कंठस्थ करें।",
                formulaOrKeyPoint = "मुख्य सूत्र: a = bq + r",
                example = "उदाहरण: वास्तविक संख्याओं के यूक्लिड विभाजन पर आधारित प्रश्न।"
            )
    }

    var selectedPracticeOption by remember { mutableStateOf<Int?>(null) }
    var showPracticeResult by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmBackground)
            .testTag("topic_learning_screen")
    ) {
        // Scrollable distraction-free educational content
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Chapter and topic header
            Column {
                Text(
                    text = "${chapter.titleHindi} • टॉपिक",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AcademicBlue
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = topic.title,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = PrimaryNavy
                    )
                )
            }

            // 1. SIMPLE EXPLANATION
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                border = CardDefaults.outlinedCardBorder()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(AcademicBlue)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "सरल व्याख्या",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = AcademicBlue
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = topic.explanation,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            lineHeight = 26.sp,
                            color = PrimaryNavy
                        )
                    )
                }
            }

            // 2. FORMULA / KEY POINT (IF ANY)
            if (!topic.formulaOrKeyPoint.isNullOrBlank()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = AcademicBlueLight),
                    border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(AcademicBlue.copy(alpha = 0.3f)))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = null,
                                tint = AcademicBlue,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "मुख्य सूत्र / संकल्पना",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = AcademicBlue
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = topic.formulaOrKeyPoint!!,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = PrimaryNavy,
                                fontFamily = FontFamily.Monospace
                            )
                        )
                    }
                }
            }

            // 3. EXAMPLE (IF ANY)
            if (!topic.example.isNullOrBlank()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = CardBackground),
                    border = CardDefaults.outlinedCardBorder()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "उदाहरण (Example)",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = MathsColor
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = topic.example!!,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                lineHeight = 24.sp,
                                color = PrimaryNavy
                            )
                        )
                    }
                }
            }

            // 4. PRACTICE MINI QUESTION
            topic.practiceQuestion?.let { miniQ ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = CardBackground),
                    border = CardDefaults.outlinedCardBorder()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "त्वरित अभ्यास (Quick Check)",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = SuccessGreen
                            )
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = miniQ.question,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = PrimaryNavy
                            )
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        miniQ.options.forEachIndexed { optIndex, optText ->
                            val isSelected = selectedPracticeOption == optIndex
                            val isCorrect = optIndex == miniQ.correctIndex

                            val borderCol = when {
                                showPracticeResult && isCorrect -> SuccessGreen
                                showPracticeResult && isSelected && !isCorrect -> ErrorRed
                                isSelected -> AcademicBlue
                                else -> BorderSubtle
                            }

                            val bgCol = when {
                                showPracticeResult && isCorrect -> SuccessGreenLight
                                showPracticeResult && isSelected && !isCorrect -> ErrorRedLight
                                isSelected -> AcademicBlueLight
                                else -> CardBackground
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .border(1.dp, borderCol, RoundedCornerShape(10.dp))
                                    .background(bgCol)
                                    .clickable {
                                        selectedPracticeOption = optIndex
                                        showPracticeResult = true
                                    }
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "${('A' + optIndex)}. $optText",
                                    fontSize = 14.sp,
                                    color = PrimaryNavy,
                                    modifier = Modifier.weight(1f)
                                )
                                if (showPracticeResult) {
                                    if (isCorrect) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = "सही",
                                            tint = SuccessGreen,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    } else if (isSelected) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "गलत",
                                            tint = ErrorRed,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            }
                        }

                        if (showPracticeResult) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "स्पष्टीकरण: ${miniQ.explanation}",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = TextMutedNavy,
                                    lineHeight = 20.sp
                                )
                            )
                        }
                    }
                }
            }
        }

        // Bottom Actions: “फिर से समझें” & “आगे बढ़ें”
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = CardBackground,
            shadowElevation = 8.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        selectedPracticeOption = null
                        showPracticeResult = false
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("understand_again_button"),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "फिर से समझें",
                        fontWeight = FontWeight.Bold
                    )
                }

                Button(
                    onClick = {
                        onMarkCompleted()
                        onNextTopic()
                    },
                    modifier = Modifier
                        .weight(1.3f)
                        .height(48.dp)
                        .testTag("next_topic_button"),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AcademicBlue)
                ) {
                    Text(
                        text = "आगे बढ़ें",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}
