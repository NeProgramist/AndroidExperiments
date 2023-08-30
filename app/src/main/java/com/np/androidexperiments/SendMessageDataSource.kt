package com.np.androidexperiments



interface SendMessageDataSource {
    suspend fun sendMessage(message: Message)
}

class SendMessageDataSourceImpl(private val api: SendMessageApi) : SendMessageDataSource {
    override suspend fun sendMessage(message: Message) {
        api.sendMessage(SendMessageBody(validateOnly = false, message = message))
    }
}
