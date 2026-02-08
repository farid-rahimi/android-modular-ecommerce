package com.solutionium.feature.product.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.solutionium.core.ui.common.component.FormattedPriceV2
import com.solutionium.data.model.Decant

// In ProductDetailScreen.kt
// 1. Update the DecantSelectionSection composable
@Composable
fun DecantSelectionSection(
    productPrice: Double, // Pass the full bottle price
    decants: List<Decant>,
    selectedDecant: Decant?,
    onDecantSelected: (Decant) -> Unit,
    fullBottleAvailable: Boolean = true,
    onFullBottleSelected: () -> Unit,
    displayPrice: Boolean = false
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(R.string.decant_selection_title),
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                fontWeight = FontWeight.SemiBold
            )
            // Display the selected option
            val selectionText = selectedDecant?.size ?: stringResource(R.string.full_bottle)
            Text(
                text = ": $selectionText",
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
        Spacer(Modifier.height(12.dp))

        // Use FlowRow for better layout
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            //verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // "Full Bottle" Chip - selected if `selectedDecant` is null
            OptionChipWithPrice(
                primaryText = stringResource(R.string.full_bottle),
                isAvailable = fullBottleAvailable,
                price = productPrice, // Show full bottle price
                isSelected = (selectedDecant == null && fullBottleAvailable),
                onClick = onFullBottleSelected,
                displayPrice = displayPrice

            )

            // Decant Chips
            decants.forEach { decant ->
                OptionChipWithPrice(
                    primaryText = decant.size,
                    price = decant.price, // Show decant price
                    isSelected = selectedDecant == decant,
                    onClick = { onDecantSelected(decant) },
                    displayPrice = displayPrice
                )
            }
        }
    }
}

// 2. Create a new, more advanced OptionChip
@Composable
private fun OptionChipWithPrice(
    primaryText: String,
    price: Double,
    isSelected: Boolean,
    isAvailable: Boolean = true,
    onClick: () -> Unit,
    displayPrice: Boolean = true
) {
    val backgroundColor =
        if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
    val contentColor =
        if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
    val alpha = if (isAvailable) 1f else 0.4f

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .alpha(alpha)
            .clickable(enabled = isAvailable, onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(if (isSelected) 4.dp else 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = primaryText,
                color = contentColor,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
            if (isAvailable) {
                if (displayPrice)
                    FormattedPriceV2(
                        price.toLong(),
                        mainStyle = TextStyle(
                            color = contentColor,
                            fontSize = 14.sp,
                            //fontWeight = FontWeight.SemiBold
                        ),
                        magnifier = 0.8
                    )
            } else {
                Text(
                    text = stringResource(R.string.out_of_stock),
                    color = contentColor,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
//            Text(
//                text = secondaryText,
//                color = contentColor.copy(alpha = 0.8f),
//                style = MaterialTheme.typography.bodySmall,
//                fontWeight = FontWeight.Normal
//            )
        }
    }
}

