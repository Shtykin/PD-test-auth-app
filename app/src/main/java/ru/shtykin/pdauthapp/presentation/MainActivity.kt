package ru.shtykin.pdauthapp.presentation

import android.os.Bundle
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
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavGraph(
                        startScreenRoute = startScreenRoute,
                        navHostController = navHostController,
                        authScreenContent = {
                            AuthScreen(
                                uiState = uiState
                            )
                        }
                    )
                }
            }
        }
    }
}