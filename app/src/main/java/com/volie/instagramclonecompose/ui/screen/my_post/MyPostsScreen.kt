package com.volie.instagramclonecompose.ui.screen.my_post

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.volie.instagramclonecompose.R
import com.volie.instagramclonecompose.main.BottomNavigationItem
import com.volie.instagramclonecompose.main.BottomNavigationMenu
import com.volie.instagramclonecompose.main.UserImageCard
import com.volie.instagramclonecompose.ui.theme.LARGE_PADDING
import com.volie.instagramclonecompose.ui.theme.LARGE_SIZE
import com.volie.instagramclonecompose.ui.theme.LOWEST_PADDING
import com.volie.instagramclonecompose.ui.theme.LOWEST_SIZE
import com.volie.instagramclonecompose.ui.theme.MEDIUM_PADDING
import com.volie.instagramclonecompose.ui.viewmodel.IgViewModel

@Composable
fun MyPostsScreen(navController: NavController, viewModel: IgViewModel) {

    val userData = viewModel.userData.value
    val isLoading = viewModel.inProgress.value

    Column {
        Column(modifier = Modifier.weight(weight = 1f)) {
            Row {
                ProfileImage(
                    imageUrl = userData?.imageUrl,
                    onClick = {}
                )
                Text(
                    text = "15\nPost",
                    modifier = Modifier
                        .weight(weight = 1f)
                        .align(Alignment.CenterVertically),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "15\nFollowers",
                    modifier = Modifier
                        .weight(weight = 1f)
                        .align(Alignment.CenterVertically),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "15\nFollowing",
                    modifier = Modifier
                        .weight(weight = 1f)
                        .align(Alignment.CenterVertically),
                    textAlign = TextAlign.Center
                )
            }

            Column(modifier = Modifier.padding(MEDIUM_PADDING)) {
                val usernameDisplay =
                    if (userData?.username == null) "" else "@${userData.username}"
                Text(text = userData?.name ?: "", fontWeight = FontWeight.Bold)
                Text(text = usernameDisplay)
                Text(text = userData?.bio ?: "")
            }
            OutlinedButton(
                modifier = Modifier
                    .padding(MEDIUM_PADDING)
                    .fillMaxWidth()
                    .background(Color.Transparent),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp,
                    disabledElevation = 0.dp
                ),
                shape = RoundedCornerShape(percent = 10),
                onClick = {}
            ) {
                Text(
                    text = stringResource(id = R.string.edit_profile),
                    color = Color.Black
                )
            }
            Column(modifier = Modifier.weight(weight = 1f)) {
                Text(text = stringResource(id = R.string.posts_list))
            }
        }

        BottomNavigationMenu(
            selectedItem = BottomNavigationItem.POSTS,
            navController = navController
        )
    }
}

@Composable
fun ProfileImage(imageUrl: String?, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(top = LARGE_PADDING)
            .clickable { onClick.invoke() }
    ) {
        UserImageCard(
            modifier = Modifier
                .padding(MEDIUM_PADDING)
                .size(LARGE_SIZE),
            userImage = imageUrl
        )
        Card(
            modifier = Modifier
                .size(LOWEST_SIZE)
                .align(alignment = Alignment.BottomEnd)
                .padding(bottom = MEDIUM_PADDING, end = MEDIUM_PADDING),
            shape = CircleShape,
            border = BorderStroke(width = LOWEST_PADDING, color = Color.White)
        ) {
            Image(
                modifier = Modifier.background(Color.Blue),
                painter = painterResource(id = R.drawable.ic_add),
                colorFilter = ColorFilter.tint(Color.White),
                contentDescription = stringResource(id = R.string.add_icon)
            )
        }
    }
}

@Composable
@Preview
private fun ProfileImagePreview() {
    ProfileImage(
        imageUrl = "",
        onClick = {}
    )
}