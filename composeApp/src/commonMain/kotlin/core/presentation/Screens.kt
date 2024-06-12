package core.presentation

sealed class Screens(val route: String) {
    
    data object Splash: Screens(route = "splash")
    
    data object Dashboard: Screens(route = "dashboard")
    
}