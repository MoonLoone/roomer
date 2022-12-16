package com.example.roomer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.compose.rememberNavController
import com.example.roomer.ui_components.NavHostContainer
import com.example.roomer.ui_components.Navbar

class NavbarFragment : Fragment() {

    @ExperimentalMaterial3Api
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(context = requireContext()).apply {
            setContent {
                val navController = rememberNavController()
                Scaffold(bottomBar = { Navbar(navController) }) {
                    NavHostContainer(navController = navController, paddingValues = it)
                }
            }
        }
    }

}