package com.yoinerdev.quizzesia.domain.repository

import com.yoinerdev.quizzesia.data.dto.CreateRoomDTO
import com.yoinerdev.quizzesia.data.dto.CreateRoomResponse

interface ISocketRepository {
    fun connect()
    fun disconnect()
    fun on(event: String, callback: (Array<Any>) -> Unit)
    fun emit(event: String, data: Any, ack:(Array<out Any>)->Unit)
    fun isConnected(): Boolean
    suspend fun createRoom(data: CreateRoomDTO): CreateRoomResponse
}