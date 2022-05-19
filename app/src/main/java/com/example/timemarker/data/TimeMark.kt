package com.example.timemarker.data

import java.time.LocalDateTime

data class TimeMark(
    val id: Long,
    val name: String,
    val localDateTime: LocalDateTime,
)