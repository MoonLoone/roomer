package com.example.roomer

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.roomer.data.remote.NotificationManager
import com.example.roomer.management.PermissionManager
import com.example.roomer.presentation.screens.NavGraphs
import com.example.roomer.presentation.screens.destinations.SearchRoomResultsDestination
import com.example.roomer.presentation.screens.destinations.SearchRoomScreenDestination
import com.example.roomer.presentation.screens.destinations.SearchRoommateResultsDestination
import com.example.roomer.presentation.screens.entrance.signup.SignUpViewModel
import com.example.roomer.presentation.screens.search_screens.SearchRoommateResults
import com.example.roomer.presentation.ui_components.Navbar
import com.example.roomer.utils.Constants
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val permissionManager: PermissionManager by lazy { PermissionManager(this, this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionManager.askNotificationPermission()
        NotificationManager.stopAllWorks(this)
        setContent {
            val navController = rememberNavController()
            Scaffold(bottomBar = { Navbar(navController = navController) }) {
                val paddingValues = it
                DestinationsNavHost(
                    navGraph = NavGraphs.root,
                    navController = navController,
                    dependenciesContainerBuilder = {
                        dependency(NavGraphs.signUp) {
                            val parentEntry = remember(navBackStackEntry) {
                                navController.getBackStackEntry(NavGraphs.signUp.route)
                            }
                            viewModel<SignUpViewModel>(parentEntry)
                        }
                    }
                )
                when (intent.action){
                    Constants.ACTION_NOTIFICATION_MATES -> navController.navigate(
                        SearchRoommateResultsDestination.route)
                    Constants.ACTION_NOTIFICATION_ROOMS -> navController.navigate(
                        SearchRoomResultsDestination.route)
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        NotificationManager.registerAllWorks(context = this)
    }

}
