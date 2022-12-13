package com.example.roomer.ui_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomer.R
import utils.NavbarItem

@Composable
fun NavigationBar(selectNavbarItem: NavbarItem) {
    Row(
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
            .background(
                color = colorResource(id = R.color.secondary_color),
            )
            .padding(top = 12.dp, bottom = 16.dp, start = 4.dp, end = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (itemName in NavbarItem.values().map { it.name }) {
            NavbarItemImg(isSelected = (itemName == selectNavbarItem.name), itemName = itemName)
        }
    }
}

@Composable
fun NavbarItemImg(isSelected: Boolean, itemName: String) {
    Column(
        Modifier
            .width(75.dp)
            .height(80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isSelected){
            Image(
                painter = painterResource(id = NavbarItem.valueOf(itemName).iconSelected),
                modifier = Modifier
                    .width(64.dp)
                    .height(32.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(colorResource(id = R.color.primary)),
                contentDescription = NavbarItem.valueOf(itemName).description,
            )
        }
        else{
            Image(
                painter = painterResource(id = NavbarItem.valueOf(itemName).iconUnSelected),
                modifier = Modifier
                    .width(32.dp)
                    .height(32.dp),
                contentScale = ContentScale.Crop,
                contentDescription = NavbarItem.valueOf(itemName).description,
            )
        }

        Text(text = NavbarItem.valueOf(itemName).description, fontSize = 12.sp)
    }
}