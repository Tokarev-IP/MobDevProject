package com.example.mobdevproject.content.presentation.screens.search

import android.content.Context
import android.util.Log
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
import com.example.mobdevproject.content.data.MenuMainData
import com.example.mobdevproject.content.domain.blogic.BarCodeScanner
import com.example.mobdevproject.content.presentation.SearchNavHostRoute
import com.example.mobdevproject.content.presentation.SearchScreenStates
import com.example.mobdevproject.content.presentation.SearchUiIntents
import com.example.mobdevproject.content.presentation.SearchViewModel
import com.example.mobdevproject.content.presentation.composable.StartDestinationCompose

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController(),
    startDestination: String = SearchNavHostRoute.StartDestinationCompose.route,
    context: Context,
) {
    val menuMainDataList by searchViewModel.getMenuListFlow().collectAsState()
    val screenState by searchViewModel.getScreenStateFlow().collectAsState()
    val menuMainData by searchViewModel.getMenuMainDataFlow().collectAsState()
    val menuItemsDataList by searchViewModel.getMenuItemsDataListFlow().collectAsState()

    when (screenState) {
        is SearchScreenStates.MenuListScreen -> {
            navController.navigate(SearchNavHostRoute.MenuListScreen.route)
        }

        is SearchScreenStates.MenuScreen -> {
            navController.navigate(SearchNavHostRoute.MenuScreen.route)
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
    ) { paddingValues: PaddingValues ->

        NavHost(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            navController = navController,
            startDestination = startDestination,
        ) {
            composable(route = SearchNavHostRoute.StartDestinationCompose.route) {
                Log.d("DAVAI", "StartDestinationCompose")
                StartDestinationCompose()
                searchViewModel.setUiIntent(SearchUiIntents.GetAllMenus)
            }

            composable(
                route = SearchNavHostRoute.MenuScreen.route,
            ) { backStackEntry ->
                MenuScreen(
                    menuMainData = menuMainData,
                    menuDataList = menuItemsDataList,
                )
                searchViewModel.setUiIntent(SearchUiIntents.NullScreenState)
            }

            composable(route = SearchNavHostRoute.MenuListScreen.route) {
                MenuListScreen(
                    menuMainDataList = menuMainDataList,
                    onBarCode = {
                        BarCodeScanner(context).startScan(
                            onSuccess = { qrCodeText: String? ->
                                searchViewModel.setUiIntent(SearchUiIntents.UseQrCode(qrCodeText))
                            },
                            onFailure = {

                            }
                        )
                    },
                    onClick = { menuMainData: MenuMainData ->
                        searchViewModel.setUiIntent(SearchUiIntents.GoToMenuScreen(menuMainData))
                    },
                    onSearchMenu = { id: String ->
                        searchViewModel.setUiIntent(SearchUiIntents.GoToMenuScreenUsingQr(id))
                    }
                )
                searchViewModel.setUiIntent(SearchUiIntents.NullScreenState)
            }
        }
    }
}