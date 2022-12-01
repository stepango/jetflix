package com.yasinkacmaz.jetflix.util.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

abstract class StatefulViewModel<State: Any> : ViewModel() {
    abstract val uiState: MutableStateFlow<State>

    var uiValue: State
        get() = uiState.value
        set(value) {
            uiState.value = value
        }
}