package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = AcademicBlue,
    onPrimary = Color.White,
    primaryContainer = AcademicBlueLight,
    onPrimaryContainer = AcademicBlueDark,
    secondary = MathsColor,
    onSecondary = Color.White,
    secondaryContainer = MathsLight,
    onSecondaryContainer = MathsColor,
    background = WarmBackground,
    onBackground = PrimaryNavy,
    surface = CardBackground,
    onSurface = PrimaryNavy,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = TextMutedNavy,
    outline = BorderSubtle,
    outlineVariant = BorderSubtle.copy(alpha = 0.5f),
    error = ErrorRed,
    onError = Color.White,
    errorContainer = ErrorRedLight,
    onErrorContainer = ErrorRed
)

private val DarkColorScheme = darkColorScheme(
    primary = AcademicBlue,
    onPrimary = Color.White,
    primaryContainer = AcademicBlueDark,
    onPrimaryContainer = Color.White,
    background = PrimaryNavy,
    onBackground = Color.White,
    surface = SecondaryNavy,
    onSurface = Color.White,
    surfaceVariant = SecondaryNavy,
    onSurfaceVariant = Color(0xFFCBD5E1),
    outline = TextMutedNavy
)

@Composable
fun StudyPathTheme(
    darkTheme: Boolean = false, // Light-first by default as requested
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
