package com.example.mobdevproject.content.presentation.screens.mymenu

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mobdevproject.R
import com.example.mobdevproject.content.data.MenuMainData
import com.example.mobdevproject.content.presentation.MyMenuViewModel
import com.example.mobdevproject.content.presentation.UiStates
import com.example.mobdevproject.content.presentation.composable.CancelAndSaveButtonsRow
import com.example.mobdevproject.content.presentation.composable.ChoosePicture
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.GoogleMapComposable
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalMaterial3Api::class)
@GoogleMapComposable
@Composable
fun EditMyMenuMainScreen(
    modifier: Modifier = Modifier,
    maximumNameLetters: Int = 60,
    menuMainData: MenuMainData,
    myMenuViewModel: MyMenuViewModel = hiltViewModel(),
    onSave: (menuMainData: MenuMainData, newPictureUri: Uri?) -> Unit,
    onCancel: () -> Unit,
    goBack: () -> Unit,
) {
    val uiState by myMenuViewModel.getUiStateFlow().collectAsState()

    var pictureUriString by rememberSaveable { mutableStateOf(menuMainData.pictureUri) }
    var nameText: String by rememberSaveable { mutableStateOf(menuMainData.name) }
    var pictureUri: Uri? by rememberSaveable { mutableStateOf(null) }

    val pickPictureLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
            if (uri != null)
                pictureUri = uri
        }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = modifier.padding(top = 12.dp),
                title = {
                    Text(text = "Edit menu")
                },
                navigationIcon = {
                    IconButton(onClick = { goBack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Share qr code"
                        )
                    }
                },
            )
        }
    ) { paddingValues: PaddingValues ->
        when (uiState) {
            is UiStates.Loading -> {
                Column(
                    modifier = modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    CircularProgressIndicator()
                }
            }

            is UiStates.Show -> {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    ChoosePicture(
                        height = 160.dp,
                        width = 160.dp,
                        uri = if (pictureUriString != null) Uri.parse(pictureUriString) else pictureUri,
                        uiState = uiState,
                        onChoosePicture = {
                            pickPictureLauncher.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        },
                        onClearPicture = {
                            pictureUri = null
                            pictureUriString = null
                        }
                    )

                    Spacer(modifier = modifier.height(24.dp))

                    OutlinedTextField(
                        value = nameText,
                        onValueChange = { text: String ->
                            if (text.length <= maximumNameLetters)
                                nameText = text
                        },
                        supportingText = {
                            Text(
                                text = stringResource(
                                    id = R.string.maximum_of_letters,
                                    nameText.length,
                                    maximumNameLetters,
                                )
                            )
                        },
                        label = { Text(text = "Name") },
                        shape = RoundedCornerShape(24.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        trailingIcon = {
                            if (nameText.isNotEmpty())
                                IconButton(onClick = { nameText = "" }) {
                                    Icon(Icons.Filled.Clear, "Clear text")
                                }
                        },
                        enabled = (uiState is UiStates.Show),
                        maxLines = 2,
                    )

                    Spacer(modifier = modifier.height(64.dp))

                    CancelAndSaveButtonsRow(
                        uiState = uiState,
                        onCancel = { onCancel() },
                        onSave = {
                            menuMainData.name = nameText
                            onSave(
                                menuMainData,
                                pictureUri
                            )
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditMyMenuMainScreenPreview() {
    EditMyMenuMainScreen(
        menuMainData = MenuMainData("", null),
        onCancel = {},
        onSave = { a, b -> },
        goBack = {},
    )
}