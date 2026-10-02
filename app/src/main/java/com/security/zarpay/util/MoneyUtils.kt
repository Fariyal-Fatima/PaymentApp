package com.security.zarpay.util

import java.math.BigDecimal
import java.math.RoundingMode

// "50.25" -> 5025. Invalid ya 2 se zyada decimals par null.
fun rupeesToPaise(input: String): Long? = try {
    BigDecimal(input.trim())
        .setScale(2, RoundingMode.UNNECESSARY)
        .movePointRight(2)
        .longValueExact()
} catch (e: ArithmeticException) {
    null
} catch (e: NumberFormatException) {
    null
}

// 5025 -> "₹50.25", -5025 -> "-₹50.25"
fun formatRupees(paise: Long): String {
    val abs = kotlin.math.abs(paise)
    val sign = if (paise < 0) "-" else ""
    return "$sign₹%d.%02d".format(abs / 100, abs % 100)
}