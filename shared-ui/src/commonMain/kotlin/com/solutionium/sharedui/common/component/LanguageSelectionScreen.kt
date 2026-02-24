package com.solutionium.sharedui.common.component


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


// In your core UI or a dedicated feature module
@Composable
fun LanguageSelectionScreen(
    currentLang: String = "",
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
