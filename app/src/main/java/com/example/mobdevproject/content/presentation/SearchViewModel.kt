package com.example.mobdevproject.content.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobdevproject.content.data.MenuData
import com.example.mobdevproject.content.data.MenuMainData
import com.example.mobdevproject.content.domain.interfaces.FirestoreReadUseCaseInterface
import com.example.mobdevproject.content.domain.interfaces.FirestoreSearchUseCaseInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val firestoreSearchUseCaseInterface: FirestoreSearchUseCaseInterface,
    private val firestoreReadUseCaseInterface: FirestoreReadUseCaseInterface,
) : ViewModel() {

    private val menuListVM: MutableStateFlow<List<MenuMainData>> = MutableStateFlow(listOf())
    private val menuListVMFlow = menuListVM.asStateFlow()

    private val screenState: MutableStateFlow<SearchScreenStates?> = MutableStateFlow(null)
    private val screenStateFlow = screenState.asStateFlow()

    private val menuItemsDataList: MutableStateFlow<List<MenuData>> = MutableStateFlow(listOf())
    private val menuItemsDataListFlow = menuItemsDataList.asStateFlow()
    private val menuMainDataVM: MutableStateFlow<MenuMainData> = MutableStateFlow(MenuMainData())
    private val menuMainDataFlow = menuMainDataVM.asStateFlow()

    fun getMenuListFlow() = menuListVMFlow
    fun getScreenStateFlow() = screenStateFlow
    fun getMenuItemsDataListFlow() = menuItemsDataListFlow
    fun getMenuMainDataFlow() = menuMainDataFlow

    fun setUiIntent(uiIntents: SearchUiIntents) {
        when (uiIntents) {
            is SearchUiIntents.UseQrCode -> {
                uiIntents.qrCodeText?.let { qrCodeText ->
                    getDataById(qrCodeText)
                    screenState.value = SearchScreenStates.MenuScreen
                }
            }

            is SearchUiIntents.GetAllMenus -> {
                getAllMenu()
            }

            is SearchUiIntents.GoToMenuScreenUsingQr -> {
                getMenuByName(uiIntents.text)
                screenState.value = SearchScreenStates.MenuScreen
            }

            is SearchUiIntents.GoToMenuScreen -> {
                getDataByMenuId(uiIntents.menuMainData)
                screenState.value = SearchScreenStates.MenuScreen
            }

            is SearchUiIntents.NullScreenState -> {
                screenState.value = null
            }
        }
    }

    private fun getAllMenu() {
        viewModelScope.launch {
            val data = async { firestoreSearchUseCaseInterface.getAllMenu() }.await()
            menuListVM.value = data
            screenState.value = SearchScreenStates.MenuListScreen
        }
    }

    private fun getMenuByName(text: String) {
        viewModelScope.launch {
            val data = async { firestoreSearchUseCaseInterface.getMenuByName(name = text) }.await()
            menuListVM.value = data
        }
    }

    private fun getDataByMenuId(menuMainData: MenuMainData) {
        viewModelScope.launch {
            val menuDataListAsync =
                async { firestoreReadUseCaseInterface.getMenuDataList(menuId = menuMainData.id) }
            val menuDataList = menuDataListAsync.await()

            menuItemsDataList.value = menuDataList
            menuMainDataVM.value = menuMainData
        }
    }

    private fun getDataById(menuId: String) {
        viewModelScope.launch {
            val menuMainDataAsync =
                async { firestoreReadUseCaseInterface.getMenuMainData(menuId = menuId) }
            val menuDataListAsync =
                async { firestoreReadUseCaseInterface.getMenuDataList(menuId = menuId) }

            val menuDataList = menuDataListAsync.await()
            val menuMainData = menuMainDataAsync.await()

            menuMainData?.let {
                menuItemsDataList.value = menuDataList
                menuMainDataVM.value = menuMainData
            }
        }
    }

}