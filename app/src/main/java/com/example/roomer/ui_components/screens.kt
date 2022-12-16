package com.example.roomer.ui_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.roomer.R

@Composable
fun ProfileScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Profile", textAlign = TextAlign.Start, fontSize = 24.sp)
        Image(
            painter = painterResource(id = R.drawable.ordinary_client),
            contentDescription = "Client avatar",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        ProfileHardcodedText("Account", R.drawable.account_icon)
        ProfileHardcodedText("Location", R.drawable.location_icon)
        ProfileHardcodedText("Rating", R.drawable.rating_icon)
        ProfileHardcodedText("Settings", R.drawable.settings_icon)
        ProfileHardcodedText("Logout", R.drawable.logout_icon)
    }
}

@Composable
fun HomeScreen() {
    Text("Hello from home")
}