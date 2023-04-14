package com.example.roomer.data.remote

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONObject

class ChatClientWebSocket(private val onMessageReceived: (String) -> Unit) : WebSocketListener() {

    var socket: WebSocket? = null

    fun open(currentUserId: Int, recipientUserId: Int) {
        socket?.let {
            return
        }
        val client = OkHttpClient()
        val request =
            Request.Builder().url("$BASE_URL/$currentUserId/$recipientUserId/").build()
        socket = client.newWebSocket(request, this)
    }

    fun sendMessage(messageJson: JSONObject) {
        socket?.let { socket!!.send(messageJson.toString()) }
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
        socket?.close(code, reason)
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        onMessageReceived.invoke(text)
    }

    companion object {
        private const val BASE_URL = "ws://176.113.83.93:8000/ws/chat"
    }
}
