package com.yoinerdev.quizzesia.data.dto

data class CreateRoomDTO(
    val quiz_id: String,
    val token:String,
    val type: String,
)

data class CreateRoomResponse(
    val code: Long,
    val message:String,
    val data: DataRoom?,
)

data class DataRoom(
    val roomCode: String,
    val numberQuestions:Long,
    val hostId:String,
    var players : List<Players>
)

data class Players(
    val id:String,
    val name:String,
    val score:Long
)

data class OnJoinNewPlayer(
    val roomCode: String,
    val players: List<Players>
)

data class JoinRequest (
    val code:String,
    val token:String
)

data class ApiResponse(
    val code: Long,
    val message:String,
    val data: DataRoom?,
)

data class PlayerLeaveReq(
    val token :String,
    val roomCode : String
)

data class PlayerLeaveResponse(
    val name:String,
    val players: List<Players>
)

data class HostLeaveResponse(
    val name:String,
)

data class GetQuestionReq(
    val token :String,
    val code : String
)

data class ActualQuestionResponse(
    val hostId: String,
    val question: Question,
    val questionIndex: Long,
    val totalQuestions: Long,
    val totalPlayers: Long,
    val time: Int
)

data class FinishedResponse(
    val players : List<Players>
)

data class CheckQuestionReq(
    val code: String,
    val token:String,
    val answer_id:String?,
    val percent:String
)

data class CheckQuestionResp(
    val isCorrect:Boolean,
    val score: Long
)

data class GameStartedResp(
    val success:Boolean,
    val hostId: String
)

