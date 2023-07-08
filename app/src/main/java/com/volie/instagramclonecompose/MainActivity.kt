package com.volie.instagramclonecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.volie.instagramclonecompose.main.NotificationMessage
import com.volie.instagramclonecompose.ui.auth.SignupScreen
import com.volie.instagramclonecompose.ui.theme.InstagramCloneComposeTheme
import com.volie.instagramclonecompose.ui.viewmodel.IgViewModel
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InstagramCloneComposeTheme {
                InstagramApp()
            }
        }
    }
}

sealed class DestinationScreen(val route: String) {
    object Signup : DestinationScreen(route = "signup")
}

@ExperimentalMaterial3Api
@Composable
fun InstagramApp() {
    val viewModel: IgViewModel = viewModel()
    val navController = rememberNavController()

    NotificationMessage(viewModel = viewModel)

    NavHost(
        navController = navController,
        startDestination = DestinationScreen.Signup.route
    ) {
        composable(DestinationScreen.Signup.route) {
            SignupScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}

@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
private fun InstagramAppPreview() {
    InstagramCloneComposeTheme {
        InstagramApp()
    }
}