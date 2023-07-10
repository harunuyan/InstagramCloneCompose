package com.volie.instagramclonecompose.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.volie.instagramclonecompose.DestinationScreen
import com.volie.instagramclonecompose.R
import com.volie.instagramclonecompose.ui.theme.LARGEST_SIZE
import com.volie.instagramclonecompose.ui.theme.LOW_PADDING

enum class BottomNavigationItem(val icon: Int, val navDestination: DestinationScreen) {
    FEED(R.drawable.ic_home, DestinationScreen.Feed),
    SEARCH(R.drawable.ic_search, DestinationScreen.Search),
    POSTS(R.drawable.ic_person, DestinationScreen.MyPosts)

}

@Composable
fun BottomNavigationMenu(selectedItem: BottomNavigationItem, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = LOW_PADDING)
            .background(Color.White)
    ) {
        for (item in BottomNavigationItem.values()) {
            Image(
                painter = painterResource(id = item.icon),
                contentDescription = null,
                modifier = Modifier
                    .size(LARGEST_SIZE)
                    .padding(LOW_PADDING)
                    .weight(1f)
                    .clickable {
                        navigateTo(navController = navController, item.navDestination)
                    },
                colorFilter =
                if (item == selectedItem) ColorFilter.tint(Color.Black)
                else ColorFilter.tint(Color.Gray)
            )
        }
    }
}