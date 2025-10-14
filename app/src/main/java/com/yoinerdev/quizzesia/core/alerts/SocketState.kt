package com.yoinerdev.quizzesia.core.alerts

sealed class SocketState {
    object Idle  : SocketState()
    object IsLoading : SocketState()
    object SuccessConnected : SocketState()
    object ErrorToConnect : SocketState()
    object SuccessCreatedRoom  : SocketState()
    object ErrorToCreateRoom  : SocketState()
    object SuccessJoined: SocketState()
    object ErrorToJoin : SocketState()
    object SuccessPlayerLeave: SocketState()
    object ErrorPlayerLeave : SocketState()
    object HostLeave : SocketState()
    object PlayerLeave : SocketState()
    object InsParticipants : SocketState()
    object SuccessGetQuestion : SocketState()
    object ErrorToGetQuestion : SocketState()
    object SuccessCheckQuestion : SocketState()
    object ErrorToCheckQuestion : SocketState()
    object SuccessStartGame : SocketState()
    object ErrorStartGame : SocketState()
    object SuccesFinishedGame: SocketState()
    object SuccessCreatedSinglePlayer  : SocketState()
    object SuccessAnswer  : SocketState()
    object ErrorAnswer  : SocketState()

}
