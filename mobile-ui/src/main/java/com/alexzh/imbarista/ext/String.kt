package com.alexzh.imbarista.ext

import android.util.Patterns

fun String.isValidEmail(): Boolean {
    val matcher = Patterns.EMAIL_ADDRESS.matcher(this)
    return matcher.matches()
}