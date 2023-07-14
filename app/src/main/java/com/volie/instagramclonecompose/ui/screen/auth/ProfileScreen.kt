package com.volie.instagramclonecompose.ui.screen.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.volie.instagramclonecompose.DestinationScreen
import com.volie.instagramclonecompose.R
import com.volie.instagramclonecompose.main.CommonProgressSpinner
import com.volie.instagramclonecompose.main.navigateTo
import com.volie.instagramclonecompose.ui.theme.LARGEST_SIZE
import com.volie.instagramclonecompose.ui.theme.LARGE_PADDING
import com.volie.instagramclonecompose.ui.theme.LOW_PADDING
import com.volie.instagramclonecompose.ui.theme.MEDIUM_PADDING
import com.volie.instagramclonecompose.ui.viewmodel.IgViewModel

@Composable
fun ProfileScreen(navController: NavController, viewModel: IgViewModel) {

    val isLoading = viewModel.inProgress.value

    if (isLoading) {
        CommonProgressSpinner()
    } else {
        val userData = viewModel.userData.value
        var name by rememberSaveable { mutableStateOf(userData?.name ?: "") }
        var username by rememberSaveable { mutableStateOf(userData?.username ?: "") }
        var bio by rememberSaveable { mutableStateOf(userData?.bio ?: "") }

        ProfileContent(
            viewModel = viewModel,
            name = name,
            username = username,
            bio = bio,
            onNameChanged = { name = it },
            onUsernameChanged = { username = it },
            onBioChanged = { bio = it },
            onSave = {
                viewModel.updateProfileData(
                    name = name,
                    username = username,
                    bio = bio
                )
                navigateTo(
                    navController = navController,
                    destination = DestinationScreen.MyPosts
                )
            },
            onBack = {
                navigateTo(
                    navController = navController,
                    destination = DestinationScreen.MyPosts
                )
            },
            onLogout = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileContent(
    viewModel: IgViewModel,
    name: String,
    username: String,
    bio: String,
    onNameChanged: (String) -> Unit,
    onUsernameChanged: (String) -> Unit,
    onBioChanged: (String) -> Unit,
    onSave: () -> Unit,
    onBack: () -> Unit,
    onLogout: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .verticalScroll(state = scrollState)
            .padding(MEDIUM_PADDING)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MEDIUM_PADDING),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.clickable { onBack.invoke() },
                text = stringResource(id = R.string.back)
            )
            Text(
                modifier = Modifier.clickable { onSave.invoke() },
                text = stringResource(id = R.string.save)
            )
        }
        CommonDivider()

        // User image
        Column(
            modifier = Modifier
                .height(height = LARGEST_SIZE)
                .fillMaxWidth()
                .background(Color.Gray)
        ) {

        }
        CommonDivider()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = LOW_PADDING, end = LOW_PADDING),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.width(100.dp),
                text = stringResource(id = R.string.name)
            )
            TextField(
                value = name,
                onValueChange = onNameChanged,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    containerColor = Color.Transparent
                )
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = LOW_PADDING, end = LOW_PADDING),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.width(100.dp),
                text = stringResource(id = R.string.username)
            )
            TextField(
                value = username,
                onValueChange = onUsernameChanged,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    containerColor = Color.Transparent
                )
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = LOW_PADDING, end = LOW_PADDING),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                modifier = Modifier.width(100.dp),
                text = stringResource(id = R.string.bio)
            )
            TextField(
                modifier = Modifier.height(150.dp),
                value = bio,
                onValueChange = onBioChanged,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    containerColor = Color.Transparent
                ),
                singleLine = false,
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = LARGE_PADDING, bottom = LARGE_PADDING),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.clickable { onLogout.invoke() },
                text = stringResource(id = R.string.logout)
            )
        }
    }
}

@Composable
fun CommonDivider() {
    Divider(
        modifier = Modifier
            .alpha(alpha = 0.3f)
            .padding(top = MEDIUM_PADDING, bottom = MEDIUM_PADDING),
        color = Color.LightGray,
        thickness = 1.dp
    )
}