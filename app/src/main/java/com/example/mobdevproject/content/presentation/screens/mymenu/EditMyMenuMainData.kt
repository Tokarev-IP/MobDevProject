package com.example.mobdevproject.content.presentation.screens.mymenu

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mobdevproject.R
import com.example.mobdevproject.content.presentation.UiStates

@Composable
fun EditMyMenuMainData(
    modifier: Modifier = Modifier,
    maximumOfLetters: Int = 60,
    uiState: UiStates,
    onChoosePicture: () -> Unit,
    onClearPicture: () -> Unit,
    onSave:() -> Unit,
    onCancel:() -> Unit,
) {
    var nameText by rememberSaveable { mutableStateOf("") }

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { paddingValues: PaddingValues ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            ChooseMainPicture(
                height = 160.dp,
                width = 160.dp,
                uri = null,
                onChoosePicture = { onChoosePicture() },
                onClearPicture = { onClearPicture() }
            )

            Spacer(modifier = modifier.height(24.dp))

            OutlinedTextField(
                value = nameText,
                onValueChange = {
                    nameText = it
                },
                supportingText = {
                    Text(
                        text = stringResource(
                            id = R.string.maximum_of_letters,
                            nameText.length,
                            maximumOfLetters,
                        )
                    )
                },
                label = { Text(text = "Name") },
                shape = RoundedCornerShape(24.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                trailingIcon = {
                    if (nameText.isNotEmpty())
                        IconButton(onClick = { nameText = "" }) {
                            Icon(Icons.Default.Clear, "Clear text")
                        }
                },
                enabled = (uiState is UiStates.Show),
            )

            Spacer(modifier = modifier.height(64.dp))

            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                TextButton(onClick = { /*TODO*/ }) {
                    Text(text = "Cancel")
                }

                OutlinedButton(onClick = { /*TODO*/ }) {
                    Text(text = "Save")
                }
            }
        }
    }
}

@Composable
fun ChooseMainPicture(
    modifier: Modifier = Modifier,
    height: Dp,
    width: Dp,
    uri: Uri?,
    onChoosePicture: () -> Unit,
    onClearPicture: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        if (uri != null)
            Box(
                modifier = modifier
                    .height(height)
                    .width(width),
            ) {
                AsyncImage(
                    modifier = modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .align(Alignment.Center),
                    model = uri,
                    contentDescription = "Picture of a restaurant",
                    contentScale = ContentScale.Crop,
                )

                IconButton(
                    onClick = { onClearPicture() },
                    modifier = modifier.align(Alignment.TopEnd)
                ) {
                    Icon(Icons.Default.Clear, contentDescription = "Clear the picture")
                }
            }
        else {
            Text(text = "Please, choose a picture")

            Spacer(modifier = modifier.height(12.dp))

            OutlinedButton(onClick = { onChoosePicture() }) {
                Icon(Icons.Default.Add, contentDescription = "Add the picture")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditMenuMainDataPreview() {
    EditMyMenuMainData(
        uiState = UiStates.Show,
        onChoosePicture = {},
        onClearPicture = {},
        onCancel = {},
        onSave = {},
    )
}