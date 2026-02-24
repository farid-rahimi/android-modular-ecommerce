package com.solutionium.shared.util

import java.text.NumberFormat
import java.util.Locale

actual fun Double.toFormattedString(): String {
    val numberFormat = NumberFormat.getNumberInstance(Locale.US) // Or your desired Locale
    // Check if the number has a fractional part.
    return if (this % 1 == 0.0) {
        // It's a whole number, format without decimal places.
        numberFormat.format(this.toLong())
    } else {
        // It has decimal places, format as is.
        numberFormat.format(this)
    }
}
