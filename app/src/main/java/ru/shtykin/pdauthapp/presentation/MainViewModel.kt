package ru.shtykin.pdauthapp.presentation


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.shtykin.pdauthapp.presentation.state.ScreenState
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
) : ViewModel() {

    private val _uiState =
        mutableStateOf<ScreenState>(
            ScreenState.AuthScreen(
                temp = ""
            )
        )

    val uiState: State<ScreenState>
        get() = _uiState


}