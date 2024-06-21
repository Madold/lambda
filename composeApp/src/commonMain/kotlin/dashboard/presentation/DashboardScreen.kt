package dashboard.presentation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dashboard.presentation.composables.DonationsListView
import dashboard.presentation.composables.Drawer
import dashboard.presentation.composables.DrawerOptions
import dashboard.presentation.composables.MentoriesListView
import dashboard.presentation.composables.UsersListView
import org.jetbrains.compose.resources.painterResource

@Composable
fun DashboardScreen(
    state: DashboardState,
    onEvent: (DashboardEvent) -> Unit,
    usersViewSnackbarHost: SnackbarHostState = remember { SnackbarHostState() },
    windowSizeClass: WindowSizeClass
) {

    val drawerNavController = rememberNavController()
    var selectedOption: DrawerOptions by remember {
        mutableStateOf(DrawerOptions.UsersView)
    }

    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Expanded -> {
            ExpandedLayout(
                state = state,
                onEvent = onEvent,
                drawerNavController = drawerNavController,
                usersViewSnackBarHost = usersViewSnackbarHost,
                selectedOptions = selectedOption,
                onOptionChange = { selectedOption = it }
            )
        }

        WindowWidthSizeClass.Medium -> {
            MediumLayout(
                state = state,
                onEvent = onEvent,
                drawerNavController = drawerNavController,
                usersViewSnackBarHost = usersViewSnackbarHost,
                selectedOptions = selectedOption,
                onOptionChange = { selectedOption = it }
            )
        }

        WindowWidthSizeClass.Compact -> {
            CompactLayout(
                state = state,
                onEvent = onEvent,
                drawerNavController = drawerNavController,
                usersViewSnackBarHost = usersViewSnackbarHost,
                selectedOptions = selectedOption,
                onOptionChange = { selectedOption = it }
            )
        }

    }

}

@Composable
private fun ExpandedLayout(
    drawerNavController: NavHostController,
    state: DashboardState,
    onEvent: (DashboardEvent) -> Unit,
    usersViewSnackBarHost: SnackbarHostState,
    selectedOptions: DrawerOptions,
    onOptionChange: (DrawerOptions) -> Unit
) {


    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        Drawer(
            navController = drawerNavController,
            selectedOption = selectedOptions,
            onOptionChange = onOptionChange
        )

        NavHost(
            navController = drawerNavController,
            startDestination = DrawerOptions.UsersView.label
        ) {

            composable(route = DrawerOptions.UsersView.label) {
                UsersListView(
                    state = state,
                    modifier = Modifier.weight(1f),
                    onEvent = onEvent,
                    snackbarHostState = usersViewSnackBarHost
                )
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
                DonationsListView(
                    state = state,
                    onEvent = onEvent,
                    snackbarHostState = remember { SnackbarHostState() }
                )
            }

        }
    }
}

@Composable
private fun MediumLayout(
    drawerNavController: NavHostController,
    state: DashboardState,
    onEvent: (DashboardEvent) -> Unit,
    usersViewSnackBarHost: SnackbarHostState,
    selectedOptions: DrawerOptions,
    onOptionChange: (DrawerOptions) -> Unit
) {

    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        NavigationRail {
            DrawerOptions.entries.forEach { option ->
                NavigationRailItem(
                    icon = {
                        Icon(
                            painter = painterResource(option.icon),
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(text = option.label)
                    },
                    onClick = {
                        onOptionChange(option)
                        drawerNavController.navigate(option.label) {
                            launchSingleTop = true
                        }
                    },
                    selected = selectedOptions == option
                )
            }
        }

        NavHost(
            navController = drawerNavController,
            startDestination = DrawerOptions.UsersView.label
        ) {

            composable(route = DrawerOptions.UsersView.label) {
                UsersListView(
                    state = state,
                    modifier = Modifier.weight(1f),
                    onEvent = onEvent,
                    snackbarHostState = usersViewSnackBarHost
                )
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
                DonationsListView(
                    state = state,
                    onEvent = onEvent,
                    snackbarHostState = remember { SnackbarHostState() }
                )
            }

        }
    }
}

@Composable
private fun CompactLayout(
    drawerNavController: NavHostController,
    state: DashboardState,
    onEvent: (DashboardEvent) -> Unit,
    usersViewSnackBarHost: SnackbarHostState,
    selectedOptions: DrawerOptions,
    onOptionChange: (DrawerOptions) -> Unit
) {

    Scaffold(
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                DrawerOptions.entries.forEach { option ->
                    NavigationBarItem(
                        selected = option == selectedOptions,
                        onClick = {
                            onOptionChange(option)
                            drawerNavController.navigate(option.label) {
                                launchSingleTop = true
                            }
                        },
                        label = {
                            Text(option.label)
                        },
                        icon = {
                            Icon(
                                painter = painterResource(option.icon),
                                contentDescription = null
                            )
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = drawerNavController,
            startDestination = DrawerOptions.UsersView.label,
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {

            composable(route = DrawerOptions.UsersView.label) {
                UsersListView(
                    state = state,
                    onEvent = onEvent,
                    snackbarHostState = usersViewSnackBarHost
                )
            }

            composable(route = DrawerOptions.MentoriesView.label) {

                val snackbarHostState = remember { SnackbarHostState() }

                MentoriesListView(
                    state = state,
                    onEvent = onEvent,
                    snackbarHostState = snackbarHostState,
                )
            }

            composable(route = DrawerOptions.DonationsView.label) {
                DonationsListView(
                    state = state,
                    onEvent = onEvent,
                    snackbarHostState = remember { SnackbarHostState() }
                )
            }

        }
    }

}