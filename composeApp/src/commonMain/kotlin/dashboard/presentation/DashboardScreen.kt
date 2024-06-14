package dashboard.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dashboard.presentation.composables.*

@Composable
fun DashboardScreen(
    state: DashboardState,
    onEvent: (DashboardEvent) -> Unit
) {

    val drawerNavController = rememberNavController()

    Row(
        modifier = Modifier.fillMaxSize()
    ) {

        Drawer(navController = drawerNavController)

        NavHost(
            navController = drawerNavController,
            startDestination = DrawerOptions.UsersView.label
        ) {

            composable(route = DrawerOptions.UsersView.label) {
                UsersListView(
                    state = state,
                    modifier = Modifier.weight(1f),
                    onEvent = onEvent
                )
            }

            composable(route = DrawerOptions.CoursesView.label) {
                Text("Cursos")
            }

            composable(route = DrawerOptions.MentoriesView.label) {
                Text("Tutorias")
            }

            composable(route = DrawerOptions.DonationsView.label) {
                Text("Donaciones")
            }

        }
    }
}