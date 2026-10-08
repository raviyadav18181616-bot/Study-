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
import androidx.compose.material.icons.filled.CardMembership
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
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
import com.example.data.local.TestHistoryEntity
import com.example.data.local.UserProfileEntity
import com.example.ui.components.CustomProgressBar
import com.example.ui.theme.AcademicBlue
import com.example.ui.theme.AcademicBlueLight
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CardBackground
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
fun ProfileScreen(
    userProfile: UserProfileEntity,
    testHistory: List<TestHistoryEntity>,
    onOpenClassBoardDialog: () -> Unit,
    onSaveName: (String) -> Unit
) {
    var isEditingName by remember { mutableStateOf(false) }
    var editedName by remember { mutableStateOf(userProfile.studentName) }

    val totalTests = testHistory.size
    val totalQuestionsSolved = testHistory.sumOf { it.totalQuestions }
    val totalCorrect = testHistory.sumOf { it.correctCount }
    val overallAccuracy = if (totalQuestionsSolved > 0) ((totalCorrect.toFloat() / totalQuestionsSolved) * 100).toInt() else 85

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmBackground)
            .padding(16.dp)
            .testTag("profile_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Profile Header Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                border = CardDefaults.outlinedCardBorder()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .background(AcademicBlue),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = userProfile.studentName.take(1).ifBlank { "वि" },
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            if (isEditingName) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    OutlinedTextField(
                                        value = editedName,
                                        onValueChange = { editedName = it },
                                        modifier = Modifier
                                            .weight(1f)
                                            .testTag("edit_student_name_input"),
                                        shape = RoundedCornerShape(8.dp),
                                        singleLine = true
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Button(
                                        onClick = {
                                            onSaveName(editedName)
                                            isEditingName = false
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = AcademicBlue)
                                    ) {
                                        Text("Save")
                                    }
                                }
                            } else {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = userProfile.studentName,
                                        style = MaterialTheme.typography.titleLarge.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = PrimaryNavy
                                        )
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "नाम बदलें",
                                        tint = AcademicBlue,
                                        modifier = Modifier
                                            .size(18.dp)
                                            .clickable { isEditingName = true }
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${userProfile.studentClass} • ${userProfile.board}",
                                style = MaterialTheme.typography.bodyMedium.copy(color = TextMutedNavy)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Change Class/Board Button
                    OutlinedButton(
                        onClick = onOpenClassBoardDialog,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("change_class_board_button"),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.School,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "कक्षा या Board बदलें",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // 2. Metrics & Stats
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                border = CardDefaults.outlinedCardBorder()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "अध्ययन आँकड़े (Study Analytics)",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MetricItem("पूर्ण टेस्ट", "$totalTests", AcademicBlue, Modifier.weight(1f))
                        MetricItem("प्रश्न हल किए", "$totalQuestionsSolved", MathsColor, Modifier.weight(1f))
                        MetricItem("औसत शुद्धता", "$overallAccuracy%", SuccessGreen, Modifier.weight(1f))
                        MetricItem("Streak", "${userProfile.streakDays} दिन 🔥", WarningAmber, Modifier.weight(1f))
                    }
                }
            }
        }

        // 3. Achievements Badges
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                border = CardDefaults.outlinedCardBorder()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "उपलब्धियाँ (Achievements)",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        AchievementBadge("🏆", "नियमित छात्र", "लगातार 3 दिन पढ़ाई", Modifier.weight(1f))
                        AchievementBadge("⚡", "फास्ट टेस्ट", "10 मिनट में 10 प्रश्न", Modifier.weight(1f))
                        AchievementBadge("🎯", "80%+ स्कोर", "उत्कृष्ट सटीकता", Modifier.weight(1f))
                    }
                }
            }
        }

        // 4. Data Safety & Offline Mode Notice (Section 51)
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceVariant),
                border = CardDefaults.outlinedCardBorder()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Security,
                        contentDescription = null,
                        tint = SuccessGreen,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "सुरक्षित एवं ऑफलाइन तैयार (Zero Data Leak)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = PrimaryNavy
                        )
                        Text(
                            text = "आपकी प्रगति और टेस्ट परिणाम केवल आपके फोन में सुरक्षित हैं। कोई विज्ञापन नहीं।",
                            fontSize = 11.sp,
                            color = TextMutedNavy
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun MetricItem(label: String, value: String, color: Color, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = value, fontSize = 17.sp, fontWeight = FontWeight.ExtraBold, color = color)
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = label, fontSize = 11.sp, color = TextMutedNavy, maxLines = 1)
    }
}

@Composable
private fun AchievementBadge(icon: String, title: String, sub: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(SurfaceVariant)
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = icon, fontSize = 22.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = title, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = PrimaryNavy, maxLines = 1)
            Text(text = sub, fontSize = 10.sp, color = TextLightSlate, maxLines = 1)
        }
    }
}
