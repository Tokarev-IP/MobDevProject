package com.example.mobdevproject.content.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobdevproject.content.presentation.screens.mymenu.CheckMyMenuScreen
import com.example.mobdevproject.content.presentation.screens.mymenu.EditMyMenuMainData

@Composable
fun ContentActivityCompose(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = NavHostRoute.CheckMyMenuScreen.route,
    contentViewModel: ContentViewModel = hiltViewModel()
) {
    val uiState by contentViewModel.getUiStateFlow().collectAsState()
    val screenStates by contentViewModel.getScreenStateFlow().collectAsState()

    when (screenStates) {
        is ScreenStates.CheckMyMenuScreen -> {
            navController.navigate(NavHostRoute.CheckMyMenuScreen.route)
        }
        is ScreenStates.CreateMyMenuScreen -> {
            navController.navigate(NavHostRoute.CreateMyMenuScreen.route)
        }
        is ScreenStates.EditMyMenuMainData -> {
            navController.navigate(NavHostRoute.EditMyMenuMainData.route)
        }
        is ScreenStates.MenuListScreen -> {
            navController.navigate(NavHostRoute.MenuListScreen.route)
        }
        is ScreenStates.MenuMapScreen -> {
            navController.navigate(NavHostRoute.MenuMapScreen.route)
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { padding: PaddingValues ->

        NavHost(
            modifier = modifier
                .fillMaxSize()
                .padding(padding),
            navController = navController,
            startDestination = startDestination,
        ) {
            composable(route = NavHostRoute.CheckMyMenuScreen.route) {
                CheckMyMenuScreen(
                    uiState = uiState,
                    onCreateMenu = { /*TODO*/ },
                    onRefresh = {/*TODO*/ }
                )
            }
            composable(route = NavHostRoute.CreateMyMenuScreen.route) {
//                CreateMyMenuScreen(
//                    menuMainData = ,
//                    menuDataList =
//                )
            }
            composable(route = NavHostRoute.EditMyMenuMainData.route) {
                EditMyMenuMainData(
                    uiState = uiState,
                    onChoosePicture = { /*TODO*/ },
                    onClearPicture = { /*TODO*/ },
                    onSave = { /*TODO*/ },
                    onCancel = { /*TODO*/ }
                )
            }
        }
    }
}