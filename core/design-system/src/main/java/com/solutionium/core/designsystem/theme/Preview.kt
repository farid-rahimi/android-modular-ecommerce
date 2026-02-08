package com.solutionium.core.designsystem.theme


import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable

@Composable
fun PreviewWooTheme(content: @Composable () -> Unit) {
    WooTheme {
        Surface {
            content()
        }
    }
}
