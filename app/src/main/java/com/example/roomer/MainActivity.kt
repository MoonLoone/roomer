package com.example.roomer

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.roomer.presentation.screens.NavGraphs
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//
//        supportFragmentManager.beginTransaction()
//            .add(R.id.content_fragment, NavbarFragment())
//            .commit()
//    }
        setContent {
            DestinationsNavHost(navGraph = NavGraphs.root)
        }
    }
}