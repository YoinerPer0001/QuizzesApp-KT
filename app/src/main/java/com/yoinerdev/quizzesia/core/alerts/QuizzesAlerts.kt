package com.yoinerdev.quizzesia.core.alerts

sealed class QuizzesAlerts {
    object Idle : QuizzesAlerts()
    object IsLoading : QuizzesAlerts()
    object SuccessCreated : QuizzesAlerts()
    object ErrortoCreate : QuizzesAlerts()
    object SuccessDataCharged : QuizzesAlerts()
    object ErrorToChargeData : QuizzesAlerts()
    object ErrorToGetQuizzesUser : QuizzesAlerts()
    object SuccessToGetQuizzesUser : QuizzesAlerts()
    object SuccessToGetQuizzesPublic : QuizzesAlerts()
    object ErrorToGetQuizzesPublic : QuizzesAlerts()
    object SuccessTogetAttemptsQuizz:QuizzesAlerts()
    object ErrorTogetAttemptsQuizz:QuizzesAlerts()
}

