import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import lambda.composeapp.generated.resources.Res
import lambda.composeapp.generated.resources.lambda_logo
import org.jetbrains.compose.resources.painterResource

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Lambda admin panel",
        icon = painterResource(Res.drawable.lambda_logo),
        state = WindowState(placement = WindowPlacement.Maximized)
    ) {
        App()
    }
}