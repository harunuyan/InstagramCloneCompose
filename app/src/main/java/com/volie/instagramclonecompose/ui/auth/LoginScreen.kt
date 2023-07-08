package com.volie.instagramclonecompose.ui.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.volie.instagramclonecompose.DestinationScreen
import com.volie.instagramclonecompose.main.navigateTo
import com.volie.instagramclonecompose.ui.viewmodel.IgViewModel

@Composable
fun LoginScreen(navController: NavController, viewModel: IgViewModel) {
    Text(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                navigateTo(
                    navController = navController,
                    destination = DestinationScreen.Signup
                )
            },
        text = "New here? Go to signup ->",
        color = Color.Blue
    )
}