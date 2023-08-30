package com.np.androidexperiments.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Tasks
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.np.androidexperiments.Message
import com.np.androidexperiments.Notification
import com.np.androidexperiments.SendMessageApi
import com.np.androidexperiments.SendMessageDataSourceImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit

class MainViewModel : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> get() = _state

    private val _action = MutableSharedFlow<MainAction>()
    val action: SharedFlow<MainAction> get() = _action

    @OptIn(ExperimentalSerializationApi::class)
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
        .baseUrl("https://fcm.googleapis.com/v1/projects/androidexperiments-a2cd1/")
        .build()

    private val service = retrofit.create(SendMessageApi::class.java)

    private val dataSource = SendMessageDataSourceImpl(service)

    fun processIntent(intent: MainIntent) {
        viewModelScope.launch {
            when (intent) {
                MainIntent.LoadImageUrlClicked -> {
                    _action.emit(MainAction.Toast("StartLoading..."))

                    // loading picture
                    delay(2000)
                    val uri = "uri://blablabla"
                    _state.emit(_state.value.copy(pictureUri = uri))

                    _action.emit(MainAction.Notification("Loaded", uri))

                }
                MainIntent.SendNotifViaApiButtonClicked -> {
                    withContext(Dispatchers.IO) {
                        delay(2000)
                        val token = Tasks.await(FirebaseMessaging.getInstance().token)
                        dataSource.sendMessage(
                            Message(
                                name = "asdfasdf",
                                data = mapOf("a" to "1", "b" to "2"),
                                notification = Notification(
                                    title = "123",
                                    body = "456 789 012 345",
                                    image = "",
                                ),
                                token = token,
                                topic = "",
                                condition = ""
                            )
                        )
                    }
                }
                MainIntent.SendNotifViaFcmButtonClicked -> {
                    withContext(Dispatchers.IO) {
                        delay(2000)
                        val token = Tasks.await(FirebaseMessaging.getInstance().token)
                        val message = RemoteMessage.Builder(token)
                            .addData("score", "850")
                            .addData("time", "2:45")
                            .build()

                        FirebaseMessaging.getInstance().send(message)
                    }
                }
            }
        }
    }
}

data class MainState(val pictureUri: String = "")

sealed interface MainAction {
    data class Toast(val msg: String) : MainAction
    data class Notification(val title: String, val msg: String) : MainAction
}

sealed interface MainIntent {
    object LoadImageUrlClicked : MainIntent
    object SendNotifViaFcmButtonClicked : MainIntent
    object SendNotifViaApiButtonClicked : MainIntent
}