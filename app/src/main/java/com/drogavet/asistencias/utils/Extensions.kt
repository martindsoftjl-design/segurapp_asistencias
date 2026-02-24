package com.drogavet.asistencias.utils

fun String.capitalizeWords(): String = split(" ").joinToString(" ") { it.replaceFirstChar { c -> c.uppercase() } }
