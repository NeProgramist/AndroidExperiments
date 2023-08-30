package com.np.androidexperiments

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.http.Body
import retrofit2.http.POST

@Serializable
data class SendMessageBody(
    @SerialName("validate_only")
    val validateOnly: Boolean,
    @SerialName("message")
    val message: Message,
)

@Serializable
data class Message(
    @SerialName("name")
    val name: String,
    @SerialName("data")
    val data: Map<String, String>,
    @SerialName("notification")
    val notification: Notification,
    @SerialName("token")
    val token: String,
    @SerialName("topic")
    val topic: String,
    @SerialName("condition")
    val condition: String,
)

@Serializable
data class Notification(
    @SerialName("title")
    val title: String,
    @SerialName("body")
    val body: String,
    @SerialName("image")
    val image: String,
)

interface SendMessageApi {

    @POST("send")
    suspend fun sendMessage(@Body body: SendMessageBody): Message
}
