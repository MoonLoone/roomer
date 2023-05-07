package com.example.roomer

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.roomer.domain.model.entities.User
import com.example.roomer.management.NotificationManager
import com.example.roomer.management.PermissionManager
import com.example.roomer.presentation.screens.NavGraphs
import com.example.roomer.presentation.screens.destinations.ChatScreenDestination
import com.example.roomer.presentation.screens.destinations.SearchRoomResultsDestination
import com.example.roomer.presentation.screens.destinations.SearchRoommateResultsDestination
import com.example.roomer.presentation.screens.destinations.UserDetailsScreenDestination
import com.example.roomer.presentation.screens.entrance.signup.SignUpViewModel
import com.example.roomer.presentation.screens.shared_screens.UserDetailsScreen
import com.example.roomer.presentation.screens.shared_screens.UserDetailsScreenViewModel
import com.example.roomer.presentation.screens.shared_screens.chat_screen.ChatScreenViewModel
import com.example.roomer.presentation.ui_components.Navbar
import com.example.roomer.utils.Constants
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var permissionManager: PermissionManager

    @EntryPoint
    @InstallIn(ActivityComponent::class)
    interface ViewModelFactoryProvider {
        fun chatViewModelFactory(): ChatScreenViewModel.Factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionManager.askNotificationPermission()
        NotificationManager.stopAllWorks(this)
        setContent {
            val navController = rememberNavController()
            Scaffold(bottomBar = { Navbar(navController = navController) }) {
                DestinationsNavHost(
                    modifier = Modifier.padding(bottom = it.calculateBottomPadding()),
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
                when (intent.action) {
                    Constants.Notification.ACTION_NOTIFICATION_MATES -> navController.navigate(
                        SearchRoommateResultsDestination.route
                    )
                    Constants.Notification.ACTION_NOTIFICATION_ROOMS -> navController.navigate(
                        SearchRoomResultsDestination.route
                    )
                    Constants.Notification.ACTION_NOTIFICATION_CHAT -> {
                        val chatId = intent.getIntExtra(
                            Constants.Notification.EXTRA_NOTIFICATION_CHAT,
                            0
                        )
                        val recipientId = intent.getIntExtra(
                            Constants.Notification.EXTRA_NOTIFICATION_RECIPIENT,
                            0
                        )
                        if (chatId > 0 && recipientId > 0) {
                            navController.navigate(
                                ChatScreenDestination(recipientUser = User(recipientId)).route
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        NotificationManager.registerAllWorks(context = this)
    }
}
