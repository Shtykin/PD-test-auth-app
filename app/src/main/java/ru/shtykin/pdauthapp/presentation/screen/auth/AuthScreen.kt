package ru.shtykin.pdauthapp.presentation.screen.auth

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.shtykin.pdauthapp.presentation.state.ScreenState

@Composable
fun AuthScreen(
    uiState: ScreenState,
) {
    Text(text = "Auth")
}