package com.example.roomer.ui_components
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomer.R

//@Composable
//fun StartScreen() {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
//            .padding(start = 40.dp, end = 40.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center,
//    ){
//        Text(
//            text = "Welcome to Roomer",
//            fontSize = 30.sp,
//            fontWeight = FontWeight.Medium,
//            textAlign = TextAlign.Center
//        )
//        Text(
//            text = "Get started with us",
//            fontSize = 20.sp,
//            fontWeight = FontWeight.Normal,
//            textAlign = TextAlign.Center,
//            color = colorResource(id = R.color.text_secondary)
//        )
//        GreenButtonPrimary(
//            text = "Login",
//            modifier = Modifier
//                .padding(top = 16.dp)
//                .fillMaxWidth(),
//            ) {}
//        GreenButtonOutline(
//            text = "Sign Up",
//            modifier = Modifier
//                .padding(top = 8.dp)
//                .fillMaxWidth(),
//            ) {}
//    }
//}
//
//@Composable
//fun LoginScreen() {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center,
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(start = 40.dp, end = 40.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.spacedBy(16.dp),
//        ){
//            Text(
//                text = "Login",
//                fontSize = 24.sp,
//                fontWeight = FontWeight.Medium,
//                textAlign = TextAlign.Center
//            )
//            var emailValue by rememberSaveable {
//                mutableStateOf("")
//            }
//            var passwordValue by rememberSaveable {
//                mutableStateOf("")
//            }
//            EmailField(
//                value = emailValue,
//                onValueChange = { emailValue = it }
//            )
//            PasswordField(
//                value = passwordValue,
//                onValueChange = { passwordValue = it }
//            )
//            GreenButtonPrimary(
//                modifier = Modifier
//                    .fillMaxWidth(),
//                text = "Login") {
//
//            }
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.Center
//            ) {
//                Text(
//                    text = "Don't have an account?",
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight.Medium,
//                    textAlign = TextAlign.Center
//                )
//                ClickableText(
//                    text = AnnotatedString("Sign Up"),
//                    modifier = Modifier
//                        .padding(start=8.dp),
//                    style = TextStyle(
//                        fontSize = 16.sp,
//                        fontWeight = FontWeight.Medium,
//                        color = colorResource(id = R.color.primary_dark),
//                        textAlign = TextAlign.Center
//
//                    ),
//                    onClick = {
//
//                    }
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun SignUpScreen1() {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center,
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(start = 40.dp, end = 40.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.spacedBy(16.dp),
//        ) {
//            Text(
//                text = "Sign Up",
//                fontSize = 24.sp,
//                fontWeight = FontWeight.Medium,
//                textAlign = TextAlign.Center
//            )
//            var emailValue by rememberSaveable {
//                mutableStateOf("")
//            }
//            var passwordValue by rememberSaveable {
//                mutableStateOf("")
//            }
//            var confirmPasswordValue by rememberSaveable {
//                mutableStateOf("")
//            }
//            EmailField(
//                value = emailValue,
//                onValueChange = { emailValue = it }
//            )
//            PasswordField(
//                value = passwordValue,
//                onValueChange = { passwordValue = it }
//            )
//            PasswordField(
//                value = confirmPasswordValue,
//                label = "Confirm password",
//                placeholder = "Confirm password here",
//                onValueChange = { confirmPasswordValue = it }
//            )
//            GreenButtonPrimary(
//                text = "Confirm",
//                modifier = Modifier
//                    .fillMaxWidth()
//            ) {
//
//            }
//        }
//    }
//}
///*TODO
//*  Figure out what causes out of hand padding: Date field or Sex field*/
//@Composable
//fun SignUpScreen2() {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White),
//        horizontalAlignment = Alignment.Start,
//        verticalArrangement = Arrangement.Center,
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(start = 40.dp, end = 40.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.spacedBy(16.dp),
//        ) {
//            LinearProgressIndicator(
//                modifier = Modifier
//                    .fillMaxWidth(),
//                color = colorResource(id = R.color.primary_dark),
//                progress = 0.2f
//            )
//            Text(
//                text = "Tell us more about you",
//                fontSize = 24.sp,
//                fontWeight = FontWeight.Medium,
//                textAlign = TextAlign.Start
//            )
//            ScreenTextField(
//                textHint = "Your first name here",
//                label = "First Name",
//                paddingValues = PaddingValues(0.dp)
//            )
//            ScreenTextField(
//                textHint = "Your last name here",
//                label = "Last Name",
//                paddingValues = PaddingValues(0.dp)
//            )
//            DateField(
//                label = "Date Of Birth",
//                paddingValues = PaddingValues(0.dp)
//            )
//            SelectSex()
//            GreenButtonPrimary(
//                text = "Continue",
//                modifier = Modifier
//                    .fillMaxWidth()
//            ) {
//
//            }
//        }
//    }
//}
//
//@Composable
//fun SignUpScreen3() {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center,
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(start = 40.dp, end = 40.dp),
//            horizontalAlignment = Alignment.Start,
//            verticalArrangement = Arrangement.spacedBy(16.dp),
//        ) {
//            LinearProgressIndicator(
//                modifier = Modifier
//                    .fillMaxWidth(),
//                color = colorResource(id = R.color.primary_dark),
//                progress = 0.4f
//            )
//            Text(
//                text = "Just basic profile info",
//                fontSize = 24.sp,
//                fontWeight = FontWeight.Medium,
//                textAlign = TextAlign.Start
//            )
//            Text(
//                text = "Add profile picture",
//                fontSize = 16.sp,
//                fontWeight = FontWeight.Medium,
//                textAlign = TextAlign.Start
//            )
//            ProfilePicture()
//            ScreenTextField(
//                textHint = "Write something about you",
//                label = "About Me",
//                paddingValues = PaddingValues(0.dp)
//            )
//            DropdownTextField(
//                listOfItems = listOf(
//                "Employed", "Unemployed", "Searching for a job"
//                ),
//                label = "What you currently do?",
//                paddingValues = PaddingValues(0.dp)
//            )
//            SelectAddressField(
//                label = "Select address on the map",
//                placeholder = "Here will be your address"
//            )
//            GreenButtonPrimary(
//                text = "Confirm",
//                modifier = Modifier
//                    .fillMaxWidth()
//            ) {
//
//            }
//        }
//    }
//}
//
//@Composable
//fun HabitsScreen() {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center,
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(start = 40.dp, end = 40.dp),
//            horizontalAlignment = Alignment.Start,
//            verticalArrangement = Arrangement.spacedBy(16.dp),
//        ) {
//            LinearProgressIndicator(
//                modifier = Modifier
//                    .fillMaxWidth(),
//                color = colorResource(id = R.color.primary_dark),
//                progress = 0.6f
//            )
//            Text(
//                text = "Tell us about your living habits",
//                fontSize = 24.sp,
//                fontWeight = FontWeight.Medium,
//                textAlign = TextAlign.Start
//            )
//            ButtonsRow(
//                label = "Your usual sleep time",
//                values = listOf("Night","Day","Occasionally")
//            )
//            ButtonsRow(
//                label = "Attitude to alcohol",
//                values = listOf("Positive","Negative","Indifferent")
//            )
//            ButtonsRow(
//                label = "Attitude to smoking",
//                values = listOf("Positive","Negative","Indifferent")
//            )
//            ButtonsRow(
//                label = "Your personality type",
//                values = listOf("Extraverted","Introverted","Mixed")
//            )
//            ButtonsRow(
//                label = "Clean habits",
//                values = listOf("Neat","It Depends","Chaos")
//            )
//            GreenButtonPrimary(
//                text = "Confirm",
//                modifier = Modifier
//                    .fillMaxWidth()
//            ) {
//
//            }
//
//        }
//    }
//}
//
////@Preview
////@Composable
////fun InterestsScreen() {
////    Column(
////        modifier = Modifier
////            .fillMaxSize()
////            .background(Color.White),
////        horizontalAlignment = Alignment.CenterHorizontally,
////        verticalArrangement = Arrangement.Center,
////    ) {
////        Column(
////            modifier = Modifier
////                .fillMaxWidth()
////                .padding(start = 40.dp, end = 40.dp),
////            horizontalAlignment = Alignment.Start,
////            verticalArrangement = Arrangement.spacedBy(16.dp),
////        ) {
////            LinearProgressIndicator(
////                modifier = Modifier
////                    .fillMaxWidth(),
////                color = colorResource(id = R.color.primary_dark),
////                progress = 0.8f
////            )
////            Text(
////                text = "Tell us about your interests",
//                fontSize = 24.sp,
//                fontWeight = FontWeight.Medium,
//                textAlign = TextAlign.Start
//            )
//            InterestsButtons(
//                label = "Select a maximum of 10",
//                values = listOf(
//                    "Football","Basketball","Baseball",
//                    "Swimming","Gym","Reading",
//                    "Movies","TV Shows","Singing",
//                    "Painting","Cooking",),
//                chooseLimit = 10
//            )
//            GreenButtonPrimary(
//                text = "Finish",
//                modifier = Modifier
//                    .fillMaxWidth()
//            ) {
//
//            }
//        }
//    }
//}
