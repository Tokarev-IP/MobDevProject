package com.example.mobdevproject.content.presentation.screens.mymenu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mobdevproject.content.data.MyMenuId
import com.example.mobdevproject.content.presentation.MyMenuViewModel
import com.example.mobdevproject.content.presentation.UiStates
import java.util.UUID

@Composable
fun CheckMyMenuScreen(
    modifier: Modifier = Modifier,
    myMenuViewModel: MyMenuViewModel = hiltViewModel(),
    onCreateMenu: (myMenuId: MyMenuId) -> Unit,
    onRefresh: () -> Unit,
) {
    val uiState by myMenuViewModel.getUiStateFlow().collectAsState()

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (uiState) {
            is UiStates.Loading -> {
                CircularProgressIndicator()
            }

            is UiStates.Show -> {
                OutlinedButton(
                    onClick = {
                        onCreateMenu(MyMenuId(id = UUID.randomUUID().toString()))
                    }) {
                    Text(text = "Create a menu")
                }
            }

            is UiStates.Error -> {
                Text(text = "Ops... Something was gone wrong")

                Spacer(modifier = modifier.height(12.dp))

                OutlinedButton(onClick = { onRefresh() }) {
                    Icon(Icons.Default.Refresh, "Refresh data")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CheckMyMenuPreview() {
    CheckMyMenuScreen(onCreateMenu = { /*TODO*/ }, onRefresh = {})
}