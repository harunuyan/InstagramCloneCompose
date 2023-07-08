package com.volie.instagramclonecompose.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.volie.instagramclonecompose.DestinationScreen
import com.volie.instagramclonecompose.R
import com.volie.instagramclonecompose.main.CommonProgressSpinner
import com.volie.instagramclonecompose.main.navigateTo
import com.volie.instagramclonecompose.ui.theme.INSTAGRAM_LOGO_SIZE
import com.volie.instagramclonecompose.ui.theme.LARGE_PADDING
import com.volie.instagramclonecompose.ui.theme.MEDIUM_PADDING
import com.volie.instagramclonecompose.ui.viewmodel.IgViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, viewModel: IgViewModel) {

    val focus = LocalFocusManager.current

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .verticalScroll(state = rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            var emailState by remember { mutableStateOf(TextFieldValue()) }
            var passState by remember { mutableStateOf(TextFieldValue()) }

            Image(
                modifier = Modifier
                    .width(INSTAGRAM_LOGO_SIZE)
                    .padding(top = LARGE_PADDING)
                    .padding(MEDIUM_PADDING),
                painter = painterResource(id = R.drawable.ig_logo),
                contentDescription = stringResource(R.string.ig_logo)
            )
            Text(
                modifier = Modifier.padding(MEDIUM_PADDING),
                fontSize = 30.sp,
                fontFamily = FontFamily.Serif,
                text = stringResource(id = R.string.login)
            )
            OutlinedTextField(
                modifier = Modifier.padding(MEDIUM_PADDING),
                value = emailState,
                label = { Text(text = stringResource(id = R.string.email)) },
                onValueChange = { emailState = it }
            )
            OutlinedTextField(
                modifier = Modifier.padding(MEDIUM_PADDING),
                value = passState,
                label = { Text(text = stringResource(id = R.string.password)) },
                visualTransformation = PasswordVisualTransformation(),
                onValueChange = { passState = it }
            )
            Button(
                modifier = Modifier.padding(MEDIUM_PADDING),
                onClick = {
                    focus.clearFocus(force = true)
                }) {
                Text(text = stringResource(id = R.string.login_button))
            }

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
        val isLoading = viewModel.inProgress.value
        if (isLoading) {
            CommonProgressSpinner()
        }
    }
}