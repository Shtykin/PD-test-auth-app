package ru.shtykin.pdauthapp.presentation.screen.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.shtykin.pdauthapp.presentation.state.ScreenState

@Composable
fun MainScreen(
    uiState: ScreenState,
) {
    val msg = (uiState as? ScreenState.MainScreen)?.msg ?: ""
    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = msg,
            fontSize = 20.sp,

        )
    }

}