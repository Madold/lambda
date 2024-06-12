package core.utils

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController

fun NavController.pop() {
    if (this.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
        this.popBackStack()
    }
}