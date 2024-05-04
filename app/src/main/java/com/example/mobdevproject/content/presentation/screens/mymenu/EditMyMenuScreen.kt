package com.example.mobdevproject.content.presentation.screens.mymenu

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.mobdevproject.content.data.MenuData
import com.example.mobdevproject.content.data.MenuMainData
import com.example.mobdevproject.content.presentation.MyMenuViewModel
import com.example.mobdevproject.content.presentation.UiStates
import com.example.mobdevproject.content.presentation.composable.DishItemCompose
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditMyMenuScreen(
    modifier: Modifier = Modifier,
    uiState: UiStates,
    menuMainData: MenuMainData,
    menuDataList: List<MenuData>,
    onEditMenuMain: (menuMainData: MenuMainData) -> Unit,
    onAddNewDish: (menuData: MenuData) -> Unit,
    onEditDish: (menuData: MenuData) -> Unit,
    onQrCodeShare: (menuId: String) -> Unit,
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp),
        topBar = {
            TopAppBar(
                modifier = modifier.padding(top = 12.dp),
                title = {
                    Text(text = menuMainData.name)
                },
                navigationIcon = {

                },
                actions = {
                    IconButton(onClick = { onQrCodeShare(menuMainData.id) }) {
                        Icon(Icons.Filled.Share, contentDescription = "Share qr code")
                    }
                },
            )
        }
    ) { paddingValues: PaddingValues ->

        if (uiState is UiStates.Loading)
            LinearProgressIndicator(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(paddingValues)
            )

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                MenuMainDataCompose(
                    menuMainData = menuMainData,
                    onEditMenuMain = { onEditMenuMain(menuMainData) }
                )
                Spacer(modifier = modifier.height(24.dp))
            }

            items(menuDataList.size) {
                Box {
                    DishItemCompose(
                        menuData = menuDataList[it],
                        onClick = {},
                    )
                    IconButton(
                        onClick = { onEditDish(menuDataList[it]) },
                        modifier = modifier.align(Alignment.CenterEnd),
                    ) {
                        Icon(Icons.Filled.Edit, contentDescription = "Edit dish")
                    }
                }
                Spacer(modifier = modifier.height(8.dp))
            }

            item {
                AddNewDishButton(
                    onAddNewDish = { menuData: MenuData ->
                        onAddNewDish(menuData)
                    }
                )
            }
        }
    }
}

@Composable
fun MenuMainDataCompose(
    modifier: Modifier = Modifier,
    menuMainData: MenuMainData,
    onEditMenuMain: () -> Unit,
    corner: Dp = 24.dp,
    height: Dp = 140.dp,
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (menuMainData.pictureUri != null)
                AsyncImage(
                    model = Uri.parse(menuMainData.pictureUri),
                    contentDescription = "Picture of a restaurant",
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .clip(RoundedCornerShape(bottomEnd = corner, bottomStart = corner))
                        .height(height),
                )
            else {
                Spacer(modifier = modifier.height(48.dp))
                Text(text = "No picture")
                Spacer(modifier = modifier.height(48.dp))
            }
        }

        IconButton(
            modifier = modifier
                .align(Alignment.TopEnd)
                .padding(4.dp),
            onClick = { onEditMenuMain() })
        {
            Icon(Icons.Default.Edit, contentDescription = "Edit menu main data")
        }
    }
}

@Composable
fun AddNewDishButton(
    modifier: Modifier = Modifier,
    onAddNewDish: (menuData: MenuData) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = modifier.height(12.dp))
        TextButton(onClick = {
            onAddNewDish(
                MenuData(
                    name = "Empty name",
                    id = UUID.randomUUID().toString(),
                )
            )
        })
        {
            Text(text = "Add a new dish")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditMyMenuScreenPreview() {
    EditMyMenuScreen(
        menuMainData = MenuMainData("Sample", null),
        menuDataList = listOf(),
        onEditMenuMain = {},
        onAddNewDish = {},
        onEditDish = {},
        onQrCodeShare = {},
        uiState = UiStates.Show,
    )
}