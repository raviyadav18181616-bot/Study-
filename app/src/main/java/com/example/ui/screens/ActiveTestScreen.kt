package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Question
import com.example.data.model.TestConfig
import com.example.ui.theme.AcademicBlue
import com.example.ui.theme.AcademicBlueDark
import com.example.ui.theme.AcademicBlueLight
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CardBackground
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.PrimaryNavy
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.SuccessGreenLight
import com.example.ui.theme.SurfaceVariant
import com.example.ui.theme.TextLightSlate
import com.example.ui.theme.TextMutedNavy
import com.example.ui.theme.WarmBackground
import com.example.ui.theme.WarningAmber

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ActiveTestScreen(
    testConfig: TestConfig,
    questions: List<Question>,
    currentIndex: Int,
    userAnswers: Map<Int, Int>,
    markedIndices: Set<Int>,
    remainingSeconds: Int,
    onSelectOption: (Int, Int) -> Unit,
    onToggleMark: (Int) -> Unit,
    onGoToQuestion: (Int) -> Unit,
    onNextQuestion: () -> Unit,
    onPreviousQuestion: () -> Unit,
    onSubmitTest: () -> Unit,
    onNavigateBack: () -> Unit
) {
    var showSubmitConfirmDialog by remember { mutableStateOf(false) }
    var showPaletteSheet by remember { mutableStateOf(false) }

    // Intercept back button to warn about test in progress
    BackHandler {
        showSubmitConfirmDialog = true
    }

    val currentQuestion = questions.getOrNull(currentIndex)
    val totalQuestions = questions.size

    val minutes = remainingSeconds / 60
    val seconds = remainingSeconds % 60
    val timerText = String.format("%02d:%02d", minutes, seconds)
    val isTimerLow = remainingSeconds < 120

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmBackground)
            .testTag("active_test_screen")
    ) {
        // TOP APP BAR: Test Name, Timer, Question Palette Button
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = CardBackground,
            shadowElevation = 2.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = testConfig.title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = PrimaryNavy
                        ),
                        maxLines = 1
                    )
                    Text(
                        text = "स्वतः सहेजा जा रहा है ✓",
                        fontSize = 11.sp,
                        color = SuccessGreen,
                        fontWeight = FontWeight.Medium
                    )
                }

                // Timer Pill
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isTimerLow) ErrorRed.copy(alpha = 0.12f) else AcademicBlueLight)
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Timer,
                            contentDescription = null,
                            tint = if (isTimerLow) ErrorRed else AcademicBlue,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = timerText,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace,
                            color = if (isTimerLow) ErrorRed else AcademicBlue
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Palette grid button
                IconButton(
                    onClick = { showPaletteSheet = true },
                    modifier = Modifier.testTag("open_question_palette_btn")
                ) {
                    Icon(
                        imageVector = Icons.Default.GridView,
                        contentDescription = "Question Palette",
                        tint = PrimaryNavy
                    )
                }
            }
        }

        // SCROLLABLE QUESTION CONTENT
        if (currentQuestion != null) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                // Question Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Question ${String.format("%02d", currentIndex + 1)} / ${String.format("%02d", totalQuestions)}",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = AcademicBlue
                        )
                    )

                    // Mark for review indicator chip
                    val isMarked = markedIndices.contains(currentIndex)
                    Surface(
                        onClick = { onToggleMark(currentIndex) },
                        shape = RoundedCornerShape(6.dp),
                        color = if (isMarked) WarningAmber.copy(alpha = 0.15f) else SurfaceVariant,
                        modifier = Modifier.testTag("mark_for_review_chip")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = if (isMarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                contentDescription = null,
                                tint = if (isMarked) WarningAmber else TextLightSlate,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (isMarked) "चिह्नित है" else "चिह्नित करें",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (isMarked) WarningAmber else TextLightSlate
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Question Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = CardBackground),
                    border = CardDefaults.outlinedCardBorder()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = currentQuestion.questionText,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = PrimaryNavy,
                                lineHeight = 26.sp
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Options List
                val selectedOption = userAnswers[currentIndex]

                currentQuestion.options.forEachIndexed { optIndex, optText ->
                    val isSelected = selectedOption == optIndex
                    val optLetter = ('A' + optIndex)

                    val borderCol = if (isSelected) AcademicBlue else BorderSubtle
                    val bgCol = if (isSelected) AcademicBlueLight else CardBackground

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp)
                            .clickable { onSelectOption(currentIndex, optIndex) }
                            .testTag("option_${optLetter}"),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = bgCol),
                        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(borderCol))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(if (isSelected) AcademicBlue else SurfaceVariant),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "$optLetter",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) Color.White else PrimaryNavy
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = optText,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = PrimaryNavy,
                                    lineHeight = 22.sp
                                ),
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }

        // BOTTOM ACTION CONTROLS (Previous, Next, Submit)
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = CardBackground,
            shadowElevation = 8.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Previous Button
                OutlinedButton(
                    onClick = onPreviousQuestion,
                    enabled = currentIndex > 0,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.testTag("test_prev_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("पिछला")
                }

                // Submit Button
                Button(
                    onClick = { showSubmitConfirmDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.testTag("test_submit_button")
                ) {
                    Text(
                        text = "सबमिट करें",
                        fontWeight = FontWeight.Bold
                    )
                }

                // Next Button
                Button(
                    onClick = onNextQuestion,
                    enabled = currentIndex < totalQuestions - 1,
                    colors = ButtonDefaults.buttonColors(containerColor = AcademicBlue),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.testTag("test_next_button")
                ) {
                    Text("अगला")
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }

    // SUBMIT CONFIRMATION DIALOG
    if (showSubmitConfirmDialog) {
        val answeredCount = userAnswers.size
        val unansweredCount = totalQuestions - answeredCount

        AlertDialog(
            onDismissRequest = { showSubmitConfirmDialog = false },
            title = {
                Text(
                    text = "क्या आप टेस्ट सबमिट करना चाहते हैं?",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column {
                    Text(text = "कुल प्रश्न: $totalQuestions")
                    Text(text = "उत्तर दिए गए: $answeredCount", color = SuccessGreen, fontWeight = FontWeight.Bold)
                    Text(text = "बिना उत्तर के: $unansweredCount", color = TextLightSlate)
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showSubmitConfirmDialog = false
                        onSubmitTest()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen)
                ) {
                    Text("हाँ, सबमिट करें")
                }
            },
            dismissButton = {
                TextButton(onClick = { showSubmitConfirmDialog = false }) {
                    Text("जारी रखें")
                }
            }
        )
    }

    // QUESTION PALETTE BOTTOM SHEET
    if (showPaletteSheet) {
        ModalBottomSheet(
            onDismissRequest = { showPaletteSheet = false },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = "Question Palette",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(12.dp))

                // Legend
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    PaletteLegend(SuccessGreen, "उत्तर दिया")
                    PaletteLegend(WarningAmber, "चिह्नित")
                    PaletteLegend(BorderSubtle, "शेष")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Palette grid
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    for (i in 0 until totalQuestions) {
                        val isAnswered = userAnswers.containsKey(i)
                        val isMarked = markedIndices.contains(i)
                        val isCurrent = i == currentIndex

                        val bg = when {
                            isCurrent -> AcademicBlue
                            isMarked -> WarningAmber
                            isAnswered -> SuccessGreen
                            else -> SurfaceVariant
                        }

                        val fg = if (isCurrent || isMarked || isAnswered) Color.White else PrimaryNavy

                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(bg)
                                .clickable {
                                    onGoToQuestion(i)
                                    showPaletteSheet = false
                                }
                                .testTag("palette_item_$i"),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${i + 1}",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = fg
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
private fun PaletteLegend(color: Color, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = text, fontSize = 12.sp, color = TextMutedNavy)
    }
}
