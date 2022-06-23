package com.example.shortcaserc.utils

class Utils {
    fun dateFormatter(day: Int, month: Int, year: Int): String {
        var formatDay = if (day >= 10) {
            "$day"
        } else {
            "0$day"
        }
        var formatMonth = if (month >= 10) {
            "$month"
        } else {
            "0$month"
        }
        return "$formatDay.$formatMonth.$year"
    }
}