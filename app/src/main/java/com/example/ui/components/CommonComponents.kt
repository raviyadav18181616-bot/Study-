package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import com.example.data.model.AppConfig
import com.example.ui.ScreenDestination
import com.example.ui.theme.AcademicBlue
import com.example.ui.theme.AcademicBlueLight
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.CardBackground
import com.example.ui.theme.HindiColor
import com.example.ui.theme.MathsColor
import com.example.ui.theme.PrimaryNavy
import com.example.ui.theme.SSTColor
import com.example.ui.theme.ScienceColor
import com.example.ui.theme.SecondaryNavy
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.SurfaceVariant
import com.example.ui.theme.TextLightSlate
import com.example.ui.theme.TextMutedNavy
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Person

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudyTopAppBar(
    title: String = AppConfig.BRAND_NAME,
    currentClass: String = "कक्षा 10",
    currentBoard: String = "बिहार बोर्ड",
    showBack: Boolean = false,
    onBackClick: () -> Unit = {},
    onClassBoardClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onDoubtClick: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (showBack) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.testTag("top_bar_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "वापस जाएँ",
                            tint = PrimaryNavy
                        )
                    }
                } else {
                    // Brand Identity Pill
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(AcademicBlue),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "SP",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = PrimaryNavy
                        )
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // Class & Board selector chip
                Surface(
                    onClick = onClassBoardClick,
                    shape = RoundedCornerShape(20.dp),
                    color = AcademicBlueLight,
                    border = CardDefaults.outlinedCardBorder(),
                    modifier = Modifier.testTag("class_board_selector_chip")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        Text(
                            text = "$currentClass • $currentBoard",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = AcademicBlue
                        )
                    }
                }

                IconButton(
                    onClick = onSearchClick,
                    modifier = Modifier.testTag("top_bar_search_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "खोजें",
                        tint = PrimaryNavy
                    )
                }

                IconButton(
                    onClick = onDoubtClick,
                    modifier = Modifier.testTag("top_bar_doubt_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.HelpOutline,
                        contentDescription = "सवाल पूछें",
                        tint = AcademicBlue
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = CardBackground,
            titleContentColor = PrimaryNavy
        )
    )
}

@Composable
fun StudyBottomNavigationBar(
    currentDestination: ScreenDestination,
    onSelectTab: (ScreenDestination) -> Unit
) {
    val items = listOf(
        Triple(ScreenDestination.Home, "होम", Icons.Default.Home),
        Triple(ScreenDestination.Subjects, "विषय", Icons.AutoMirrored.Filled.MenuBook),
        Triple(ScreenDestination.Tests, "टेस्ट", Icons.Default.Quiz),
        Triple(ScreenDestination.Courses, "कोर्स", Icons.Default.School),
        Triple(ScreenDestination.Profile, "प्रोफ़ाइल", Icons.Default.Person)
    )

    NavigationBar(
        containerColor = CardBackground,
        tonalElevation = 6.dp,
        modifier = Modifier.testTag("study_bottom_navigation_bar")
    ) {
        items.forEach { (destination, label, icon) ->
            val isSelected = currentDestination::class == destination::class

            NavigationBarItem(
                selected = isSelected,
                onClick = { onSelectTab(destination) },
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = label,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = label,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = AcademicBlue,
                    selectedTextColor = AcademicBlue,
                    unselectedIconColor = TextLightSlate,
                    unselectedTextColor = TextLightSlate,
                    indicatorColor = AcademicBlueLight
                ),
                modifier = Modifier.testTag("nav_item_${label}")
            )
        }
    }
}

@Composable
fun SubjectBadge(subjectId: String) {
    val (label, bg, fg) = when (subjectId) {
        "maths" -> Triple("गणित", MathsColor.copy(alpha = 0.12f), MathsColor)
        "science" -> Triple("विज्ञान", ScienceColor.copy(alpha = 0.12f), ScienceColor)
        "sst" -> Triple("सामाजिक विज्ञान", SSTColor.copy(alpha = 0.12f), SSTColor)
        "hindi" -> Triple("हिंदी", HindiColor.copy(alpha = 0.12f), HindiColor)
        else -> Triple("सामान्य", SurfaceVariant, SecondaryNavy)
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(bg)
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = fg
        )
    }
}

@Composable
fun CustomProgressBar(
    progressPercent: Int,
    color: Color = AcademicBlue,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "प्रगति",
                fontSize = 12.sp,
                color = TextLightSlate
            )
            Text(
                text = "$progressPercent%",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = color
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = { (progressPercent / 100f).coerceIn(0f, 1f) },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = color,
            trackColor = BorderSubtle
        )
    }
}
