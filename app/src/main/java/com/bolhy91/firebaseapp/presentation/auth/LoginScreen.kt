package com.bolhy91.firebaseapp.presentation.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bolhy91.firebaseapp.R
import com.bolhy91.firebaseapp.ui.components.InputText
import com.bolhy91.firebaseapp.ui.theme.FirebaseAppTheme
import com.bolhy91.firebaseapp.ui.theme.blackColor
import com.bolhy91.firebaseapp.ui.theme.grayColor200
import com.bolhy91.firebaseapp.ui.theme.primaryColor
import com.bolhy91.firebaseapp.utils.ScopeManager
import com.bolhy91.firebaseapp.utils.Toaster
import com.bolhy91.firebaseapp.utils.forward

@Composable
fun LoginScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onHomeNav: () -> Unit,
    onRegisterNav: () -> Unit
) {

    val state = viewModel.state
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val context = LocalContext.current

    LaunchedEffect(key1 = viewModel.uiScope.value) {
        viewModel.uiScope.forward(
            ScopeManager(
                toaster = Toaster(context)
            )
        )
    }
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(
                start = 22.dp,
                end = 22.dp,
                top = 100.dp,
            )
            .verticalScroll(rememberScrollState()),
    ) {
        Text(
            text = stringResource(R.string.welcome),
            style = TextStyle(
                color = blackColor,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
        InputText(
            placeholder = stringResource(id = R.string.text_email),
            KeyboardType.Email,
            email,
            onChangeText = { email.value = it })
        Spacer(modifier = Modifier.height(10.dp))
        InputText(
            placeholder = stringResource(id = R.string.text_password),
            KeyboardType.Password,
            password,
            onChangeText = { password.value = it })
        Text(
            text = stringResource(id = R.string.text_forgot),
            style = TextStyle(
                color = grayColor200,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Right
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextButton(
            onClick = {
                viewModel.loginUser(email.value, password.value)
                if (state.value.isAuth) {
                    onHomeNav()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(blackColor),
            enabled = viewModel.validateForm(email, password)
        ) {
            Text(
                text = stringResource(id = R.string.button_login),
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp
                ),
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            buildAnnotatedString {
                withStyle(style = ParagraphStyle(lineHeight = 25.sp)) {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Normal, fontSize = 14.sp)) {
                        append(stringResource(id = R.string.text_register_not_account))
                    }
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 16.sp,
                            color = primaryColor
                        ),
                    ) {
                        append(stringResource(id = R.string.button_register))
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onRegisterNav() },
            textAlign = TextAlign.Center
        )
    }
}