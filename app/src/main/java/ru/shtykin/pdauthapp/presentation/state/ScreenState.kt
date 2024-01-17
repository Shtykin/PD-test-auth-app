package ru.shtykin.pdauthapp.presentation.state


sealed class ScreenState {

    data class AuthScreen(
        val phone: String? = null,
        val isVisibleCodeField: Boolean,
        val phoneError: String?,
        val codeError: String?,
    ) : ScreenState()

    data class MainScreen(
        val msg: String,
    ) : ScreenState()

}
