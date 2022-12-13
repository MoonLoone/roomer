package com.example.roomer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.ui.platform.ComposeView
import com.example.roomer.ui_components.NavigationBar
import utils.NavbarItem

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        findViewById<ComposeView>(R.id.navbar).setContent { 
            NavigationBar(selectNavbarItem = NavbarItem.Chats)
        }
    }
}