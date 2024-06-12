package splash.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import lambda.composeapp.generated.resources.Res
import lambda.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.painterResource

@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
    
    Scaffold(
        modifier = modifier
    ) { innerPadding ->
        
        var isLinearProgressIndicatorVisible by remember {
            mutableStateOf(false)
        }
        
        LaunchedEffect(Unit) {
            delay(600)
            isLinearProgressIndicatorVisible = true
        }
        
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
            ,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(Res.drawable.logo),
                contentDescription = null
            )
            AnimatedVisibility(
                visible = isLinearProgressIndicatorVisible,
                enter = expandVertically()
            ) {
                LinearProgressIndicator()
            }
        }
    }
    
}

