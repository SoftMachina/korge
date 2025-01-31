package com.soywiz.klock.wrapped

import com.soywiz.klock.Month
import com.soywiz.klock.annotations.KlockExperimental

@KlockExperimental
val Month.wrapped get() = this
@KlockExperimental
val WMonth.value get() = this
@KlockExperimental
fun WMonth(value: Month) = value

// An enum, thus not inline
typealias WMonth = Month
