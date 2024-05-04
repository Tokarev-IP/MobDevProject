package com.example.mobdevproject.content.presentation

import com.example.mobdevproject.content.data.MenuData
import com.example.mobdevproject.content.data.MenuMainData
import com.example.mobdevproject.content.data.MyMenuId

interface UiStates {
    object Show : UiStates
    object Loading : UiStates
    object Error : UiStates
}

sealed class ContentActivityNavHostRoute(val route: String) {
    data object EditScreen : EditNavHostRoute("editScreen")
    data object SearchScreen : EditNavHostRoute("searchScreen")
    data object SettingsScreen : EditNavHostRoute("settingsScreen")
}

sealed class EditNavHostRoute(val route: String) {
    data object CheckMyMenuScreen : EditNavHostRoute("checkMyMenuScreen")
    data object EditMyMenuMainScreen : EditNavHostRoute("editMyMenuMainScreen")
    data object EditMyMenuItemScreen : EditNavHostRoute("editMyMenuItemScreen")
    data object EditMyMenuScreen : EditNavHostRoute("editMyMenuScreen")
    data object MenuListScreen : EditNavHostRoute("menuListScreen")
    data object StartDestinationCompose : EditNavHostRoute("startDestinationCompose")
    data object QrCodeScreen : EditNavHostRoute("qrCodeScreen")
}

sealed class SearchNavHostRoute(val route: String) {
    data object MenuListScreen : EditNavHostRoute("menuListScreen")
    data object MenuScreen : EditNavHostRoute("menuScreen")
    data object StartDestinationCompose : EditNavHostRoute("startDestinationCompose")
}

sealed interface MyMenuUiIntents {
    data class UploadMenuData(val menuData: MenuData, val byteArray: ByteArray?) : MyMenuUiIntents {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as UploadMenuData

            if (menuData != other.menuData) return false
            if (byteArray != null) {
                if (other.byteArray == null) return false
                if (!byteArray.contentEquals(other.byteArray)) return false
            } else if (other.byteArray != null) return false

            return true
        }

        override fun hashCode(): Int {
            var result = menuData.hashCode()
            result = 31 * result + (byteArray?.contentHashCode() ?: 0)
            return result
        }
    }

    data class UploadMainMenuData(val menuMainData: MenuMainData, val byteArray: ByteArray?) :
        MyMenuUiIntents {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as UploadMainMenuData

            if (menuMainData != other.menuMainData) return false
            if (byteArray != null) {
                if (other.byteArray == null) return false
                if (!byteArray.contentEquals(other.byteArray)) return false
            } else if (other.byteArray != null) return false

            return true
        }

        override fun hashCode(): Int {
            var result = menuMainData.hashCode()
            result = 31 * result + (byteArray?.contentHashCode() ?: 0)
            return result
        }
    }

    data class CreateMyMenu(val myMenuId: MyMenuId) : MyMenuUiIntents
    data object CheckMyMenu : MyMenuUiIntents
    data object DeletePicture : MyMenuUiIntents
    data class GoToEditMyMenuMainScreen(val menuMainData: MenuMainData) : MyMenuUiIntents
    data class GoToEditMyMenuItemScreen(val menuData: MenuData) : MyMenuUiIntents
    data class GoToQrCodeScreen(val menuId: String) : MyMenuUiIntents
    data object NullStateScreen: MyMenuUiIntents
}

sealed interface SearchUiIntents {
    data class UseQrCode(val qrCodeText: String?) : SearchUiIntents
    data object GetAllMenus : SearchUiIntents
    data class GoToMenuScreenUsingQr(val text: String) : SearchUiIntents
    data class GoToMenuScreen(val menuMainData: MenuMainData) : SearchUiIntents
    data object NullScreenState : SearchUiIntents
}

interface EditScreenStates {
    object CheckMyMenuScreen : EditScreenStates
    data class EditMyMenuMainScreen(val menuMainData: MenuMainData) : EditScreenStates
    object MenuListScreen : EditScreenStates
    object EditMyMenuScreen : EditScreenStates
    data class EditMyMenuItemScreen(val menuData: MenuData) : EditScreenStates
    data class QrCodeScreen(val menuId: String) : EditScreenStates
    object GoBackNavigation : EditScreenStates
}

interface SearchScreenStates {
    object MenuListScreen : SearchScreenStates
    object MenuScreen : SearchScreenStates
}

interface SettingsUiIntents {
    data object LoadData: SettingsUiIntents
    data class UpdateEmail(val email: String) : SettingsUiIntents
    data object SignOut: SettingsUiIntents
    data object VerifyEmail: SettingsUiIntents
}

