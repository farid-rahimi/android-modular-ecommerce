package com.solutionium.core.ui.common.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.solutionium.data.model.ProductAttribute

@Composable
fun ProductAttributes(attributes: List<ProductAttribute>, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(horizontal = 16.dp)) {
        val scentNotes = attributes.filter { it.name.lowercase().contains("notes") }
        if (scentNotes.isNotEmpty()) {
            Text(
                text = "Scent Notes",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium)
            )
            Spacer(modifier = Modifier.height(4.dp))
            scentNotes.forEach { attr ->
                Text(
                    text = "${attr.name}: ${attr.options.joinToString(", ")}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        val otherAttributes = attributes.filterNot { it.name.lowercase().contains("notes") }
        if (otherAttributes.isNotEmpty()) {
            Text(
                text = "Details",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium)
            )
            Spacer(modifier = Modifier.height(4.dp))
            otherAttributes.forEach { attr ->
                Text(
                    text = "${attr.name}: ${attr.options.joinToString(", ")}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}