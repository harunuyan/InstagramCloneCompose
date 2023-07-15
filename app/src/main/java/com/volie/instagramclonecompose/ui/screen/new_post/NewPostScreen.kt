package com.volie.instagramclonecompose.ui.screen.new_post

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.volie.instagramclonecompose.R
import com.volie.instagramclonecompose.main.CommonDivider
import com.volie.instagramclonecompose.main.CommonProgressSpinner
import com.volie.instagramclonecompose.ui.theme.LARGE_PADDING
import com.volie.instagramclonecompose.ui.theme.MEDIUM_PADDING
import com.volie.instagramclonecompose.ui.viewmodel.IgViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewPostScreen(navController: NavController, viewModel: IgViewModel, encodedUri: String) {

    val imageUri by remember { mutableStateOf(encodedUri) }
    var description by rememberSaveable { mutableStateOf("") }
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .verticalScroll(state = scrollState)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MEDIUM_PADDING),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.clickable { navController.popBackStack() },
                text = stringResource(id = R.string.cancel)
            )
            Text(
                modifier = Modifier.clickable {
                    focusManager.clearFocus()
                    viewModel.onNewPost(
                        uri = Uri.parse(imageUri),
                        description = description,
                    ) { navController.popBackStack() }
                }, text = stringResource(id = R.string.post)
            )
        }
        CommonDivider()

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 150.dp),
            contentScale = ContentScale.FillWidth,
            painter = rememberAsyncImagePainter(model = imageUri),
            contentDescription = null
        )

        Row(modifier = Modifier.padding(LARGE_PADDING)) {

            OutlinedTextField(modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
                label = { Text(text = stringResource(id = R.string.description)) },
                singleLine = false,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent, textColor = Color.Black
                ),
                value = description,
                onValueChange = { description = it })
        }
    }

    val inProgress = viewModel.inProgress.value
    if (inProgress) CommonProgressSpinner()

}