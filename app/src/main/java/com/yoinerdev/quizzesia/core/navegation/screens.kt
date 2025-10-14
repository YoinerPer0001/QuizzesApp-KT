package com.yoinerdev.quizzesia.core.navegation

import kotlinx.serialization.Serializable

@Serializable
object MainScreen

@Serializable
object LoginScreen

@Serializable
object RegisterEsc

@Serializable
object ResetPassEsc

@Serializable
object PerfilESC

@Serializable
data class CreateQuizzesScreen(val type:Int, val uri:String?)

@Serializable
data class ModesQuizScreen(val id:String)

@Serializable
object MyQuizzesEsc

@Serializable
object PublicQuizzesEsc

@Serializable
data class LobbyMultiplayerEsc(val quizId:String)

@Serializable
object JoinGameScreen

@Serializable
object LobbyOtherPlayersESC

@Serializable
data class QuizScreenESC(val typeQuiz:Int)

@Serializable
object PodiumScreenESC

@Serializable
object SocketGraph

@Serializable
data class AttemptsScreenESC(val quizId:String, val quizTitle:String)

@Serializable
object StartScreenESC

@Serializable
object SplasScreenESC