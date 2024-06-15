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
    onEvent: (DashboardEvent) -> Unit,
    usersViewSnackbarHost: SnackbarHostState = remember { SnackbarHostState() }
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
                    onEvent = onEvent,
                    snackbarHostState = usersViewSnackbarHost
                )
            }

            composable(route = DrawerOptions.CoursesView.label) {
                Text("Cursos")
            }

            composable(route = DrawerOptions.MentoriesView.label) {

                val snackbarHostState = remember { SnackbarHostState() }

                MentoriesListView(
                    state = state,
                    onEvent = onEvent,
                    snackbarHostState = snackbarHostState,
                    modifier = Modifier.weight(1f)
                )
            }

            composable(route = DrawerOptions.DonationsView.label) {
                Text("Donaciones")
            }

        }
    }
}