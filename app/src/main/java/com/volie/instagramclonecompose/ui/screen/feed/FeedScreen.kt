package com.volie.instagramclonecompose.ui.screen.feed

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.volie.instagramclonecompose.ui.viewmodel.IgViewModel

@Composable
fun FeedScreen(navController: NavController, viewModel: IgViewModel) {
    Text(text = "Feed Screen")
}