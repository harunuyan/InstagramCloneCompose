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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.volie.instagramclonecompose.R
import com.volie.instagramclonecompose.ui.theme.INSTAGRAM_LOGO_PADDING
import com.volie.instagramclonecompose.ui.theme.INSTAGRAM_LOGO_PADDING_TOP
import com.volie.instagramclonecompose.ui.theme.INSTAGRAM_LOGO_SIZE
import com.volie.instagramclonecompose.ui.theme.MEDIUM_PADDING
import com.volie.instagramclonecompose.ui.viewmodel.IgViewModel

@ExperimentalMaterial3Api
@Composable
fun SignupScreen(
    navController: NavController,
    viewModel: IgViewModel
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .verticalScroll(
                    rememberScrollState()
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            var usernameState by remember { mutableStateOf(TextFieldValue()) }
            var emailState by remember { mutableStateOf(TextFieldValue()) }
            var passState by remember { mutableStateOf(TextFieldValue()) }

            Image(
                modifier = Modifier
                    .width(INSTAGRAM_LOGO_SIZE)
                    .padding(top = INSTAGRAM_LOGO_PADDING_TOP)
                    .padding(INSTAGRAM_LOGO_PADDING),
                painter = painterResource(
                    id = R.drawable.ig_logo
                ),
                contentDescription = stringResource(
                    id = R.string.ig_logo
                )
            )
            Text(
                modifier = Modifier.padding(MEDIUM_PADDING),
                text = stringResource(id = R.string.signup),
                fontSize = 30.sp,
                fontFamily = FontFamily.SansSerif
            )
            OutlinedTextField(
                modifier = Modifier.padding(MEDIUM_PADDING),
                label = { Text(text = stringResource(id = R.string.username)) },
                value = usernameState,
                onValueChange = { usernameState = it }
            )
            OutlinedTextField(
                modifier = Modifier.padding(MEDIUM_PADDING),
                label = { Text(text = stringResource(id = R.string.email)) },
                value = emailState,
                onValueChange = { emailState = it }
            )
            OutlinedTextField(
                modifier = Modifier.padding(MEDIUM_PADDING),
                label = { Text(text = stringResource(id = R.string.password)) },
                value = passState,
                onValueChange = { passState = it },
                visualTransformation = PasswordVisualTransformation() // -> Hide password
            )
            Button(
                onClick = {
                    viewModel.onSignup(
                        usernameState.text,
                        emailState.text,
                        passState.text
                    )
                },
                modifier = Modifier.padding(MEDIUM_PADDING)
            ) {
                Text(text = stringResource(id = R.string.signup_button))
            }
            Text(
                modifier = Modifier
                    .padding(MEDIUM_PADDING)
                    .clickable { },
                text = stringResource(id = R.string.already_user),
                color = Color.Blue
            )
        }
    }
}