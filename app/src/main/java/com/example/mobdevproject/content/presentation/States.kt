package com.example.mobdevproject.content.presentation

interface UiStates{
    object Show: UiStates
    object Loading: UiStates
    object Error: UiStates
}

sealed class NavHostRoute(val route: String) {
    data object CheckMyMenuScreen: NavHostRoute("CheckMyMenuScreen")
    data object CreateMyMenuScreen: NavHostRoute("CreateMyMenuScreen")
    data object EditMyMenuMainData: NavHostRoute("EditMyMenuMainData")
    data object MenuListScreen: NavHostRoute("MenuListScreen")
    data object MenuMapScreen: NavHostRoute("MenuMapScreen")
}

sealed interface UiIntents{
    object CreateMenu: UiIntents
    object  UploadMainMenuData: UiIntents
}

interface ScreenStates{
    object CheckMyMenuScreen: ScreenStates
    object CreateMyMenuScreen: ScreenStates
    object EditMyMenuMainData: ScreenStates
    object MenuListScreen: ScreenStates
    object MenuMapScreen: ScreenStates
}