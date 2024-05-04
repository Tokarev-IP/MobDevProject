package com.example.mobdevproject.content.presentation.screens.mymenu

import android.content.Context
import android.net.Uri
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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mobdevproject.content.data.MenuData
import com.example.mobdevproject.content.data.MenuMainData
import com.example.mobdevproject.content.data.MyMenuId
import com.example.mobdevproject.content.domain.blogic.CompressPicture
import com.example.mobdevproject.content.presentation.EditNavHostRoute
import com.example.mobdevproject.content.presentation.EditScreenStates
import com.example.mobdevproject.content.presentation.MyMenuUiIntents
import com.example.mobdevproject.content.presentation.MyMenuViewModel
import com.example.mobdevproject.content.presentation.composable.StartDestinationCompose

@Composable
fun EditScreen(
    modifier: Modifier = Modifier,
    myMenuViewModel: MyMenuViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController(),
    startDestination: String = EditNavHostRoute.StartDestinationCompose.route,
    context: Context,
) {
    val uiState by myMenuViewModel.getUiStateFlow().collectAsState()

    val editScreenState by myMenuViewModel.getScreenStateFlow().collectAsState()
    val menuMainData by myMenuViewModel.getMenuMainDataFlow().collectAsState()
    val menuDataList by myMenuViewModel.getMenuDataListFlow().collectAsState()

    when (editScreenState) {
        is EditScreenStates.CheckMyMenuScreen -> {
            navController.navigate(route = EditNavHostRoute.CheckMyMenuScreen.route)
        }

        is EditScreenStates.MenuListScreen -> {
            navController.navigate(route = EditNavHostRoute.MenuListScreen.route)
        }

        is EditScreenStates.EditMyMenuScreen -> {
            navController.navigate(route = EditNavHostRoute.EditMyMenuScreen.route)
        }

        is EditScreenStates.EditMyMenuMainScreen -> {
            val data =
                (editScreenState as EditScreenStates.EditMyMenuMainScreen).menuMainData
            navController.navigate(
                route = EditNavHostRoute.EditMyMenuMainScreen.route + "/${data.id}" + "/${data.name}"
            )
        }

        is EditScreenStates.EditMyMenuItemScreen -> {
            val menuData = (editScreenState as EditScreenStates.EditMyMenuItemScreen).menuData
            navController.navigate(
                route = EditNavHostRoute.EditMyMenuItemScreen.route + "/${menuData.name}" + "/${menuData.id}" + "/${menuData.price}"
            )
        }

        is EditScreenStates.QrCodeScreen -> {
            val menuId = (editScreenState as EditScreenStates.QrCodeScreen).menuId
            navController.navigate(route = EditNavHostRoute.QrCodeScreen.route + "/${menuId}")
        }

        is EditScreenStates.GoBackNavigation -> {
            navController.popBackStack()
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
    ) { padding: PaddingValues ->

        NavHost(
            modifier = modifier
                .fillMaxSize()
                .padding(padding),
            navController = navController,
            startDestination = startDestination,
        ) {
            composable(route = EditNavHostRoute.StartDestinationCompose.route) {
                StartDestinationCompose()
                myMenuViewModel.setUiIntent(MyMenuUiIntents.CheckMyMenu)
            }

            composable(route = EditNavHostRoute.CheckMyMenuScreen.route) {
                CheckMyMenuScreen(
                    onCreateMenu = { myMenuId: MyMenuId ->
                        myMenuViewModel.setUiIntent(MyMenuUiIntents.CreateMyMenu(myMenuId))
                    },
                    onRefresh = { myMenuViewModel.setUiIntent(MyMenuUiIntents.CheckMyMenu) }
                )
                myMenuViewModel.setUiIntent(MyMenuUiIntents.NullStateScreen)
            }

            composable(
                route = EditNavHostRoute.EditMyMenuMainScreen.route + "/{id}" + "/{name}",
                arguments = listOf(
                    navArgument("id") { type = NavType.StringType },
                    navArgument("name") { type = NavType.StringType },
                ),
            ) { backStackEntry ->
                val idString = backStackEntry.arguments?.getString("id") ?: ""
                val nameString = backStackEntry.arguments?.getString("name") ?: ""

                val pictureUriString = myMenuViewModel.getPictureUriString()

                val menuMainDataString: MenuMainData =
                    MenuMainData(
                        name = nameString,
                        pictureUri = pictureUriString,
                        id = idString
                    )

                EditMyMenuMainScreen(
                    menuMainData = menuMainDataString,
                    onSave = { data: MenuMainData, pictureUri ->
                        val compressedPicture =
                            if (pictureUri != null) CompressPicture().compressImage(
                                context = context,
                                imageUri = pictureUri,
                                maxWidth = 1200,
                                maxHeight = 600,
                                quality = 10,
                            ) else null

                        myMenuViewModel.setUiIntent(
                            MyMenuUiIntents.UploadMainMenuData(
                                data,
                                compressedPicture
                            )
                        )
                    },
                    onCancel = { navController.popBackStack() },
                    goBack = { navController.popBackStack() },
                )

                myMenuViewModel.setUiIntent(MyMenuUiIntents.NullStateScreen)
            }

            composable(
                route = EditNavHostRoute.EditMyMenuScreen.route,
            ) { backStackEntry ->

                EditMyMenuScreen(
                    menuMainData = menuMainData,
                    menuDataList = menuDataList,
                    onEditMenuMain = { data: MenuMainData ->
                        myMenuViewModel.setUiIntent(MyMenuUiIntents.GoToEditMyMenuMainScreen(data))
                    },
                    onAddNewDish = { data: MenuData ->
                        myMenuViewModel.setUiIntent(MyMenuUiIntents.GoToEditMyMenuItemScreen(data))
                    },
                    onEditDish = { data: MenuData ->
                        myMenuViewModel.setUiIntent(MyMenuUiIntents.GoToEditMyMenuItemScreen(data))
                    },
                    onQrCodeShare = { menuId: String ->
                        myMenuViewModel.setUiIntent(MyMenuUiIntents.GoToQrCodeScreen(menuId))
                    },
                    uiState = uiState,
                )
                myMenuViewModel.setUiIntent(MyMenuUiIntents.NullStateScreen)
            }

            composable(
                route = EditNavHostRoute.EditMyMenuItemScreen.route + "/{name}" + "/{id}" + "/{price}",
                arguments = listOf(
                    navArgument("name") { type = NavType.StringType },
                    navArgument("id") { type = NavType.StringType },
                    navArgument("price") { type = NavType.StringType },
                ),
            ) { backStackEntry ->
                val nameString = backStackEntry.arguments?.getString("name") ?: ""
                val idString = backStackEntry.arguments?.getString("id") ?: ""
                val priceString = backStackEntry.arguments?.getString("price") ?: ""

                val price: Double = priceString.toDoubleOrNull() ?: 0.0
                val pictureUriString = myMenuViewModel.getPictureUriString()

                val menuData = MenuData(
                    id = idString,
                    name = nameString,
                    price = price,
                    pictureUri = pictureUriString,
                )

                EditMyMenuItemScreen(
                    menuData = menuData,
                    onCancel = { navController.popBackStack() },
                    onSave = { data: MenuData, pictureUri: Uri? ->
                        val compressedPicture =
                            if (pictureUri != null) CompressPicture().compressImage(
                                context = context,
                                imageUri = pictureUri,
                                maxWidth = 600,
                                maxHeight = 600,
                                quality = 5,
                            ) else null

                        myMenuViewModel.setUiIntent(
                            MyMenuUiIntents.UploadMenuData(
                                data,
                                compressedPicture
                            )
                        )
                    },
                    goBack = { navController.popBackStack() },
                    uiState = uiState,
                )

                myMenuViewModel.setUiIntent(MyMenuUiIntents.NullStateScreen)
            }

            composable(
                route = EditNavHostRoute.QrCodeScreen.route + "/{menuId}",
                arguments = listOf(
                    navArgument("menuId") { type = NavType.StringType },
                ),
            ) { backStackEntry ->
                val menuId = backStackEntry.arguments?.getString("menuId")

                menuId?.let {
                    QrCodeScreen(
                        menuId = menuId,
                        context = context,
                        goBack = { navController.popBackStack() }
                    )
                }
                myMenuViewModel.setUiIntent(MyMenuUiIntents.NullStateScreen)
            }
        }
    }
}