package com.example.roomer.utils.navigation

import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph
@NavGraph
annotation class SignUpNavGraph(
    val start: Boolean = false
)
