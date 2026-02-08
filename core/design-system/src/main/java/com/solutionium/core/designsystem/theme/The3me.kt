package com.example.compose
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.solutionium.core.designsystem.theme.backgroundDark
import com.solutionium.core.designsystem.theme.backgroundDarkHighContrast
import com.solutionium.core.designsystem.theme.backgroundDarkMediumContrast
import com.solutionium.core.designsystem.theme.backgroundLight
import com.solutionium.core.designsystem.theme.backgroundLightHighContrast
import com.solutionium.core.designsystem.theme.backgroundLightMediumContrast
import com.solutionium.core.designsystem.theme.errorContainerDark
import com.solutionium.core.designsystem.theme.errorContainerDarkHighContrast
import com.solutionium.core.designsystem.theme.errorContainerDarkMediumContrast
import com.solutionium.core.designsystem.theme.errorContainerLight
import com.solutionium.core.designsystem.theme.errorContainerLightHighContrast
import com.solutionium.core.designsystem.theme.errorContainerLightMediumContrast
import com.solutionium.core.designsystem.theme.errorDark
import com.solutionium.core.designsystem.theme.errorDarkHighContrast
import com.solutionium.core.designsystem.theme.errorDarkMediumContrast
import com.solutionium.core.designsystem.theme.errorLight
import com.solutionium.core.designsystem.theme.errorLightHighContrast
import com.solutionium.core.designsystem.theme.errorLightMediumContrast
import com.solutionium.core.designsystem.theme.inverseOnSurfaceDark
import com.solutionium.core.designsystem.theme.inverseOnSurfaceDarkHighContrast
import com.solutionium.core.designsystem.theme.inverseOnSurfaceDarkMediumContrast
import com.solutionium.core.designsystem.theme.inverseOnSurfaceLight
import com.solutionium.core.designsystem.theme.inverseOnSurfaceLightHighContrast
import com.solutionium.core.designsystem.theme.inverseOnSurfaceLightMediumContrast
import com.solutionium.core.designsystem.theme.inversePrimaryDark
import com.solutionium.core.designsystem.theme.inversePrimaryDarkHighContrast
import com.solutionium.core.designsystem.theme.inversePrimaryDarkMediumContrast
import com.solutionium.core.designsystem.theme.inversePrimaryLight
import com.solutionium.core.designsystem.theme.inversePrimaryLightHighContrast
import com.solutionium.core.designsystem.theme.inversePrimaryLightMediumContrast
import com.solutionium.core.designsystem.theme.inverseSurfaceDark
import com.solutionium.core.designsystem.theme.inverseSurfaceDarkHighContrast
import com.solutionium.core.designsystem.theme.inverseSurfaceDarkMediumContrast
import com.solutionium.core.designsystem.theme.inverseSurfaceLight
import com.solutionium.core.designsystem.theme.inverseSurfaceLightHighContrast
import com.solutionium.core.designsystem.theme.inverseSurfaceLightMediumContrast
import com.solutionium.core.designsystem.theme.onBackgroundDark
import com.solutionium.core.designsystem.theme.onBackgroundDarkHighContrast
import com.solutionium.core.designsystem.theme.onBackgroundDarkMediumContrast
import com.solutionium.core.designsystem.theme.onBackgroundLight
import com.solutionium.core.designsystem.theme.onBackgroundLightHighContrast
import com.solutionium.core.designsystem.theme.onBackgroundLightMediumContrast
import com.solutionium.core.designsystem.theme.onErrorContainerDark
import com.solutionium.core.designsystem.theme.onErrorContainerDarkHighContrast
import com.solutionium.core.designsystem.theme.onErrorContainerDarkMediumContrast
import com.solutionium.core.designsystem.theme.onErrorContainerLight
import com.solutionium.core.designsystem.theme.onErrorContainerLightHighContrast
import com.solutionium.core.designsystem.theme.onErrorContainerLightMediumContrast
import com.solutionium.core.designsystem.theme.onErrorDark
import com.solutionium.core.designsystem.theme.onErrorDarkHighContrast
import com.solutionium.core.designsystem.theme.onErrorDarkMediumContrast
import com.solutionium.core.designsystem.theme.onErrorLight
import com.solutionium.core.designsystem.theme.onErrorLightHighContrast
import com.solutionium.core.designsystem.theme.onErrorLightMediumContrast
import com.solutionium.core.designsystem.theme.onPrimaryContainerDark
import com.solutionium.core.designsystem.theme.onPrimaryContainerDarkHighContrast
import com.solutionium.core.designsystem.theme.onPrimaryContainerDarkMediumContrast
import com.solutionium.core.designsystem.theme.onPrimaryContainerLight
import com.solutionium.core.designsystem.theme.onPrimaryContainerLightHighContrast
import com.solutionium.core.designsystem.theme.onPrimaryContainerLightMediumContrast
import com.solutionium.core.designsystem.theme.onPrimaryDark
import com.solutionium.core.designsystem.theme.onPrimaryDarkHighContrast
import com.solutionium.core.designsystem.theme.onPrimaryDarkMediumContrast
import com.solutionium.core.designsystem.theme.onPrimaryLight
import com.solutionium.core.designsystem.theme.onPrimaryLightHighContrast
import com.solutionium.core.designsystem.theme.onPrimaryLightMediumContrast
import com.solutionium.core.designsystem.theme.onSecondaryContainerDark
import com.solutionium.core.designsystem.theme.onSecondaryContainerDarkHighContrast
import com.solutionium.core.designsystem.theme.onSecondaryContainerDarkMediumContrast
import com.solutionium.core.designsystem.theme.onSecondaryContainerLight
import com.solutionium.core.designsystem.theme.onSecondaryContainerLightHighContrast
import com.solutionium.core.designsystem.theme.onSecondaryContainerLightMediumContrast
import com.solutionium.core.designsystem.theme.onSecondaryDark
import com.solutionium.core.designsystem.theme.onSecondaryDarkHighContrast
import com.solutionium.core.designsystem.theme.onSecondaryDarkMediumContrast
import com.solutionium.core.designsystem.theme.onSecondaryLight
import com.solutionium.core.designsystem.theme.onSecondaryLightHighContrast
import com.solutionium.core.designsystem.theme.onSecondaryLightMediumContrast
import com.solutionium.core.designsystem.theme.onSurfaceDark
import com.solutionium.core.designsystem.theme.onSurfaceDarkHighContrast
import com.solutionium.core.designsystem.theme.onSurfaceDarkMediumContrast
import com.solutionium.core.designsystem.theme.onSurfaceLight
import com.solutionium.core.designsystem.theme.onSurfaceLightHighContrast
import com.solutionium.core.designsystem.theme.onSurfaceLightMediumContrast
import com.solutionium.core.designsystem.theme.onSurfaceVariantDark
import com.solutionium.core.designsystem.theme.onSurfaceVariantDarkHighContrast
import com.solutionium.core.designsystem.theme.onSurfaceVariantDarkMediumContrast
import com.solutionium.core.designsystem.theme.onSurfaceVariantLight
import com.solutionium.core.designsystem.theme.onSurfaceVariantLightHighContrast
import com.solutionium.core.designsystem.theme.onSurfaceVariantLightMediumContrast
import com.solutionium.core.designsystem.theme.onTertiaryContainerDark
import com.solutionium.core.designsystem.theme.onTertiaryContainerDarkHighContrast
import com.solutionium.core.designsystem.theme.onTertiaryContainerDarkMediumContrast
import com.solutionium.core.designsystem.theme.onTertiaryContainerLight
import com.solutionium.core.designsystem.theme.onTertiaryContainerLightHighContrast
import com.solutionium.core.designsystem.theme.onTertiaryContainerLightMediumContrast
import com.solutionium.core.designsystem.theme.onTertiaryDark
import com.solutionium.core.designsystem.theme.onTertiaryDarkHighContrast
import com.solutionium.core.designsystem.theme.onTertiaryDarkMediumContrast
import com.solutionium.core.designsystem.theme.onTertiaryLight
import com.solutionium.core.designsystem.theme.onTertiaryLightHighContrast
import com.solutionium.core.designsystem.theme.onTertiaryLightMediumContrast
import com.solutionium.core.designsystem.theme.outlineDark
import com.solutionium.core.designsystem.theme.outlineDarkHighContrast
import com.solutionium.core.designsystem.theme.outlineDarkMediumContrast
import com.solutionium.core.designsystem.theme.outlineLight
import com.solutionium.core.designsystem.theme.outlineLightHighContrast
import com.solutionium.core.designsystem.theme.outlineLightMediumContrast
import com.solutionium.core.designsystem.theme.outlineVariantDark
import com.solutionium.core.designsystem.theme.outlineVariantDarkHighContrast
import com.solutionium.core.designsystem.theme.outlineVariantDarkMediumContrast
import com.solutionium.core.designsystem.theme.outlineVariantLight
import com.solutionium.core.designsystem.theme.outlineVariantLightHighContrast
import com.solutionium.core.designsystem.theme.outlineVariantLightMediumContrast
import com.solutionium.core.designsystem.theme.primaryContainerDark
import com.solutionium.core.designsystem.theme.primaryContainerDarkHighContrast
import com.solutionium.core.designsystem.theme.primaryContainerDarkMediumContrast
import com.solutionium.core.designsystem.theme.primaryContainerLight
import com.solutionium.core.designsystem.theme.primaryContainerLightHighContrast
import com.solutionium.core.designsystem.theme.primaryContainerLightMediumContrast
import com.solutionium.core.designsystem.theme.primaryDark
import com.solutionium.core.designsystem.theme.primaryDarkHighContrast
import com.solutionium.core.designsystem.theme.primaryDarkMediumContrast
import com.solutionium.core.designsystem.theme.primaryLight
import com.solutionium.core.designsystem.theme.primaryLightHighContrast
import com.solutionium.core.designsystem.theme.primaryLightMediumContrast
import com.solutionium.core.designsystem.theme.scrimDark
import com.solutionium.core.designsystem.theme.scrimDarkHighContrast
import com.solutionium.core.designsystem.theme.scrimDarkMediumContrast
import com.solutionium.core.designsystem.theme.scrimLight
import com.solutionium.core.designsystem.theme.scrimLightHighContrast
import com.solutionium.core.designsystem.theme.scrimLightMediumContrast
import com.solutionium.core.designsystem.theme.secondaryContainerDark
import com.solutionium.core.designsystem.theme.secondaryContainerDarkHighContrast
import com.solutionium.core.designsystem.theme.secondaryContainerDarkMediumContrast
import com.solutionium.core.designsystem.theme.secondaryContainerLight
import com.solutionium.core.designsystem.theme.secondaryContainerLightHighContrast
import com.solutionium.core.designsystem.theme.secondaryContainerLightMediumContrast
import com.solutionium.core.designsystem.theme.secondaryDark
import com.solutionium.core.designsystem.theme.secondaryDarkHighContrast
import com.solutionium.core.designsystem.theme.secondaryDarkMediumContrast
import com.solutionium.core.designsystem.theme.secondaryLight
import com.solutionium.core.designsystem.theme.secondaryLightHighContrast
import com.solutionium.core.designsystem.theme.secondaryLightMediumContrast
import com.solutionium.core.designsystem.theme.surfaceBrightDark
import com.solutionium.core.designsystem.theme.surfaceBrightDarkHighContrast
import com.solutionium.core.designsystem.theme.surfaceBrightDarkMediumContrast
import com.solutionium.core.designsystem.theme.surfaceBrightLight
import com.solutionium.core.designsystem.theme.surfaceBrightLightHighContrast
import com.solutionium.core.designsystem.theme.surfaceBrightLightMediumContrast
import com.solutionium.core.designsystem.theme.surfaceContainerDark
import com.solutionium.core.designsystem.theme.surfaceContainerDarkHighContrast
import com.solutionium.core.designsystem.theme.surfaceContainerDarkMediumContrast
import com.solutionium.core.designsystem.theme.surfaceContainerHighDark
import com.solutionium.core.designsystem.theme.surfaceContainerHighDarkHighContrast
import com.solutionium.core.designsystem.theme.surfaceContainerHighDarkMediumContrast
import com.solutionium.core.designsystem.theme.surfaceContainerHighLight
import com.solutionium.core.designsystem.theme.surfaceContainerHighLightHighContrast
import com.solutionium.core.designsystem.theme.surfaceContainerHighLightMediumContrast
import com.solutionium.core.designsystem.theme.surfaceContainerHighestDark
import com.solutionium.core.designsystem.theme.surfaceContainerHighestDarkHighContrast
import com.solutionium.core.designsystem.theme.surfaceContainerHighestDarkMediumContrast
import com.solutionium.core.designsystem.theme.surfaceContainerHighestLight
import com.solutionium.core.designsystem.theme.surfaceContainerHighestLightHighContrast
import com.solutionium.core.designsystem.theme.surfaceContainerHighestLightMediumContrast
import com.solutionium.core.designsystem.theme.surfaceContainerLight
import com.solutionium.core.designsystem.theme.surfaceContainerLightHighContrast
import com.solutionium.core.designsystem.theme.surfaceContainerLightMediumContrast
import com.solutionium.core.designsystem.theme.surfaceContainerLowDark
import com.solutionium.core.designsystem.theme.surfaceContainerLowDarkHighContrast
import com.solutionium.core.designsystem.theme.surfaceContainerLowDarkMediumContrast
import com.solutionium.core.designsystem.theme.surfaceContainerLowLight
import com.solutionium.core.designsystem.theme.surfaceContainerLowLightHighContrast
import com.solutionium.core.designsystem.theme.surfaceContainerLowLightMediumContrast
import com.solutionium.core.designsystem.theme.surfaceContainerLowestDark
import com.solutionium.core.designsystem.theme.surfaceContainerLowestDarkHighContrast
import com.solutionium.core.designsystem.theme.surfaceContainerLowestDarkMediumContrast
import com.solutionium.core.designsystem.theme.surfaceContainerLowestLight
import com.solutionium.core.designsystem.theme.surfaceContainerLowestLightHighContrast
import com.solutionium.core.designsystem.theme.surfaceContainerLowestLightMediumContrast
import com.solutionium.core.designsystem.theme.surfaceDark
import com.solutionium.core.designsystem.theme.surfaceDarkHighContrast
import com.solutionium.core.designsystem.theme.surfaceDarkMediumContrast
import com.solutionium.core.designsystem.theme.surfaceDimDark
import com.solutionium.core.designsystem.theme.surfaceDimDarkHighContrast
import com.solutionium.core.designsystem.theme.surfaceDimDarkMediumContrast
import com.solutionium.core.designsystem.theme.surfaceDimLight
import com.solutionium.core.designsystem.theme.surfaceDimLightHighContrast
import com.solutionium.core.designsystem.theme.surfaceDimLightMediumContrast
import com.solutionium.core.designsystem.theme.surfaceLight
import com.solutionium.core.designsystem.theme.surfaceLightHighContrast
import com.solutionium.core.designsystem.theme.surfaceLightMediumContrast
import com.solutionium.core.designsystem.theme.surfaceVariantDark
import com.solutionium.core.designsystem.theme.surfaceVariantDarkHighContrast
import com.solutionium.core.designsystem.theme.surfaceVariantDarkMediumContrast
import com.solutionium.core.designsystem.theme.surfaceVariantLight
import com.solutionium.core.designsystem.theme.surfaceVariantLightHighContrast
import com.solutionium.core.designsystem.theme.surfaceVariantLightMediumContrast
import com.solutionium.core.designsystem.theme.tertiaryContainerDark
import com.solutionium.core.designsystem.theme.tertiaryContainerDarkHighContrast
import com.solutionium.core.designsystem.theme.tertiaryContainerDarkMediumContrast
import com.solutionium.core.designsystem.theme.tertiaryContainerLight
import com.solutionium.core.designsystem.theme.tertiaryContainerLightHighContrast
import com.solutionium.core.designsystem.theme.tertiaryContainerLightMediumContrast
import com.solutionium.core.designsystem.theme.tertiaryDark
import com.solutionium.core.designsystem.theme.tertiaryDarkHighContrast
import com.solutionium.core.designsystem.theme.tertiaryDarkMediumContrast
import com.solutionium.core.designsystem.theme.tertiaryLight
import com.solutionium.core.designsystem.theme.tertiaryLightHighContrast
import com.solutionium.core.designsystem.theme.tertiaryLightMediumContrast

private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)

private val mediumContrastLightColorScheme = lightColorScheme(
    primary = primaryLightMediumContrast,
    onPrimary = onPrimaryLightMediumContrast,
    primaryContainer = primaryContainerLightMediumContrast,
    onPrimaryContainer = onPrimaryContainerLightMediumContrast,
    secondary = secondaryLightMediumContrast,
    onSecondary = onSecondaryLightMediumContrast,
    secondaryContainer = secondaryContainerLightMediumContrast,
    onSecondaryContainer = onSecondaryContainerLightMediumContrast,
    tertiary = tertiaryLightMediumContrast,
    onTertiary = onTertiaryLightMediumContrast,
    tertiaryContainer = tertiaryContainerLightMediumContrast,
    onTertiaryContainer = onTertiaryContainerLightMediumContrast,
    error = errorLightMediumContrast,
    onError = onErrorLightMediumContrast,
    errorContainer = errorContainerLightMediumContrast,
    onErrorContainer = onErrorContainerLightMediumContrast,
    background = backgroundLightMediumContrast,
    onBackground = onBackgroundLightMediumContrast,
    surface = surfaceLightMediumContrast,
    onSurface = onSurfaceLightMediumContrast,
    surfaceVariant = surfaceVariantLightMediumContrast,
    onSurfaceVariant = onSurfaceVariantLightMediumContrast,
    outline = outlineLightMediumContrast,
    outlineVariant = outlineVariantLightMediumContrast,
    scrim = scrimLightMediumContrast,
    inverseSurface = inverseSurfaceLightMediumContrast,
    inverseOnSurface = inverseOnSurfaceLightMediumContrast,
    inversePrimary = inversePrimaryLightMediumContrast,
    surfaceDim = surfaceDimLightMediumContrast,
    surfaceBright = surfaceBrightLightMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestLightMediumContrast,
    surfaceContainerLow = surfaceContainerLowLightMediumContrast,
    surfaceContainer = surfaceContainerLightMediumContrast,
    surfaceContainerHigh = surfaceContainerHighLightMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestLightMediumContrast,
)

private val highContrastLightColorScheme = lightColorScheme(
    primary = primaryLightHighContrast,
    onPrimary = onPrimaryLightHighContrast,
    primaryContainer = primaryContainerLightHighContrast,
    onPrimaryContainer = onPrimaryContainerLightHighContrast,
    secondary = secondaryLightHighContrast,
    onSecondary = onSecondaryLightHighContrast,
    secondaryContainer = secondaryContainerLightHighContrast,
    onSecondaryContainer = onSecondaryContainerLightHighContrast,
    tertiary = tertiaryLightHighContrast,
    onTertiary = onTertiaryLightHighContrast,
    tertiaryContainer = tertiaryContainerLightHighContrast,
    onTertiaryContainer = onTertiaryContainerLightHighContrast,
    error = errorLightHighContrast,
    onError = onErrorLightHighContrast,
    errorContainer = errorContainerLightHighContrast,
    onErrorContainer = onErrorContainerLightHighContrast,
    background = backgroundLightHighContrast,
    onBackground = onBackgroundLightHighContrast,
    surface = surfaceLightHighContrast,
    onSurface = onSurfaceLightHighContrast,
    surfaceVariant = surfaceVariantLightHighContrast,
    onSurfaceVariant = onSurfaceVariantLightHighContrast,
    outline = outlineLightHighContrast,
    outlineVariant = outlineVariantLightHighContrast,
    scrim = scrimLightHighContrast,
    inverseSurface = inverseSurfaceLightHighContrast,
    inverseOnSurface = inverseOnSurfaceLightHighContrast,
    inversePrimary = inversePrimaryLightHighContrast,
    surfaceDim = surfaceDimLightHighContrast,
    surfaceBright = surfaceBrightLightHighContrast,
    surfaceContainerLowest = surfaceContainerLowestLightHighContrast,
    surfaceContainerLow = surfaceContainerLowLightHighContrast,
    surfaceContainer = surfaceContainerLightHighContrast,
    surfaceContainerHigh = surfaceContainerHighLightHighContrast,
    surfaceContainerHighest = surfaceContainerHighestLightHighContrast,
)

private val mediumContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkMediumContrast,
    onPrimary = onPrimaryDarkMediumContrast,
    primaryContainer = primaryContainerDarkMediumContrast,
    onPrimaryContainer = onPrimaryContainerDarkMediumContrast,
    secondary = secondaryDarkMediumContrast,
    onSecondary = onSecondaryDarkMediumContrast,
    secondaryContainer = secondaryContainerDarkMediumContrast,
    onSecondaryContainer = onSecondaryContainerDarkMediumContrast,
    tertiary = tertiaryDarkMediumContrast,
    onTertiary = onTertiaryDarkMediumContrast,
    tertiaryContainer = tertiaryContainerDarkMediumContrast,
    onTertiaryContainer = onTertiaryContainerDarkMediumContrast,
    error = errorDarkMediumContrast,
    onError = onErrorDarkMediumContrast,
    errorContainer = errorContainerDarkMediumContrast,
    onErrorContainer = onErrorContainerDarkMediumContrast,
    background = backgroundDarkMediumContrast,
    onBackground = onBackgroundDarkMediumContrast,
    surface = surfaceDarkMediumContrast,
    onSurface = onSurfaceDarkMediumContrast,
    surfaceVariant = surfaceVariantDarkMediumContrast,
    onSurfaceVariant = onSurfaceVariantDarkMediumContrast,
    outline = outlineDarkMediumContrast,
    outlineVariant = outlineVariantDarkMediumContrast,
    scrim = scrimDarkMediumContrast,
    inverseSurface = inverseSurfaceDarkMediumContrast,
    inverseOnSurface = inverseOnSurfaceDarkMediumContrast,
    inversePrimary = inversePrimaryDarkMediumContrast,
    surfaceDim = surfaceDimDarkMediumContrast,
    surfaceBright = surfaceBrightDarkMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkMediumContrast,
    surfaceContainerLow = surfaceContainerLowDarkMediumContrast,
    surfaceContainer = surfaceContainerDarkMediumContrast,
    surfaceContainerHigh = surfaceContainerHighDarkMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkMediumContrast,
)

private val highContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkHighContrast,
    onPrimary = onPrimaryDarkHighContrast,
    primaryContainer = primaryContainerDarkHighContrast,
    onPrimaryContainer = onPrimaryContainerDarkHighContrast,
    secondary = secondaryDarkHighContrast,
    onSecondary = onSecondaryDarkHighContrast,
    secondaryContainer = secondaryContainerDarkHighContrast,
    onSecondaryContainer = onSecondaryContainerDarkHighContrast,
    tertiary = tertiaryDarkHighContrast,
    onTertiary = onTertiaryDarkHighContrast,
    tertiaryContainer = tertiaryContainerDarkHighContrast,
    onTertiaryContainer = onTertiaryContainerDarkHighContrast,
    error = errorDarkHighContrast,
    onError = onErrorDarkHighContrast,
    errorContainer = errorContainerDarkHighContrast,
    onErrorContainer = onErrorContainerDarkHighContrast,
    background = backgroundDarkHighContrast,
    onBackground = onBackgroundDarkHighContrast,
    surface = surfaceDarkHighContrast,
    onSurface = onSurfaceDarkHighContrast,
    surfaceVariant = surfaceVariantDarkHighContrast,
    onSurfaceVariant = onSurfaceVariantDarkHighContrast,
    outline = outlineDarkHighContrast,
    outlineVariant = outlineVariantDarkHighContrast,
    scrim = scrimDarkHighContrast,
    inverseSurface = inverseSurfaceDarkHighContrast,
    inverseOnSurface = inverseOnSurfaceDarkHighContrast,
    inversePrimary = inversePrimaryDarkHighContrast,
    surfaceDim = surfaceDimDarkHighContrast,
    surfaceBright = surfaceBrightDarkHighContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkHighContrast,
    surfaceContainerLow = surfaceContainerLowDarkHighContrast,
    surfaceContainer = surfaceContainerDarkHighContrast,
    surfaceContainerHigh = surfaceContainerHighDarkHighContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkHighContrast,
)

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color
)

val unspecified_scheme = ColorFamily(
    Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable() () -> Unit
) {
  val colorScheme = when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
          val context = LocalContext.current
          if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }
      
      darkTheme -> darkScheme
      else -> lightScheme
  }

  MaterialTheme(
    colorScheme = colorScheme,
    //typography = AppTypography,
    content = content
  )
}

