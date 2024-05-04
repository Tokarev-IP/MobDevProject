package com.example.mobdevproject.content.presentation.screens.mymenu

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.mobdevproject.content.data.MenuData
import com.example.mobdevproject.content.presentation.MyMenuViewModel
import com.example.mobdevproject.content.presentation.UiStates
import com.example.mobdevproject.content.presentation.composable.CancelAndSaveButtonsRow
import com.example.mobdevproject.content.presentation.composable.ChoosePicture

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditMyMenuItemScreen(
    modifier: Modifier = Modifier,
    maximumLettersOfName: Int = 30,
    maximumLettersOfPrice: Int = 7,
    menuData: MenuData,
    onCancel: () -> Unit,
    onSave: (menuData: MenuData, newPictureUri: Uri?) -> Unit,
    goBack: () -> Unit,
    uiState: UiStates,
) {
    var pictureUriString by rememberSaveable { mutableStateOf(menuData.pictureUri) }
    var dishNameText by rememberSaveable { mutableStateOf(menuData.name) }
    var dishPriceText by rememberSaveable { mutableStateOf(menuData.price.toString()) }
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
                    Text(text = "Edit dish")
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
                LazyColumn(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    item {
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
                            value = dishNameText,
                            onValueChange = { text: String ->
                                if (text.length < maximumLettersOfName)
                                    dishNameText = text
                            },
                            supportingText = {
                                Text(
                                    text = stringResource(
                                        id = R.string.maximum_of_letters,
                                        dishNameText.length,
                                        maximumLettersOfName,
                                    )
                                )
                            },
                            label = { Text(text = "Name") },
                            shape = RoundedCornerShape(24.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            trailingIcon = {
                                if (dishNameText.isNotEmpty())
                                    IconButton(onClick = { dishNameText = "" }) {
                                        Icon(Icons.Default.Clear, "Clear text")
                                    }
                            },
                        )
                        Spacer(modifier = modifier.height(24.dp))

                        OutlinedTextField(
                            value = dishPriceText,
                            onValueChange = {
                                dishPriceText = it.trim()
                            },
                            label = { Text(text = "Price") },
                            shape = RoundedCornerShape(24.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            trailingIcon = {
                                if (dishPriceText.isNotEmpty())
                                    IconButton(onClick = { dishPriceText = "" }) {
                                        Icon(Icons.Default.Clear, "Clear text")
                                    }
                            },
                        )
                        Spacer(modifier = modifier.height(24.dp))

                        CancelAndSaveButtonsRow(
                            uiState = uiState,
                            onCancel = { onCancel() },
                            onSave = {
                                onSave(
                                    menuData.apply {
                                        id = menuData.id
                                        name = dishNameText
                                        price =
                                            if (dishPriceText != "") dishPriceText.toDouble() else 0.0
                                    },
                                    pictureUri,
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditMyMenuItemScreenPreview() {
    EditMyMenuItemScreen(
        menuData = MenuData("", "", 0.0, null),
        onCancel = { /*TODO*/ },
        onSave = { a, b -> },
        goBack = {},
        uiState = UiStates.Show
    )
}