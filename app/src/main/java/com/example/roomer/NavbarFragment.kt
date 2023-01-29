package com.example.roomer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.example.roomer.presentation.screens.NavGraphs
import com.ramcosta.composedestinations.DestinationsNavHost

class NavbarFragment : Fragment() {

    @ExperimentalMaterial3Api
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(context = requireContext()).apply {
            setContent {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}