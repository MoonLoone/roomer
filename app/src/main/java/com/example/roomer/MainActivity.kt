package com.example.roomer

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.roomer.presentation.screens.NavGraphs
import com.example.roomer.presentation.screens.entrance.signup.SignUpViewModel
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DestinationsNavHost(
                navGraph = NavGraphs.root,
                dependenciesContainerBuilder = {
                    dependency(NavGraphs.signUp) {
                        val parentEntry = remember(navBackStackEntry) {
                            navController.getBackStackEntry(NavGraphs.signUp.route)
                        }
                        viewModel<SignUpViewModel>(parentEntry)
                    }
                }
            )
//            val navController = rememberNavController()
//            NavbarHostContainer(navController = navController)
        }
    }
}
