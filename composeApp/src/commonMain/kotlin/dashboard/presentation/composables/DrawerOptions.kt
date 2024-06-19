package dashboard.presentation.composables

import lambda.composeapp.generated.resources.*
import lambda.composeapp.generated.resources.Res
import lambda.composeapp.generated.resources.ic_class_scene
import lambda.composeapp.generated.resources.ic_graduation_cap
import lambda.composeapp.generated.resources.ic_user
import org.jetbrains.compose.resources.DrawableResource

sealed class DrawerOptions(
    val icon: DrawableResource,
    val label: String
) {
    
    companion object {
        val entries get() = listOf(
            UsersView,
            MentoriesView,
            DonationsView
        )    
    }
    
    
    data object UsersView: DrawerOptions(
        icon = Res.drawable.ic_user,
        label = "Usuarios"
    )
    
    /*data object CoursesView: DrawerOptions(
        icon = Res.drawable.ic_graduation_cap,
        label = "Cursos",
    )*/
    
    data object MentoriesView: DrawerOptions(
        icon = Res.drawable.ic_class_scene,
        label = "Tutorias"
    )
    
    data object DonationsView: DrawerOptions(
        icon = Res.drawable.ic_donation,
        label = "Donaciones"
    )
    
    
}

