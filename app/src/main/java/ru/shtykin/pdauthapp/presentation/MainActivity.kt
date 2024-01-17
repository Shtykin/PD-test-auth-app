package ru.shtykin.pdauthapp.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.shtykin.pdauthapp.navigation.AppNavGraph
import ru.shtykin.pdauthapp.navigation.Screen
import ru.shtykin.pdauthapp.presentation.screen.auth.AuthScreen
import ru.shtykin.pdauthapp.presentation.screen.main.MainScreen
import ru.shtykin.pdauthapp.presentation.ui.theme.PDAuthAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHostController = rememberNavController()
            val uiState by viewModel.uiState
            val startScreenRoute = Screen.Auth.route
            PDAuthAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavGraph(
                        startScreenRoute = startScreenRoute,
                        navHostController = navHostController,
                        authScreenContent = {
                            AuthScreen(
                                uiState = uiState,
                                getFlagEmoji = { viewModel.getFlagEmoji(it) },
                                onGetCodeClick = { phone ->
                                    viewModel.getCode(
                                        phone = phone,
                                        onSuccess = { }
                                    )
                                },
                                onSubmitClick = { phone, code ->
                                    viewModel.login(
                                        phone = phone,
                                        code = code,
                                        onSuccess = {text ->
                                            navHostController.navigate(Screen.Main.route) {
                                                popUpTo(0)
                                            }
                                            viewModel.mainScreenOpened(text)
                                        }
                                    )
                                }
                            )
                        },
                        mainScreenContent = {
                            MainScreen(
                                uiState = uiState
                            )

                        }
                    )
                }
            }
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}