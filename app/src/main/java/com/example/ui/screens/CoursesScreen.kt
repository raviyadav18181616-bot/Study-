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
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Science
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
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
import com.example.data.model.Course
import com.example.ui.ScreenDestination
import com.example.ui.theme.AcademicBlue
import com.example.ui.theme.AcademicBlueLight
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CardBackground
import com.example.ui.theme.MathsColor
import com.example.ui.theme.MathsLight
import com.example.ui.theme.PrimaryNavy
import com.example.ui.theme.SSTColor
import com.example.ui.theme.SSTLight
import com.example.ui.theme.ScienceColor
import com.example.ui.theme.ScienceLight
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.SuccessGreenLight
import com.example.ui.theme.SurfaceVariant
import com.example.ui.theme.TextLightSlate
import com.example.ui.theme.TextMutedNavy
import com.example.ui.theme.WarmBackground

@Composable
fun CoursesScreen(
    onNavigate: (ScreenDestination) -> Unit
) {
    val courses = listOf(
        Course(
            id = "c_maths",
            title = "Class 10 Maths Complete Course",
            subject = "गणित (NCERT & BSEB)",
            instructor = "वरिष्ठ शिक्षक टीम",
            description = "कक्षा 10 बिहार बोर्ड के सभी 15 अध्यायों के सिद्धांत, हस्तलिखित नोट्स, अध्यायवार टेस्ट एवं मॉडल पेपर।",
            totalLessons = 45,
            completedLessons = 18,
            highlights = listOf("15 अध्याय व्याख्या", "हस्तलिखित नोट्स", "अध्यायवार टेस्ट", "लाइव असाइनमेंट")
        ),
        Course(
            id = "c_sci",
            title = "Class 10 Science Master Series",
            subject = "विज्ञान (Physics + Chem + Bio)",
            instructor = "विज्ञान विशेषज्ञ",
            description = "भौतिक, रसायन और जीव विज्ञान के रासायनिक समीकरण, किरण आरेख, और 500+ बोर्ड वस्तुनिष्ठ प्रश्न।",
            totalLessons = 48,
            completedLessons = 14,
            highlights = listOf("किरण आरेख अभ्यास", "रासायनिक समीकरण", "नेफ्रॉन व मानव अंग", "प्रयोगशाला विधि")
        ),
        Course(
            id = "c_sst",
            title = "Class 10 Social Science Complete Guide",
            subject = "सामाजिक विज्ञान",
            instructor = "इतिहास व भूगोल संकाय",
            description = "इतिहास, भूगोल, नागरिक शास्त्र और आपदा प्रबंधन के विस्तृत मानचित्र अध्ययन एवं स्मरणीय बिंदु।",
            totalLessons = 36,
            completedLessons = 9,
            highlights = listOf("मानचित्र अध्ययन", "क्रांतियों का घटनाक्रम", "पंचायती राज नोट्स", "आपदा प्रबंधन")
        )
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmBackground)
            .padding(16.dp)
            .testTag("courses_screen"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column {
                Text(
                    text = "कोर्स (Courses)",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = PrimaryNavy
                    )
                )
                Text(
                    text = "संरचित वीडियो पाठ, नोट्स, असाइनमेंट एवं टेस्ट (सभी छात्रों के लिए निःशुल्क)",
                    style = MaterialTheme.typography.bodyMedium.copy(color = TextMutedNavy)
                )
            }
        }

        courses.forEach { course ->
            item {
                CourseCard(
                    course = course,
                    onOpenCourse = {
                        val subId = if (course.id.contains("maths")) "maths" else if (course.id.contains("sci")) "science" else "sst"
                        onNavigate(ScreenDestination.SubjectDetail(subId))
                    }
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun CourseCard(
    course: Course,
    onOpenCourse: () -> Unit
) {
    val progress = ((course.completedLessons.toFloat() / course.totalLessons) * 100).toInt()
    val themeColor = if (course.id.contains("maths")) MathsColor else if (course.id.contains("sci")) ScienceColor else SSTColor

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onOpenCourse)
            .testTag("course_card_${course.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        border = CardDefaults.outlinedCardBorder()
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(SuccessGreenLight)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "100% निःशुल्क (Free)",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = SuccessGreen
                    )
                }

                Text(
                    text = course.subject,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = themeColor
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = course.title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = PrimaryNavy
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = course.description,
                style = MaterialTheme.typography.bodyMedium.copy(color = TextMutedNavy)
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Course highlights
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                course.highlights.take(3).forEach { hl ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(SurfaceVariant)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "• $hl",
                            fontSize = 11.sp,
                            color = PrimaryNavy
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "${course.completedLessons}/${course.totalLessons} पाठ पूरे हुए ($progress%)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = PrimaryNavy
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    LinearProgressIndicator(
                        progress = { progress / 100f },
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = themeColor,
                        trackColor = BorderSubtle
                    )
                }

                Button(
                    onClick = onOpenCourse,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = themeColor),
                    modifier = Modifier.testTag("open_course_btn_${course.id}")
                ) {
                    Text("पढ़ाई करें", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
