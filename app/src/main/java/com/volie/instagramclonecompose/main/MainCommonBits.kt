package com.volie.instagramclonecompose.main

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.volie.instagramclonecompose.DestinationScreen
import com.volie.instagramclonecompose.ui.viewmodel.IgViewModel

@Composable
fun NotificationMessage(viewModel: IgViewModel) {
    val notificationState = viewModel.popupNotification.value
    val notificationMessage = notificationState?.getContentOrNull()

    if (notificationMessage != null) {
        Toast.makeText(LocalContext.current, notificationMessage, Toast.LENGTH_LONG).show()
    }
}

@Composable
fun CommonProgressSpinner() {
    Row(
        modifier = Modifier
            .alpha(0.5f)
            .background(Color.LightGray)
            .clickable(enabled = false) { }
            .fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularProgressIndicator()
    }
}

fun navigateTo(
    navController: NavController,
    destination: DestinationScreen
) {
    navController.navigate(destination.route) {
        popUpTo(destination.route)
        launchSingleTop = true
    }
}

@Composable
fun CheckSignedIn(navController: NavController, viewModel: IgViewModel) {
    var alreadyLoggedIn by remember { mutableStateOf(false) }
    val signedIn = viewModel.signedIn.value
    if (signedIn && !alreadyLoggedIn) {
        alreadyLoggedIn = true
        navController.navigate(DestinationScreen.Feed.route) {
            popUpTo(0)
        }
    }
}

