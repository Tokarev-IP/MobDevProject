package com.example.mobdevproject.content.presentation

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobdevproject.content.data.MenuData
import com.example.mobdevproject.content.data.MenuMainData
import com.example.mobdevproject.content.data.MyMenuId
import com.example.mobdevproject.content.domain.interfaces.FirestoreAddUseCaseInterface
import com.example.mobdevproject.content.domain.interfaces.FirestoreReadUseCaseInterface
import com.example.mobdevproject.content.domain.interfaces.StorageUseCaseInterface
import com.example.mobdevproject.login.domain.interfaces.AuthUseCaseInterface
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MyMenuViewModel @Inject constructor(
    private val firestoreAddUseCaseInterface: FirestoreAddUseCaseInterface,
    private val firestoreReadUseCaseInterface: FirestoreReadUseCaseInterface,
    private val authUseCaseInterface: AuthUseCaseInterface,
    private val storageUseCaseInterface: StorageUseCaseInterface,
) : ViewModel() {

    private var myMenuIdVM: String = ""
    private var userIdVM: String = ""
    private var pictureUriString: String? = null

    private val uiState: MutableStateFlow<UiStates> = MutableStateFlow(UiStates.Show)
    private val uiStateFlow = uiState.asStateFlow()

    private val screenState: MutableStateFlow<EditScreenStates?> = MutableStateFlow(null)
    private val screenStateFlow = screenState.asStateFlow()

    private val menuMainDataVM: MutableStateFlow<MenuMainData> = MutableStateFlow(MenuMainData())
    private val menuMainDataFlow = menuMainDataVM.asStateFlow()

    private val menuDataListVM: MutableStateFlow<MutableList<MenuData>> = MutableStateFlow(
        mutableListOf()
    )
    private val menuDataListFlow = menuDataListVM.asStateFlow()

    fun getUiStateFlow() = uiStateFlow
    fun getScreenStateFlow() = screenStateFlow
    fun getMenuMainDataFlow() = menuMainDataFlow
    fun getMenuDataListFlow() = menuDataListFlow
    fun getPictureUriString() = pictureUriString

    private fun setUiState(state: UiStates) {
        uiState.value = state
    }

    private fun setScreenState(state: EditScreenStates?) {
        screenState.value = state
    }

    fun setUiIntent(uiIntent: MyMenuUiIntents) {
        when (uiIntent) {
            is MyMenuUiIntents.UploadMainMenuData -> {
                setUiState(UiStates.Loading)
                uploadMenuMainData(uiIntent.menuMainData, uiIntent.byteArray)
                setUiState(UiStates.Show)
            }

            is MyMenuUiIntents.CheckMyMenu -> {
                setUiState(UiStates.Loading)
                checkMyMenu()
                setScreenState(EditScreenStates.EditMyMenuScreen)
                setUiState(UiStates.Show)
            }

            is MyMenuUiIntents.UploadMenuData -> {
                setUiState(UiStates.Loading)
                uploadMenuData(uiIntent.menuData, uiIntent.byteArray, myMenuIdVM)
                setUiState(UiStates.Show)
            }

            is MyMenuUiIntents.DeletePicture -> {
                setUiState(UiStates.Loading)
                setUiState(UiStates.Show)
            }

            is MyMenuUiIntents.CreateMyMenu -> {
                setUiState(UiStates.Loading)
                uploadMyMenuId(uiIntent.myMenuId)
            }

            is MyMenuUiIntents.GoToEditMyMenuMainScreen -> {
                pictureUriString = uiIntent.menuMainData.pictureUri
                setScreenState(EditScreenStates.EditMyMenuMainScreen(uiIntent.menuMainData))
            }

            is MyMenuUiIntents.GoToEditMyMenuItemScreen -> {
                pictureUriString = uiIntent.menuData.pictureUri
                setScreenState(EditScreenStates.EditMyMenuItemScreen(uiIntent.menuData))
            }

            is MyMenuUiIntents.GoToQrCodeScreen -> {
                setUiState(UiStates.Loading)
                setScreenState(EditScreenStates.QrCodeScreen(uiIntent.menuId))
                setUiState(UiStates.Show)
            }

            is MyMenuUiIntents.NullStateScreen -> {
                setScreenState(null)
            }
        }
    }

    private fun downloadMenuData(menuId: String) {
        viewModelScope.launch {
            val menuMainData = withContext(Dispatchers.IO) {
                firestoreReadUseCaseInterface.getMenuMainData(menuId = menuId)
            }
            val menuDataList = withContext(Dispatchers.IO) {
                firestoreReadUseCaseInterface.getMenuDataList(menuId = menuId)
            }

            if (menuMainData != null) {
                menuMainDataVM.value = menuMainData
                menuDataListVM.value = menuDataList.toMutableList()
            } else {
                menuMainDataVM.value = MenuMainData("Empty name", null, myMenuIdVM)
            }
        }
    }

    private fun checkMyMenu() {
        authUseCaseInterface.getCurrentUser(
            onUser = { user: FirebaseUser ->
                userIdVM = user.uid
                viewModelScope.launch {
                    val myMenuId =
                        withContext(Dispatchers.IO) { firestoreReadUseCaseInterface.checkMyMenu(userId = user.uid) }

                    if (myMenuId != null) {
                        myMenuIdVM = myMenuId.id
                        downloadMenuData(myMenuId.id)
                    } else {
                        Log.e("error", "my Menu ID is null")
                    }
                }
            },
            onNullUser = {
                setUiState(UiStates.Error)
            }
        )
    }

    private fun uploadMyMenuId(menuId: MyMenuId) {
        firestoreAddUseCaseInterface.addMyMenuId(
            userId = userIdVM,
            menuId = menuId.id,
            onSuccess = {
                downloadMenuData(menuId.id)
            },
            onFailure = {
                UiStates.Show
            }
        )
    }

    private fun uploadMenuMainData(menuMainData: MenuMainData, byteArray: ByteArray?) {
        viewModelScope.launch {
            byteArray?.let {
                val pictureUploadAsync = async {
                    uploadMenuPicture(byteArray, menuMainData.id)
                }
                if (pictureUploadAsync.await() == null) {
                    val uriAsync: Uri? =
                        async { storageUseCaseInterface.downloadMenuPictureUri(menuId = menuMainData.id) }.await()
                    if (uriAsync != null)
                        menuMainData.pictureUri = uriAsync.toString()
                    else
                        menuMainData.pictureUri = null
                }
            }
            val menuMainDataAsync = async {
                firestoreAddUseCaseInterface.addMenuMainData(
                    data = menuMainData,
                    menuId = menuMainData.id,
                )
            }
            if (menuMainDataAsync.await() == null) {
                setScreenState(EditScreenStates.GoBackNavigation)
                downloadMenuData(myMenuIdVM)
            } else
                setUiState(UiStates.Error)
        }
    }

    private fun uploadMenuData(menuData: MenuData, byteArray: ByteArray?, menuId: String) {
        viewModelScope.launch {
            byteArray?.let {
                val pictureUploadAsync = async {
                    uploadDishPicture(byteArray = byteArray, dishId = menuData.id, menuId)
                }
                if (pictureUploadAsync.await() == null) {
                    val uriAsync: Uri? =
                        async {
                            storageUseCaseInterface.downloadMenuDishPictureUri(
                                menuId = menuId,
                                dishId = menuData.id
                            )
                        }.await()
                    if (uriAsync != null) {
                        menuData.pictureUri = uriAsync.toString()
                    } else
                        menuData.pictureUri = null
                }
            }
            val menuDataAsync = async {
                firestoreAddUseCaseInterface.addMenuData(
                    data = menuData,
                    menuId = menuId,
                    documentId = menuData.id,
                )
            }
            if (menuDataAsync.await() == null) {
                setScreenState(EditScreenStates.GoBackNavigation)
                downloadMenuData(myMenuIdVM)
            } else
                setUiState(UiStates.Error)
        }
    }

    private suspend fun uploadMenuPicture(byteArray: ByteArray, menuId: String): Unit? {
        return storageUseCaseInterface.uploadMenuPicture(
            menuId = menuId,
            byteArray = byteArray,
        )
    }

    private suspend fun uploadDishPicture(byteArray: ByteArray, dishId: String, menuId: String): Unit? {
        return storageUseCaseInterface.uploadMenuDishPicture(
            menuId = menuId,
            dishId = dishId,
            byteArray = byteArray,
        )
    }
}