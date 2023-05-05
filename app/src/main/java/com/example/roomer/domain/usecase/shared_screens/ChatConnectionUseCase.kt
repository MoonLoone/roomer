package com.example.roomer.domain.usecase.shared_screens

import com.example.roomer.data.remote.ChatClientWebSocket
import com.example.roomer.data.repository.roomer_repository.RoomerRepositoryInterface

class ChatConnectionUseCase(roomerRepository: RoomerRepositoryInterface) {

    fun openConnection(
        chatClientWebSocket: ChatClientWebSocket,
        currentUserId: Int,
        recipientUserId: Int
    ) = chatClientWebSocket.open(currentUserId, recipientUserId)

    fun closeConnection(chatClientWebSocket: ChatClientWebSocket) = chatClientWebSocket.close()
}
