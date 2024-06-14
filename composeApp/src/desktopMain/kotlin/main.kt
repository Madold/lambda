import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import di.DesktopViewModelModule
import di.dashboardModule
import di.desktopModule
import lambda.composeapp.generated.resources.Res
import lambda.composeapp.generated.resources.lambda_logo
import org.jetbrains.compose.resources.painterResource
import org.koin.core.context.startKoin

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Lambda admin panel",
        icon = painterResource(Res.drawable.lambda_logo),
        state = WindowState(placement = WindowPlacement.Maximized)
    ) {
        startKoin {
            modules(dashboardModule, desktopModule, DesktopViewModelModule().get())
        }
        App()
    }
}