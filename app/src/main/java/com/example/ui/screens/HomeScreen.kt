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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.ActiveTestSessionEntity
import com.example.data.local.UserProfileEntity
import com.example.data.model.SubjectCategory
import com.example.ui.ScreenDestination
import com.example.ui.components.CustomProgressBar
import com.example.ui.theme.AcademicBlue
import com.example.ui.theme.AcademicBlueDark
import com.example.ui.theme.AcademicBlueLight
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CardBackground
import com.example.ui.theme.HindiColor
import com.example.ui.theme.HindiLight
import com.example.ui.theme.MathsColor
import com.example.ui.theme.MathsLight
import com.example.ui.theme.PrimaryNavy
import com.example.ui.theme.SSTColor
import com.example.ui.theme.SSTLight
import com.example.ui.theme.ScienceColor
import com.example.ui.theme.ScienceLight
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.SuccessGreenLight
import com.example.ui.theme.TextLightSlate
import com.example.ui.theme.TextMutedNavy
import com.example.ui.theme.WarmBackground
import com.example.ui.theme.WarningAmber

@Composable
fun HomeScreen(
    userProfile: UserProfileEntity,
    activeSession: ActiveTestSessionEntity?,
    onNavigate: (ScreenDestination) -> Unit,
    onStartDailyTest: () -> Unit,
    onResumeActiveTest: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmBackground)
            .padding(horizontal = 16.dp)
            .testTag("home_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            // 1. Top Greeting
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "नमस्ते, ${userProfile.studentName} 👋",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = PrimaryNavy
                    )
                )
                Text(
                    text = "आज क्या पढ़ना है?",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = TextMutedNavy,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }

        // Active unfinished test resume banner (if any)
        if (activeSession != null) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("resume_test_card"),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = SuccessGreenLight),
                    border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(SuccessGreen))
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
                                text = "जारी रखें: ${activeSession.testTitle}",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = SuccessGreen
                                )
                            )
                            Text(
                                text = "बचा हुआ समय: ${activeSession.remainingSeconds / 60} मिनट",
                                style = MaterialTheme.typography.bodySmall.copy(color = PrimaryNavy)
                            )
                        }
                        Button(
                            onClick = onResumeActiveTest,
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen),
                            modifier = Modifier.testTag("resume_test_button")
                        ) {
                            Text("Test जारी रखें", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // 2. MAIN ACTION: आज की पढ़ाई
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("todays_goal_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "आज की पढ़ाई",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = AcademicBlue
                            )
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(AcademicBlueLight)
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "लक्ष्य",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = AcademicBlue
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "आज का लक्ष्य",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = PrimaryNavy
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        TargetPill("⏱️ 30 मिनट")
                        TargetPill("📝 10 प्रश्न")
                        TargetPill("🔄 1 Revision")
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    Button(
                        onClick = onStartDailyTest,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("start_daily_goal_button"),
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
                            text = "शुरू करें",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // 3. CONTINUE LEARNING (One card only)
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "CONTINUE LEARNING",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = TextLightSlate,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNavigate(ScreenDestination.ChapterDetail("m_01")) }
                        .testTag("continue_learning_card"),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = CardBackground),
                    border = CardDefaults.outlinedCardBorder()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(MathsLight),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Calculate,
                                        contentDescription = null,
                                        tint = MathsColor,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = "गणित",
                                        fontSize = 12.sp,
                                        color = MathsColor,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "वास्तविक संख्याएँ",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = PrimaryNavy
                                        )
                                    )
                                }
                            }

                            OutlinedButton(
                                onClick = { onNavigate(ScreenDestination.ChapterDetail("m_01")) },
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.testTag("continue_learning_button")
                            ) {
                                Text(
                                    text = "जारी रखें",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = AcademicBlue
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        CustomProgressBar(progressPercent = 68, color = MathsColor)
                    }
                }
            }
        }

        // 4. MY SUBJECTS (Four compact cards)
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "MY SUBJECTS",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = TextLightSlate,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CompactSubjectCard(
                        title = "गणित",
                        sub = "15 अध्याय",
                        icon = Icons.Default.Calculate,
                        color = MathsColor,
                        bg = MathsLight,
                        progress = 40,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigate(ScreenDestination.SubjectDetail("maths")) }
                    )
                    CompactSubjectCard(
                        title = "विज्ञान",
                        sub = "16 अध्याय",
                        icon = Icons.Default.Science,
                        color = ScienceColor,
                        bg = ScienceLight,
                        progress = 35,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigate(ScreenDestination.SubjectDetail("science")) }
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CompactSubjectCard(
                        title = "सामाजिक विज्ञान",
                        sub = "29 अध्याय",
                        icon = Icons.Default.Public,
                        color = SSTColor,
                        bg = SSTLight,
                        progress = 25,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigate(ScreenDestination.SubjectDetail("sst")) }
                    )
                    CompactSubjectCard(
                        title = "हिंदी",
                        sub = "25 पाठ",
                        icon = Icons.Default.Translate,
                        color = HindiColor,
                        bg = HindiLight,
                        progress = 50,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigate(ScreenDestination.SubjectDetail("hindi")) }
                    )
                }
            }
        }

        // 5. QUICK ACTIONS (Only four: सवाल पूछें, Practice, Revision, Test)
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "QUICK ACTIONS",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = TextLightSlate,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickActionItem(
                        title = "सवाल पूछें",
                        icon = Icons.AutoMirrored.Filled.HelpOutline,
                        color = AcademicBlue,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigate(ScreenDestination.DoubtSolver) }
                    )
                    QuickActionItem(
                        title = "Practice",
                        icon = Icons.Default.FitnessCenter,
                        color = ScienceColor,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigate(ScreenDestination.SubjectDetail("maths")) }
                    )
                    QuickActionItem(
                        title = "Revision",
                        icon = Icons.Default.Repeat,
                        color = SSTColor,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigate(ScreenDestination.ChapterDetail("m_01")) }
                    )
                    QuickActionItem(
                        title = "Test",
                        icon = Icons.Default.Quiz,
                        color = HindiColor,
                        modifier = Modifier.weight(1f),
                        onClick = { onNavigate(ScreenDestination.Tests) }
                    )
                }
            }
        }

        // 6. SMALL PROGRESS AREA
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("small_progress_area"),
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
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Overall Preparation",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextLightSlate
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "कुल तैयारी: ${userProfile.overallProgress}%",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = PrimaryNavy
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        LinearProgressIndicator(
                            progress = { userProfile.overallProgress / 100f },
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = AcademicBlue,
                            trackColor = BorderSubtle
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(AcademicBlueLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${userProfile.streakDays}🔥",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun TargetPill(text: String) {
    Surface(
        shape = RoundedCornerShape(6.dp),
        color = WarmBackground,
        border = CardDefaults.outlinedCardBorder()
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = PrimaryNavy,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
private fun CompactSubjectCard(
    title: String,
    sub: String,
    icon: ImageVector,
    color: Color,
    bg: Color,
    progress: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clickable(onClick = onClick)
            .testTag("compact_subject_${title}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(bg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = PrimaryNavy
                )
            )
            Text(
                text = sub,
                fontSize = 11.sp,
                color = TextLightSlate
            )
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { progress / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp)),
                color = color,
                trackColor = BorderSubtle
            )
        }
    }
}

@Composable
private fun QuickActionItem(
    title: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clickable(onClick = onClick)
            .testTag("quick_action_${title}"),
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
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryNavy
            )
        }
    }
}
