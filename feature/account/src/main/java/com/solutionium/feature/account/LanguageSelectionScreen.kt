package com.solutionium.feature.account

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// In your core UI or a dedicated feature module
@Composable
fun LanguageSelectionScreen(
    currentLang: String = "fa",
    onLanguageSelected: (String) -> Unit
) {
    Surface(modifier = Modifier
        .fillMaxSize()
        .padding(32.dp)) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Choose your language / زبان خود را انتخاب کنید",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(32.dp))
            LanguageButton(
                text = "English",
                languageCode = "en",
                isSelected = currentLang == "en",
                onClick = { onLanguageSelected("en") }
            )

            Spacer(Modifier.height(16.dp))

            // Farsi Button
            LanguageButton(
                text = "فارسی",
                languageCode = "fa",
                isSelected = currentLang == "fa",
                onClick = { onLanguageSelected("fa") }
            )
        }
    }
}

@Composable
private fun LanguageButton(
    text: String,
    languageCode: String,
    isSelected: Boolean,
    onClick: (String) -> Unit
) {
    val colors = if (isSelected) {
        // Highlighted state: Filled with primary color
        ButtonDefaults.outlinedButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    } else {
        // Default state: Transparent with primary text
        ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.primary
        )
    }

    OutlinedButton(
        onClick = { onClick(languageCode) },
        modifier = Modifier.fillMaxWidth(),
        colors = colors,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
    ) {
        Text(text)
    }
}