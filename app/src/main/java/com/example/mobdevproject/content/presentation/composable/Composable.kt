package com.example.mobdevproject.content.presentation.composable

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mobdevproject.content.presentation.UiStates

@Composable
fun CancelAndSaveButtonsRow(
    modifier: Modifier = Modifier,
    uiState: UiStates,
    onCancel:() -> Unit,
    onSave:() -> Unit,
){
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        TextButton(
            onClick = { onCancel() },
            enabled = (uiState is UiStates.Show),
        ) {
            Text(text = "Cancel")
        }

        OutlinedButton(
            onClick = { onSave() },
            enabled = (uiState is UiStates.Show),
        ) {
            Text(text = "Save")
        }
    }
}

@Composable
fun ChoosePicture(
    modifier: Modifier = Modifier,
    height: Dp,
    width: Dp,
    uri: Uri?,
    uiState: UiStates,
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
                    modifier = modifier.align(Alignment.TopEnd),
                    enabled = (uiState is UiStates.Show),
                ) {
                    Icon(Icons.Filled.Clear, contentDescription = "Clear the picture")
                }
            }
        else {
            Text(text = "Please, choose a picture")

            Spacer(modifier = modifier.height(12.dp))

            OutlinedButton(
                onClick = { onChoosePicture() },
                enabled = (uiState is UiStates.Show),
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add the picture")
            }
        }
    }
}

@Composable
fun StartDestinationCompose(
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = modifier.align(Alignment.Center))
    }
}