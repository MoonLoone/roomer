package com.example.roomer.data.remote

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class ChatClientWebSocket: WebSocketListener() {

    private var socket: WebSocket? = null

    fun open(){
        val client = OkHttpClient()
        val request = Request.Builder().url(BASE_URL).build()
        socket = client.newWebSocket(request, this)
        Log.d("!!!", "Opened")
    }

    fun sendMessage(message:String){
        socket?.send(message)
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
        Log.d("!!!", "Disconnected^ $code")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        Log.d("!!!", text)
    }

    companion object{
        private const val BASE_URL = "ws://176.113.83.93:8000/ws"
    }

}