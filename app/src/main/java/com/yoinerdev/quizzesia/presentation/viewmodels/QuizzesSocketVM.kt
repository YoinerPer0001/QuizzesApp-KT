package com.yoinerdev.quizzesia.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.yoinerdev.quizzesia.core.alerts.AuthUiState
import com.yoinerdev.quizzesia.core.alerts.SocketState
import com.yoinerdev.quizzesia.data.dto.ActualQuestionResponse
import com.yoinerdev.quizzesia.data.dto.ApiResponse
import com.yoinerdev.quizzesia.data.dto.CheckQuestionReq
import com.yoinerdev.quizzesia.data.dto.CheckQuestionResp
import com.yoinerdev.quizzesia.data.dto.CreateRoomDTO
import com.yoinerdev.quizzesia.data.dto.CreateRoomResponse
import com.yoinerdev.quizzesia.data.dto.DataRoom
import com.yoinerdev.quizzesia.data.dto.FinishedResponse
import com.yoinerdev.quizzesia.data.dto.GameStartedResp
import com.yoinerdev.quizzesia.data.dto.GetQuestionReq
import com.yoinerdev.quizzesia.data.dto.HostLeaveResponse
import com.yoinerdev.quizzesia.data.dto.JoinRequest
import com.yoinerdev.quizzesia.data.dto.OnJoinNewPlayer
import com.yoinerdev.quizzesia.data.dto.PlayerLeaveReq
import com.yoinerdev.quizzesia.data.dto.PlayerLeaveResponse
import com.yoinerdev.quizzesia.data.dto.Players
import com.yoinerdev.quizzesia.data.dto.Question
import com.yoinerdev.quizzesia.domain.repository.ISocketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.socket.client.Ack
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class QuizzesSocketVM @Inject constructor(
    private val socketRepo: ISocketRepository
) : ViewModel() {

    private val state_ = MutableStateFlow<SocketState>(SocketState.Idle)
    val state: StateFlow<SocketState> = state_

    private val playerLeave_ = MutableStateFlow<String>("")
    val playerLeave: StateFlow<String> = playerLeave_

    private val roomData_ = MutableStateFlow<DataRoom?>(null)
    val roomData: StateFlow<DataRoom?> = roomData_

    private val actualQuestions_ = MutableStateFlow<ActualQuestionResponse?>(null)
    val actualQuestions: StateFlow<ActualQuestionResponse?> = actualQuestions_

    private val userScore_ = MutableStateFlow<CheckQuestionResp?>(null)
    val userScore: StateFlow<CheckQuestionResp?> = userScore_

    fun onCharge() {
        viewModelScope.launch {
            try {

                state_.emit(SocketState.IsLoading)
                socketRepo.connect()
                state_.emit(SocketState.SuccessConnected)

            } catch (e: Exception) {
                Log.d("Error to connect", e.message.toString())
                state_.emit(SocketState.ErrorToConnect)
            } finally {
                state_.emit(SocketState.Idle)
            }
        }
    }

    fun disconnect() {
        viewModelScope.launch {
            try {
                socketRepo.disconnect()
            } catch (e: Exception) {
                Log.d("Error to disconnect", e.message.toString())
            }
        }
    }

    fun onPlayerJoin() {
        viewModelScope.launch {

            try {
                socketRepo.on("new_joined") { args ->
                    val response = args[0] as JSONObject
                    val result = Gson().fromJson(response.toString(), OnJoinNewPlayer::class.java)
                    roomData_.update { currentRoom ->
                        currentRoom?.copy(players = result.players)
                    }
                }
            } catch (e: Exception) {
                Log.d("Error to disconnect", e.message.toString())
            }

        }
    }

    fun onGameStarted() {
        viewModelScope.launch {

            try {
                socketRepo.on("game_Started") { args ->
                    val response = args[0] as JSONObject
                    val result = Gson().fromJson(response.toString(), GameStartedResp::class.java)
                    if (result.success) {
                        state_.value = SocketState.SuccessStartGame
                    }
                }

                socketRepo.on("game_finished") { args ->
                    val response = args[0] as JSONObject
                    val result = Gson().fromJson(response.toString(), FinishedResponse::class.java)
                    roomData_.update { currentRoom ->
                        currentRoom?.copy(players = result.players.sortedByDescending { it.score })
                    }
                   viewModelScope.launch {
                       delay(2000)
                       state_.value = SocketState.SuccesFinishedGame
                   }
                }

            } catch (e: Exception) {
                Log.d("Error to disconnect", e.message.toString())
            }

        }
    }

    fun JoinToRoom(code: String) {
        viewModelScope.launch {
            viewModelScope.launch {
                state_.emit(SocketState.IsLoading)
                try {
                    val token =
                        FirebaseAuth.getInstance().currentUser?.getIdToken(true)?.await()?.token
                    val data = JoinRequest(code, token ?: "")

                    val json = JSONObject(Gson().toJson(data))
                    socketRepo.emit("join_server", json) { args ->
                        val response = args[0] as JSONObject
                        val result = Gson().fromJson(response.toString(), ApiResponse::class.java)
                        if (result.code.toInt() != 201) {
                            state_.value = SocketState.ErrorToJoin
                            return@emit
                        }
                        roomData_.value = result.data
                        Log.d("JOIN DATA", roomData_.value.toString())
                        state_.value = SocketState.SuccessJoined
                    }
                } catch (e: Exception) {
                    Log.d("Error to disconnect", e.message.toString())
                    state_.value = SocketState.ErrorToJoin
                } finally {
                    delay(50)
                    state_.emit(SocketState.Idle)
                }
            }
        }
    }

    fun createRoom(quizId: String) {
        viewModelScope.launch {
            try {
                state_.emit(SocketState.IsLoading)
                val token =
                    FirebaseAuth.getInstance().currentUser?.getIdToken(false)?.await()?.token
                val data = CreateRoomDTO(
                    quiz_id = quizId,
                    type = "private",
                    token = token ?: ""
                )
                Log.d("Data", data.toString())
                if (socketRepo.isConnected()) {
                    val result = socketRepo.createRoom(data)

                    if (result.code.toInt() != 200) {
                        state_.emit(SocketState.ErrorToCreateRoom)
                        return@launch
                    }

                    roomData_.emit(result.data)
                    onPlayerJoin() //listen new players
                    delay(50)
                    state_.emit(SocketState.SuccessCreatedRoom)

                } else {
                    delay(50)
                    state_.emit(SocketState.ErrorToConnect)
                }

            } catch (e: Exception) {
                Log.d("Error to connect", e.message.toString())
                state_.emit(SocketState.ErrorToCreateRoom)
            } finally {
                delay(50)
                state_.emit(SocketState.Idle)
            }
        }
    }

    fun LeaveRoom() {
        viewModelScope.launch {
            try {
                val token = FirebaseAuth.getInstance().currentUser?.getIdToken(true)?.await()?.token
                val data = PlayerLeaveReq(token = token ?: "", roomData_.value?.roomCode ?: "")
                val json = Gson().toJson(data)
                socketRepo.emit("leave_room", json) {}
                state_.emit(SocketState.SuccessPlayerLeave)

            } catch (e: Exception) {
                Log.d("Error to connect", e.message.toString())
                state_.emit(SocketState.ErrorPlayerLeave)
            } finally {
                delay(50)
                state_.emit(SocketState.Idle)
            }
        }
    }

    fun onPlayerLeaveRoom() {
        viewModelScope.launch {
            try {

                socketRepo.on("host_leave") { args ->
                    val response = args[0] as JSONObject
                    val result = Gson().fromJson(response.toString(), HostLeaveResponse::class.java)
                    playerLeave_.value = result.name
                    state_.value = SocketState.HostLeave
                }

                socketRepo.on("player_leave") { args ->
                    val response = args[0] as JSONObject
                    val result =
                        Gson().fromJson(response.toString(), PlayerLeaveResponse::class.java)
                    roomData_.update { currentRoom ->
                        currentRoom?.copy(players = result.players)
                    }
                    playerLeave_.value = result.name
                    state_.value = SocketState.PlayerLeave

                }

                socketRepo.on("insuficient_participants") {
                    playerLeave_.value = ""
                    state_.value = SocketState.InsParticipants
                }

            } catch (e: Exception) {

                state_.value = SocketState.ErrorPlayerLeave
            } finally {
                delay(50)
                state_.value = SocketState.Idle
            }
        }
    }

    fun getQuestion() {
        viewModelScope.launch {
            try {
                val token = FirebaseAuth.getInstance().currentUser?.getIdToken(true)?.await()?.token
                val data =
                    GetQuestionReq(code = roomData_.value?.roomCode ?: "", token = token ?: "")
                val json = Gson().toJson(data)
                socketRepo.emit("get_question", json) {}
                state_.emit(SocketState.SuccessGetQuestion)

            } catch (e: Exception) {
                state_.emit(SocketState.ErrorToGetQuestion)
            } finally {
                delay(50)
                state_.emit(SocketState.Idle)
            }
        }
    }


    fun questionListener() {
        viewModelScope.launch {
            try {
                socketRepo.on("actual_question") { args ->
                    val response = args[0] as JSONObject
                    val result =
                        Gson().fromJson(response.toString(), ActualQuestionResponse::class.java)
                    actualQuestions_.value = result
                }

            } catch (e: Exception) {
                state_.emit(SocketState.ErrorToGetQuestion)
            } finally {
                delay(50)
                state_.emit(SocketState.Idle)
            }
        }
    }

    fun startGame() {
        viewModelScope.launch {
            state_.emit(SocketState.IsLoading)
            try {

                if (roomData_.value?.players?.size!! > 1) {
                    val token =
                        FirebaseAuth.getInstance().currentUser?.getIdToken(true)?.await()?.token
                    val data =
                        GetQuestionReq(token = token ?: "", code = roomData_.value?.roomCode ?: "")
                    val json = Gson().toJson(data)
                    socketRepo.emit("start_Game", json) { args ->
                        state_.value = SocketState.ErrorStartGame
                    }
                } else {
                    state_.value = SocketState.InsParticipants
                }


            } catch (e: Exception) {
                state_.emit(SocketState.ErrorToCheckQuestion)
            } finally {
                delay(50)
                state_.emit(SocketState.Idle)
            }
        }
    }

    fun createRoomSinglePlayer(quizId: String, token:String) {
        viewModelScope.launch {
            try {

                val data = CreateRoomDTO(
                    quiz_id = quizId,
                    type = "private",
                    token = token ?: ""
                )
                if (socketRepo.isConnected()) {
                    val result = socketRepo.createRoom(data)

                    if (result.code.toInt() != 200) {
                        state_.emit(SocketState.ErrorToCreateRoom)
                        return@launch
                    }

                    roomData_.emit(result.data)
                    delay(50)
                    state_.emit(SocketState.SuccessCreatedSinglePlayer)

                } else {
                    delay(50)
                    state_.emit(SocketState.ErrorToConnect)
                }

            } catch (e: Exception) {
                Log.d("Error to connect", e.message.toString())
                state_.emit(SocketState.ErrorToCreateRoom)
            } finally {
                delay(50)
                state_.emit(SocketState.Idle)
            }
        }
    }

    fun startSingleGame(quizId: String) {
        viewModelScope.launch {
            state_.emit(SocketState.IsLoading)
            try {

                val token = FirebaseAuth.getInstance().currentUser?.getIdToken(true)?.await()?.token
                createRoomSinglePlayer(quizId, token ?: "")
                val data =
                    GetQuestionReq(token = token ?: "", code = roomData_.value?.roomCode ?: "")

                val json = Gson().toJson(data)
                socketRepo.emit("start_Game", json) { args ->
                    state_.value = SocketState.ErrorStartGame
                }

            } catch (e: Exception) {
                state_.emit(SocketState.ErrorToCheckQuestion)
            } finally {
                delay(50)
                state_.emit(SocketState.Idle)
            }
        }
    }

    fun checkQuestion(answerId: String?, percentTime: String) {
        viewModelScope.launch {
            try {
                val token = FirebaseAuth.getInstance().currentUser?.getIdToken(true)?.await()?.token
                val data = CheckQuestionReq(
                    code = roomData_.value?.roomCode ?: "",
                    token ?: "",
                    answerId,
                    percentTime
                )
                val json = Gson().toJson(data)
                socketRepo.emit("check_question", json) { args ->
                    val response = args[0] as JSONObject
                    val result = Gson().fromJson(response.toString(), CheckQuestionResp::class.java)
                    Log.d("result check", result.toString())

                    viewModelScope.launch {
                        if(result.isCorrect){

                            state_.value = SocketState.SuccessAnswer
                            delay(2000)
                        }else{

                            state_.value = SocketState.ErrorAnswer
                            delay(2000)
                        }
                        userScore_.value = result
                        state_.value = SocketState.SuccessCheckQuestion
                    }

                }

            } catch (e: Exception) {
                state_.emit(SocketState.ErrorToCheckQuestion)
            } finally {
                delay(50)
                state_.emit(SocketState.Idle)
            }
        }
    }

}