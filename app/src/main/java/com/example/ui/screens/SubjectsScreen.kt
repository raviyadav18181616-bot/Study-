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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.example.ui.ScreenDestination
import com.example.ui.components.CustomProgressBar
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
import com.example.ui.theme.TextLightSlate
import com.example.ui.theme.TextMutedNavy
import com.example.ui.theme.WarmBackground

@Composable
fun SubjectsScreen(
    onNavigate: (ScreenDestination) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmBackground)
            .padding(16.dp)
            .testTag("subjects_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column {
                Text(
                    text = "विषय",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = PrimaryNavy
                    )
                )
                Text(
                    text = "बिहार बोर्ड कक्षा 10 का संपूर्ण पाठ्यक्रम",
                    style = MaterialTheme.typography.bodyMedium.copy(color = TextMutedNavy)
                )
            }
        }

        item {
            SubjectCard(
                title = "गणित",
                englishTitle = "Mathematics",
                description = "वास्तविक संख्याएं, बहुपद, त्रिकोणमिति, द्विघात समीकरण, सांख्यिकी",
                chaptersCount = 15,
                completedChapters = 6,
                icon = Icons.Default.Calculate,
                themeColor = MathsColor,
                lightColor = MathsLight,
                onClick = { onNavigate(ScreenDestination.SubjectDetail("maths")) }
            )
        }

        item {
            SubjectCard(
                title = "विज्ञान",
                englishTitle = "Science",
                description = "भौतिक विज्ञान (5) • रसायन विज्ञान (5) • जीव विज्ञान (6)",
                chaptersCount = 16,
                completedChapters = 5,
                icon = Icons.Default.Science,
                themeColor = ScienceColor,
                lightColor = ScienceLight,
                onClick = { onNavigate(ScreenDestination.SubjectDetail("science")) }
            )
        }

        item {
            SubjectCard(
                title = "सामाजिक विज्ञान",
                englishTitle = "Social Science",
                description = "इतिहास (8) • भूगोल (6) • राजनीति (5) • अर्थशास्त्र (7) • आपदा प्रबंधन (5)",
                chaptersCount = 31,
                completedChapters = 8,
                icon = Icons.Default.Public,
                themeColor = SSTColor,
                lightColor = SSTLight,
                onClick = { onNavigate(ScreenDestination.SubjectDetail("sst")) }
            )
        }

        item {
            SubjectCard(
                title = "हिंदी",
                englishTitle = "Hindi",
                description = "गोधूलि भाग 2 • वर्णिका भाग 2 • हिंदी व्याकरण • रचना",
                chaptersCount = 25,
                completedChapters = 12,
                icon = Icons.Default.Translate,
                themeColor = HindiColor,
                lightColor = HindiLight,
                onClick = { onNavigate(ScreenDestination.SubjectDetail("hindi")) }
            )
        }
    }
}

@Composable
private fun SubjectCard(
    title: String,
    englishTitle: String,
    description: String,
    chaptersCount: Int,
    completedChapters: Int,
    icon: ImageVector,
    themeColor: Color,
    lightColor: Color,
    onClick: () -> Unit
) {
    val progress = ((completedChapters.toFloat() / chaptersCount) * 100).toInt()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("subject_card_${title}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(lightColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = themeColor,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = PrimaryNavy
                            )
                        )
                        Text(
                            text = englishTitle,
                            fontSize = 12.sp,
                            color = TextLightSlate
                        )
                    }
                }

                Button(
                    onClick = onClick,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = themeColor),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Text(text = "पढ़ें", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium.copy(color = TextMutedNavy)
            )

            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "$completedChapters / $chaptersCount अध्याय पूरे किए",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = PrimaryNavy
                )
                Text(
                    text = "$progress%",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = themeColor
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            CustomProgressBar(progressPercent = progress, color = themeColor)
        }
    }
}
