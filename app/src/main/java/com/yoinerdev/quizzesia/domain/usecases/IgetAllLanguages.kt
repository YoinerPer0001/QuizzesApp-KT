package com.yoinerdev.quizzesia.domain.usecases

interface IgetAllLanguages {
    suspend operator fun invoke(): List<Pair<String, String>>
}