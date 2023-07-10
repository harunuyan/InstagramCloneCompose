package com.volie.instagramclonecompose.ui.screen.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.volie.instagramclonecompose.R
import com.volie.instagramclonecompose.main.BottomNavigationItem
import com.volie.instagramclonecompose.main.BottomNavigationMenu
import com.volie.instagramclonecompose.ui.viewmodel.IgViewModel

@Composable
fun SearchScreen(navController: NavController, viewModel: IgViewModel) {
    Column(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.weight(weight = 1f)) {
            Text(text = stringResource(id = R.string.search_screen))
        }
        BottomNavigationMenu(
            selectedItem = BottomNavigationItem.SEARCH,
            navController = navController
        )
    }
}