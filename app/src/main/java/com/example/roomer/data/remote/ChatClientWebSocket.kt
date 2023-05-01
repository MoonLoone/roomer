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

    fun open(currentUserId: Int, recipientUserId: Int): Boolean {
        socket?.let {
            return true
        }
        val client = OkHttpClient()
        val request =
            Request.Builder().url("$BASE_URL/$currentUserId/$recipientUserId/").build()
        socket = client.newWebSocket(request, this)
        socket?.let { return true }
        return false
    }

    fun sendMessage(messageJson: JSONObject) {
        socket?.let { socket!!.send(messageJson.toString()) }
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        Log.d("!!!", "Connection closed by $reason with code $code")
        super.onClosed(webSocket, code, reason)
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        Log.d("!!!", "Connection failure with response $response")
        super.onFailure(webSocket, t, response)
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        Log.d("!!!", "Connection opened")
        super.onOpen(webSocket, response)
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        Log.d("!!!", "Connection closed $reason")
        socket?.close(code, reason)
        super.onClosing(webSocket, code, reason)
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        onMessageReceived.invoke(text)
    }

    companion object {
        private const val BASE_URL = "ws://176.113.83.93:8000/ws/chat"
    }
}
