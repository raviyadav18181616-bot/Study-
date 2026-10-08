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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.seed.BiharBoardCurriculumData
import com.example.data.seed.QuestionBankData
import com.example.ui.ScreenDestination
import com.example.ui.theme.AcademicBlue
import com.example.ui.theme.CardBackground
import com.example.ui.theme.MathsColor
import com.example.ui.theme.PrimaryNavy
import com.example.ui.theme.SSTColor
import com.example.ui.theme.ScienceColor
import com.example.ui.theme.SurfaceVariant
import com.example.ui.theme.TextLightSlate
import com.example.ui.theme.TextMutedNavy
import com.example.ui.theme.WarmBackground

data class SearchResultItem(
    val title: String,
    val subtitle: String,
    val category: String, // "अध्याय", "टॉपिक", "टेस्ट"
    val destination: ScreenDestination
)

@Composable
fun SearchScreen(
    searchQuery: String,
    onQueryChange: (String) -> Unit,
    onNavigate: (ScreenDestination) -> Unit
) {
    val allResults = remember(searchQuery) {
        if (searchQuery.isBlank()) emptyList()
        else {
            val q = searchQuery.trim().lowercase()
            val list = mutableListOf<SearchResultItem>()

            // Search in Chapters
            val allChapters = BiharBoardCurriculumData.mathsChapters +
                    BiharBoardCurriculumData.scienceChapters +
                    BiharBoardCurriculumData.sstChapters +
                    BiharBoardCurriculumData.hindiChapters

            allChapters.forEach { chapter ->
                if (chapter.titleHindi.lowercase().contains(q) || chapter.titleEnglish.lowercase().contains(q)) {
                    list.add(
                        SearchResultItem(
                            title = chapter.titleHindi,
                            subtitle = "${chapter.subjectId.uppercase()} • अध्याय ${chapter.number}",
                            category = "अध्याय",
                            destination = ScreenDestination.ChapterDetail(chapter.id)
                        )
                    )
                }
                // Search topics
                chapter.topics.forEach { topic ->
                    if (topic.title.lowercase().contains(q) || topic.shortSummary.lowercase().contains(q)) {
                        list.add(
                            SearchResultItem(
                                title = topic.title,
                                subtitle = "${chapter.titleHindi} • टॉपिक",
                                category = "टॉपिक",
                                destination = ScreenDestination.TopicLearning(chapter.id, topic.id)
                            )
                        )
                    }
                }
            }

            // Search in Question Bank
            QuestionBankData.questions.forEach { question ->
                if (question.questionText.lowercase().contains(q)) {
                    list.add(
                        SearchResultItem(
                            title = question.questionText.take(50) + "...",
                            subtitle = "${question.chapterName} • टेस्ट प्रश्न",
                            category = "टेस्ट",
                            destination = ScreenDestination.Tests
                        )
                    )
                }
            }

            list.take(15)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmBackground)
            .padding(16.dp)
            .testTag("search_screen")
    ) {
        // Search Input
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onQueryChange,
            placeholder = { Text("क्या पढ़ना है? (उदा. त्रिकोणमिति, लेंस, अम्ल)") },
            leadingIcon = {
                Icon(Icons.Default.Search, null, tint = AcademicBlue)
            },
            trailingIcon = {
                if (searchQuery.isNotBlank()) {
                    IconButton(onClick = { onQueryChange("") }) {
                        Icon(Icons.Default.Clear, null)
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("search_text_input"),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (searchQuery.isBlank()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = TextLightSlate,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "विषय, अध्याय, टॉपिक या सूत्र खोजें",
                    style = MaterialTheme.typography.titleMedium.copy(color = TextMutedNavy)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "उदा. वास्तविक संख्याएँ, प्रकाश, pH मान, लोकतंत्र",
                    fontSize = 12.sp,
                    color = TextLightSlate
                )
            }
        } else if (allResults.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "\"$searchQuery\" के लिए कोई परिणाम नहीं मिला",
                    style = MaterialTheme.typography.titleSmall.copy(color = TextMutedNavy)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(allResults) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onNavigate(item.destination) }
                            .testTag("search_result_item"),
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = CardBackground),
                        border = CardDefaults.outlinedCardBorder()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = item.title,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = PrimaryNavy
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = item.subtitle,
                                    fontSize = 11.sp,
                                    color = TextLightSlate
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(SurfaceVariant)
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = item.category,
                                    fontSize = 11.sp,
                                    color = PrimaryNavy,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
