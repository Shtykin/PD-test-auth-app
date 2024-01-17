package ru.shtykin.pdauthapp.presentation.screen.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import ru.shtykin.pdauthapp.R
import ru.shtykin.pdauthapp.presentation.screen.common.VerticalSpace
import ru.shtykin.pdauthapp.presentation.state.ScreenState
import ru.shtykin.pdauthapp.presentation.utils.PhoneNumberVisualTransformation

@Composable
fun AuthScreen(
    uiState: ScreenState,
    getFlagEmoji: ((String) -> String?),
    onGetCodeClick: ((String) -> Unit)?,
    onSubmitClick: ((String, String) -> Unit)?,
) {
    val isVisibleCodeField = (uiState as? ScreenState.AuthScreen)?.isVisibleCodeField ?: false
    val phoneError = (uiState as? ScreenState.AuthScreen)?.phoneError
    val codeError = (uiState as? ScreenState.AuthScreen)?.codeError

    var flagEmoji by remember {
        mutableStateOf(
            getFlagEmoji.invoke("") ?: ""
        )
    }
    var phone by remember {
        mutableStateOf(
            TextFieldValue(
                text = ""
            )
        )
    }

    val phoneChanged: (TextFieldValue) -> Unit = {
        if (it.text.length <= PHONE_LENGTH) {
            phone = it.copy(
                text = "+" + it.text.filter { char -> char.isDigit() },
                selection = TextRange(it.text.length + 1)
            )
            getFlagEmoji.invoke(phone.text)?.let { emoji ->
                flagEmoji = emoji
            }
        }
    }

    var code by remember {
        mutableStateOf(
            TextFieldValue(
                text = ""
            )
        )
    }

    val codeChanged: (TextFieldValue) -> Unit = {
        if (it.text.length <= CODE_LENGTH) {
            code = it.copy(
                text = it.text.filter { char -> char.isDigit() },
                selection = TextRange(it.text.length + 1)
            )
        }
    }

    var visible by remember { mutableStateOf(false) }
    SideEffect {
        visible = true
    }

    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        VerticalSpace(height = 25.dp)
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(
                animationSpec = tween(2000, 500),
                initialAlpha = 0f
            )
        ) {
            Image(
                modifier = Modifier
                    .clip(RoundedCornerShape(10))
                    .size(50.dp),
                painter = painterResource(id = R.drawable.pd_logo),
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
        }
        VerticalSpace(height = 25.dp)
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = phone,
            onValueChange = phoneChanged,
            label = { Text("Номер телефона") },
            singleLine = true,
            enabled = !isVisibleCodeField,
            supportingText = { phoneError?.let { Text(text = it, color = Color.Red) } },
            placeholder = { Text("Введите номер телефона") },
            leadingIcon = { Text(flagEmoji) },
            visualTransformation = PhoneNumberVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )
        VerticalSpace(16.dp)
        OutlinedButton(
            enabled = !isVisibleCodeField,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            onClick = { onGetCodeClick?.invoke(phone.text) }
        ) {
            Text(text = "Получить код")
        }
        VerticalSpace(16.dp)
        if (isVisibleCodeField) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = code,
                onValueChange = codeChanged,
                label = { Text("Код из СМС") },
                singleLine = true,
                supportingText = { codeError?.let { Text(text = it, color = Color.Red) } },
                placeholder = { Text("Введите код из СМС") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            VerticalSpace(16.dp)
            if (codeError != null) {
                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    onClick = { onGetCodeClick?.invoke(phone.text) }
                ) {
                    Text(text = "Отправить код заново")
                }
            }
            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                onClick = { onSubmitClick?.invoke(phone.text, code.text) }
            ) {
                Text(text = "Войти")
            }
        }
    }
}

const val PHONE_LENGTH = 12
const val CODE_LENGTH = 6