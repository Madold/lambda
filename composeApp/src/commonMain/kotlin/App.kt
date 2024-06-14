import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import core.presentation.Screens
import core.presentation.koinViewModel
import core.utils.pop
import dashboard.presentation.DashboardScreen
import dashboard.presentation.DashboardViewModel
import kotlinx.coroutines.delay
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import splash.presentation.SplashScreen
import ui.LambdaTheme

@Composable
@Preview
fun App() {

    val navController = rememberNavController()

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

                        DashboardScreen(
                            state = state,
                            onEvent = viewModel::onEvent
                        )
                    }
                }

            }
        }
    }
}