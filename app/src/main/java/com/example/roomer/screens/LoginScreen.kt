package com.example.roomer.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.roomer.R
import com.example.roomer.ui_components.EmailField
import com.example.roomer.ui_components.GreenButtonPrimary
import com.example.roomer.ui_components.PasswordField
import com.example.roomer.view_model.LoginScreenViewModel
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun LoginScreen(
    id: Int,
) {
    val loginScreenViewModel: LoginScreenViewModel by viewModel()
    val focusManager = LocalFocusManager.current
    var emailValue by rememberSaveable {
        mutableStateOf("")
    }
    var passwordValue by rememberSaveable {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .clickable { focusManager.clearFocus() },

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ){
            Text(
                text = "Login",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
            EmailField(
                value = emailValue,
                onValueChange = { emailValue = it }
            )
            PasswordField(
                value = passwordValue,
                onValueChange = { passwordValue = it }
            )
            GreenButtonPrimary(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "Login") {

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Don't have an account?",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
                ClickableText(
                    text = AnnotatedString("Sign Up"),
                    modifier = Modifier
                        .padding(start=8.dp),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = colorResource(id = R.color.primary_dark),
                        textAlign = TextAlign.Center

                    ),
                    onClick = {
                        loginScreenViewModel.getUserLogin(emailValue, passwordValue)
                    }
                )
            }
        }
    }
}