package ru.shtykin.pdauthapp.presentation.state


sealed class ScreenState {

    data class AuthScreen(
        val temp: String,
    ) : ScreenState()

}
