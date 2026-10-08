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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import com.example.data.model.PracticeMiniQuestion
import com.example.data.model.TestQuestionState
import com.example.data.model.TestResultSummary
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

@Composable
fun MistakeAnalysisScreen(
    summary: TestResultSummary,
    generatedPracticeQuestion: PracticeMiniQuestion?,
    isPracticeQuestionLoading: Boolean,
    onGenerateSimilarQuestion: (String, String) -> Unit,
    onBack: () -> Unit
) {
    val mistakes = remember(summary) {
        summary.questionStates.filter {
            it.selectedOptionIndex == null || it.selectedOptionIndex != it.question.correctIndex
        }
    }

    var activePracticeTopic by remember { mutableStateOf<String?>(null) }
    var selectedMiniAnswer by remember { mutableStateOf<Int?>(null) }
    var showMiniResult by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmBackground)
            .padding(16.dp)
            .testTag("mistake_analysis_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column {
                Text(
                    text = "गलती विश्लेषण (Mistake Analysis)",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = PrimaryNavy
                    )
                )
                Text(
                    text = "प्रत्येक गलती से सीखें और अपनी अवधारणा को मजबूत करें।",
                    style = MaterialTheme.typography.bodyMedium.copy(color = TextMutedNavy)
                )
            }
        }

        // GENERATED SIMILAR PRACTICE QUESTION (IF TRIGGERED)
        if (isPracticeQuestionLoading) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = AcademicBlueLight)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp), color = AcademicBlue)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "नया अभ्यास प्रश्न तैयार किया जा रहा है...",
                            fontWeight = FontWeight.SemiBold,
                            color = AcademicBlue
                        )
                    }
                }
            }
        } else if (generatedPracticeQuestion != null && activePracticeTopic != null) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("generated_practice_question_card"),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = AcademicBlueLight),
                    border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(AcademicBlue))
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = AcademicBlue,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "समान टॉपिक अभ्यास: $activePracticeTopic",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = AcademicBlue
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = generatedPracticeQuestion.question,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = PrimaryNavy
                            )
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        generatedPracticeQuestion.options.forEachIndexed { optIdx, optText ->
                            val isSelected = selectedMiniAnswer == optIdx
                            val isCorrect = optIdx == generatedPracticeQuestion.correctIndex

                            val borderCol = when {
                                showMiniResult && isCorrect -> SuccessGreen
                                showMiniResult && isSelected && !isCorrect -> ErrorRed
                                isSelected -> AcademicBlue
                                else -> BorderSubtle
                            }

                            val bgCol = when {
                                showMiniResult && isCorrect -> SuccessGreenLight
                                showMiniResult && isSelected && !isCorrect -> ErrorRedLight
                                isSelected -> Color.White
                                else -> CardBackground
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .border(1.dp, borderCol, RoundedCornerShape(8.dp))
                                    .background(bgCol)
                                    .clickable {
                                        selectedMiniAnswer = optIdx
                                        showMiniResult = true
                                    }
                                    .padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "${('A' + optIdx)}. $optText",
                                    fontSize = 13.sp,
                                    color = PrimaryNavy,
                                    modifier = Modifier.weight(1f)
                                )
                                if (showMiniResult) {
                                    if (isCorrect) {
                                        Icon(Icons.Default.Check, null, tint = SuccessGreen, modifier = Modifier.size(16.dp))
                                    } else if (isSelected) {
                                        Icon(Icons.Default.Close, null, tint = ErrorRed, modifier = Modifier.size(16.dp))
                                    }
                                }
                            }
                        }

                        if (showMiniResult) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "व्याख्या: ${generatedPracticeQuestion.explanation}",
                                fontSize = 12.sp,
                                color = PrimaryNavy,
                                lineHeight = 18.sp
                            )
                        }
                    }
                }
            }
        }

        // MISTAKES LIST
        itemsIndexed(mistakes) { index, mistake ->
            val q = mistake.question
            val yourAnswerIndex = mistake.selectedOptionIndex
            val yourAnswerText = if (yourAnswerIndex != null) q.options.getOrNull(yourAnswerIndex) ?: "कोई नहीं" else "छोड़ दिया"
            val correctAnswerText = q.options.getOrNull(q.correctIndex) ?: ""

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("mistake_card_${q.id}"),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                border = CardDefaults.outlinedCardBorder()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Question Header & Topic Tag
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "प्रश्न ${index + 1}",
                            fontWeight = FontWeight.Bold,
                            color = PrimaryNavy
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(SurfaceVariant)
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = q.topicName,
                                fontSize = 11.sp,
                                color = TextMutedNavy,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = q.questionText,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = PrimaryNavy,
                            lineHeight = 22.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Your Answer vs Correct Answer
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(ErrorRedLight)
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "आपका उत्तर: ", fontSize = 12.sp, color = ErrorRed, fontWeight = FontWeight.Bold)
                        Text(text = yourAnswerText, fontSize = 12.sp, color = ErrorRed)
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(SuccessGreenLight)
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "सही उत्तर: ", fontSize = 12.sp, color = SuccessGreen, fontWeight = FontWeight.Bold)
                        Text(text = correctAnswerText, fontSize = 12.sp, color = SuccessGreen)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Correct Method / Explanation
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceVariant)
                            .padding(12.dp)
                    ) {
                        Text(
                            text = "💡 सही विधि एवं सरल व्याख्या:",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryNavy
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = q.explanation,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = PrimaryNavy,
                                lineHeight = 20.sp
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // "इसी topic का एक सवाल और" Button
                    Button(
                        onClick = {
                            activePracticeTopic = q.topicName
                            selectedMiniAnswer = null
                            showMiniResult = false
                            onGenerateSimilarQuestion(q.topicName, q.subjectId)
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AcademicBlueLight),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("similar_question_btn_${q.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = AcademicBlue,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "इसी topic का एक सवाल और",
                            color = AcademicBlue,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }

        item {
            OutlinedButton(
                onClick = onBack,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("वापस जाएँ", fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
