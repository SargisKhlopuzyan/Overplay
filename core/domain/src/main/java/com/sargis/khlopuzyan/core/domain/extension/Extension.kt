package com.sargis.khlopuzyan.core.domain.extension

import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs

fun Date.elapsedTimeInMinutes(): Long {
    val diffInMillis = abs(Date().time - this.time)
    return TimeUnit.MINUTES.convert(diffInMillis, TimeUnit.MILLISECONDS)
}