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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.data.model.TestResultSummary
import com.example.ui.ScreenDestination
import com.example.ui.theme.AcademicBlue
import com.example.ui.theme.AcademicBlueLight
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CardBackground
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.ErrorRedLight
import com.example.ui.theme.PrimaryNavy
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.SuccessGreenLight
import com.example.ui.theme.SurfaceVariant
import com.example.ui.theme.TextLightSlate
import com.example.ui.theme.TextMutedNavy
import com.example.ui.theme.WarmBackground
import com.example.ui.theme.WarningAmber
import com.example.ui.theme.WarningAmberLight

@Composable
fun TestResultScreen(
    summary: TestResultSummary,
    feedbackSubmitted: Boolean,
    onViewMistakes: () -> Unit,
    onSubmitFeedback: (String, String) -> Unit,
    onStartRecommendedTest: (String) -> Unit,
    onBackToTestCenter: () -> Unit
) {
    var selectedFeedbackRating by remember { mutableStateOf("बहुत अच्छा") }
    var feedbackNotes by remember { mutableStateOf("") }

    val recommendedTopic = summary.weakTopics.firstOrNull() ?: summary.chapterName

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmBackground)
            .padding(16.dp)
            .testTag("test_result_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. CELEBRATION HEADER & SCORE CARD
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Test पूरा हुआ 🎉",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = PrimaryNavy
                        )
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = summary.testTitle,
                        style = MaterialTheme.typography.bodyMedium.copy(color = TextMutedNavy)
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // Score Circle
                    Box(
                        modifier = Modifier
                            .size(110.dp)
                            .clip(CircleShape)
                            .background(
                                when (summary.performanceLevel) {
                                    "Strong" -> SuccessGreenLight
                                    "Needs Improvement" -> WarningAmberLight
                                    else -> ErrorRedLight
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "${summary.score} / ${summary.totalQuestions}",
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    color = when (summary.performanceLevel) {
                                        "Strong" -> SuccessGreen
                                        "Needs Improvement" -> WarningAmber
                                        else -> ErrorRed
                                    }
                                )
                            )
                            Text(
                                text = "${summary.percentage}%",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryNavy
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Performance Badge
                    val (perfText, perfColor) = when (summary.performanceLevel) {
                        "Strong" -> "उत्कृष्ट प्रदर्शन (Strong)" to SuccessGreen
                        "Needs Improvement" -> "सुधार की आवश्यकता (Needs Improvement)" to WarningAmber
                        else -> "कमजोर - अभ्यास आवश्यक (Weak)" to ErrorRed
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(perfColor.copy(alpha = 0.12f))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = perfText,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = perfColor
                        )
                    }
                }
            }
        }

        // 2. STATISTICS GRID (Correct, Wrong, Skipped, Accuracy, Time)
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                border = CardDefaults.outlinedCardBorder()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "टेस्ट विश्लेषण",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        StatBox("सही (Correct)", "${summary.correctCount}", SuccessGreen, Modifier.weight(1f))
                        Spacer(modifier = Modifier.width(8.dp))
                        StatBox("गलत (Wrong)", "${summary.wrongCount}", ErrorRed, Modifier.weight(1f))
                        Spacer(modifier = Modifier.width(8.dp))
                        StatBox("छोड़े (Skipped)", "${summary.skippedCount}", TextLightSlate, Modifier.weight(1f))
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        StatBox("शुद्धता (Accuracy)", "${summary.percentage}%", AcademicBlue, Modifier.weight(1f))
                        Spacer(modifier = Modifier.width(8.dp))
                        StatBox("समय (Time)", "${summary.timeSpentSeconds / 60} मिनट", PrimaryNavy, Modifier.weight(1f))
                    }
                }
            }
        }

        // 3. MISTAKE ANALYSIS BUTTON
        if (summary.wrongCount > 0 || summary.skippedCount > 0) {
            item {
                Button(
                    onClick = onViewMistakes,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("view_mistakes_button"),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryNavy)
                ) {
                    Icon(
                        imageVector = Icons.Default.ReportProblem,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = WarningAmber
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "मेरी गलतियाँ देखें (${summary.wrongCount + summary.skippedCount})",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }
        }

        // 4. PERSONALIZED NEXT TEST (ONE RECOMMENDED ACTION ONLY)
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = AcademicBlueLight),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(AcademicBlue.copy(alpha = 0.3f)))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "सिफारिश (Next Action)",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = AcademicBlue
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "आपको $recommendedTopic में अधिक अभ्यास चाहिए।",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = PrimaryNavy
                        )
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = { onStartRecommendedTest(recommendedTopic) },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AcademicBlue),
                        modifier = Modifier.testTag("recommended_next_test_btn")
                    ) {
                        Text(text = "$recommendedTopic Test दें", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // 5. STUDENT FEEDBACK SECTION
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                border = CardDefaults.outlinedCardBorder()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "यह Test कैसा लगा?",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    if (feedbackSubmitted) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = SuccessGreen,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "आपकी प्रतिक्रिया सुरक्षित कर ली गई है! धन्यवाद।",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = SuccessGreen
                            )
                        }
                    } else {
                        val ratings = listOf("बहुत अच्छा", "अच्छा", "ठीक", "सुधार चाहिए")
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            ratings.forEach { rating ->
                                val isSel = selectedFeedbackRating == rating
                                FilterChip(
                                    selected = isSel,
                                    onClick = { selectedFeedbackRating = rating },
                                    label = { Text(text = rating, fontSize = 12.sp) },
                                    modifier = Modifier.testTag("feedback_chip_${rating}")
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedTextField(
                            value = feedbackNotes,
                            onValueChange = { feedbackNotes = it },
                            placeholder = { Text("आप क्या बदलना चाहेंगे? (वैकल्पिक)") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("feedback_notes_input"),
                            shape = RoundedCornerShape(8.dp)
                        )

                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedButton(
                            onClick = { onSubmitFeedback(selectedFeedbackRating, feedbackNotes) },
                            modifier = Modifier.testTag("submit_feedback_btn")
                        ) {
                            Text("प्रतिक्रिया भेजें")
                        }
                    }
                }
            }
        }

        // Back to Test Center
        item {
            OutlinedButton(
                onClick = onBackToTestCenter,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("back_to_test_center_btn"),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = "Test Center पर वापस जाएँ", fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun StatBox(label: String, value: String, color: Color, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(SurfaceVariant)
            .padding(vertical = 10.dp, horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = value, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = color)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = label, fontSize = 11.sp, color = TextMutedNavy)
        }
    }
}
