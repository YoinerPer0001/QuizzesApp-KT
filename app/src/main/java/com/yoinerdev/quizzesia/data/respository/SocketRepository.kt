package com.yoinerdev.quizzesia.data.respository

import android.util.Log
import com.google.gson.Gson
import com.yoinerdev.quizzesia.data.dto.CreateRoomDTO
import com.yoinerdev.quizzesia.data.dto.CreateRoomResponse
import com.yoinerdev.quizzesia.domain.repository.ISocketRepository
import io.socket.client.Ack
import io.socket.client.Socket
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import org.json.JSONObject
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class SocketRepository @Inject constructor(
    private val socket: Socket
) : ISocketRepository {

    override fun connect() {
        socket.connect()
    }

    override fun disconnect() {
        socket.disconnect()
    }

    override fun on(event: String, callback: (Array<Any>) -> Unit) {
        socket.on(event) { args -> callback(args) }
    }

    override fun emit(event: String, data: Any, ack: (Array<out Any>) -> Unit) {
        socket.emit(event, data, Ack { args -> ack(args) })
    }

    override fun isConnected(): Boolean = socket.connected()


    override suspend fun createRoom(data: CreateRoomDTO): CreateRoomResponse =
        suspendCancellableCoroutine { cont ->
            val json = JSONObject(Gson().toJson(data))
            socket.emit("create_room", json, Ack { args ->

                if (args.isNotEmpty()) {
                    val response = args[0] as JSONObject
                    Log.d("DATA", response.toString())
                    val result =
                        Gson().fromJson(response.toString(), CreateRoomResponse::class.java)
                    cont.resume(result)
                } else {
                    cont.resumeWithException(Exception("Sin respuesta del servidor"))
                }

            })


        }


}