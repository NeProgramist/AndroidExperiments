package com.np.androidexperiments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> get() = _state

    private val _action = MutableSharedFlow<MainAction>()
    val action: SharedFlow<MainAction> get() = _action

    fun processIntent(intent: MainIntent) {
        viewModelScope.launch {
            when (intent) {
                MainIntent.ButtonClicked -> {
                    _action.emit(MainAction.Toast("StartLoading..."))

                    // loading picture
                    delay(2000)
                    val uri = "uri://blablabla"
                    _state.emit(_state.value.copy(pictureUri = uri))

                    _action.emit(MainAction.Notification("Loaded", uri))

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
    object ButtonClicked : MainIntent
}