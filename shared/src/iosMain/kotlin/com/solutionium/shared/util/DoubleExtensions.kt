package com.solutionium.shared.util

import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterDecimalStyle
import platform.Foundation.NSLocale
import platform.Foundation.localeWithLocaleIdentifier

actual fun Double.toFormattedString(): String {
    val formatter = NSNumberFormatter().apply {
        numberStyle = NSNumberFormatterDecimalStyle
        locale = NSLocale.localeWithLocaleIdentifier("en_US")
        minimumFractionDigits = 0u
        maximumFractionDigits = 2u // You can adjust this as needed
    }
    
    return if (this % 1 == 0.0) {
        formatter.maximumFractionDigits = 0u
        formatter.stringFromNumber(NSNumber(this)) ?: this.toString()
    } else {
        formatter.stringFromNumber(NSNumber(this)) ?: this.toString()
    }
}
