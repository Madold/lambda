@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class)

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import core.presentation.Screens
import core.presentation.koinViewModel
import core.utils.pop
import dashboard.presentation.DashboardScreen
import dashboard.presentation.DashboardViewModel
import dashboard.presentation.DashboardViewModelEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import splash.presentation.SplashScreen
import ui.LambdaTheme


@Composable
@Preview
fun App() {

    val navController = rememberNavController()
    val windowSize = calculateWindowSizeClass()

    LambdaTheme {
        KoinContext {
            Surface(
                modifier = Modifier.fillMaxSize()
            ) {
                NavHost(
                    navController = navController,
                    startDestination = Screens.Splash.route
                ) {
                    composable(route = Screens.Splash.route) {

                        LaunchedEffect(Unit) {
                            delay(3000)
                            navController.pop()
                            navController.navigate(Screens.Dashboard.route)
                        }

                        SplashScreen()
                    }

                    composable(route = Screens.Dashboard.route) {

                        val viewModel = koinViewModel<DashboardViewModel>()
                        val state by viewModel.state.collectAsState()
                        val usersViewSnackbarHost = remember { SnackbarHostState() }

                        LaunchedEffect(Unit) {
                            viewModel.events.collectLatest { event ->
                                when (event) {
                                    is DashboardViewModelEvent.UserNotFoundError -> {
                                        usersViewSnackbarHost.showSnackbar(
                                            message = event.errorMessage,
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                }
                            }
                        }

                        DashboardScreen(
                            state = state,
                            onEvent = viewModel::onEvent,
                            usersViewSnackbarHost = usersViewSnackbarHost,
                            windowSizeClass = windowSize
                        )
                    }
                }

            }
        }
    }
}