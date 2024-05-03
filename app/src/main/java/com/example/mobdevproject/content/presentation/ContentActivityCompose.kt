package com.example.mobdevproject.content.presentation

import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobdevproject.content.presentation.screens.mymenu.EditScreen
import com.example.mobdevproject.content.presentation.screens.search.SearchScreen
import com.example.mobdevproject.content.presentation.screens.settings.SettingsScreen

@Composable
fun ContentActivityCompose(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ContentActivityNavHostRoute.SearchScreen.route,
    context: Context,
    onSignOut: () -> Unit,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                var selectedItem by rememberSaveable { mutableIntStateOf(1) }

                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
                    label = { Text(text = "Search") },
                    selected = selectedItem == 1,
                    onClick = {
                        if (selectedItem != 1) {
                            selectedItem = 1
                            navController.navigate(route = ContentActivityNavHostRoute.SearchScreen.route)
                        }
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Edit, contentDescription = "Edit") },
                    label = { Text(text = "Edit") },
                    selected = selectedItem == 2,
                    onClick = {
                        if (selectedItem != 2) {
                            selectedItem = 2
                            navController.navigate(route = ContentActivityNavHostRoute.EditScreen.route)
                        }
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings") },
                    label = { Text(text = "Settings") },
                    selected = selectedItem == 3,
                    onClick = {
                        if (selectedItem != 3) {
                            selectedItem = 3
                            navController.navigate(route = ContentActivityNavHostRoute.SettingsScreen.route)
                        }
                    }
                )
            }
        },
    ) { padding: PaddingValues ->

        NavHost(
            modifier = modifier
                .fillMaxSize()
                .padding(padding),
            navController = navController,
            startDestination = startDestination,
        ) {
            composable(route = ContentActivityNavHostRoute.SearchScreen.route) {
                SearchScreen(context = context)
            }

            composable(route = ContentActivityNavHostRoute.EditScreen.route) {
                EditScreen(context = context)
            }

            composable(route = ContentActivityNavHostRoute.SettingsScreen.route) {
                SettingsScreen(
                    onSignOut = { onSignOut() }
                )
            }

        }
    }
}