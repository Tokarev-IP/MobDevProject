package com.example.mobdevproject.content.presentation

import androidx.lifecycle.ViewModel
import com.example.mobdevproject.content.domain.interfaces.FirestoreAddUseCaseInterface
import com.example.mobdevproject.content.domain.interfaces.FirestoreReadUseCaseInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ContentViewModel @Inject constructor(
    private val firestoreAddUseCaseInterface: FirestoreAddUseCaseInterface,
    private val firestoreReadUseCaseInterface: FirestoreReadUseCaseInterface,
) : ViewModel() {

    private val uiState: MutableStateFlow<UiStates> = MutableStateFlow(UiStates.Show)
    private val uiStateFlow = uiState.asStateFlow()

    private val screenState: MutableStateFlow<ScreenStates?> = MutableStateFlow(null)
    private val screenStateFlow = screenState.asStateFlow()

    fun getUiStateFlow() = uiStateFlow
    fun getScreenStateFlow() = screenStateFlow

    fun setUiIntent(uiIntent: UiIntents) {
        when (uiIntent) {
            is UiIntents.CreateMenu -> {

            }

            is UiIntents.UploadMainMenuData -> {

            }
        }
    }
}