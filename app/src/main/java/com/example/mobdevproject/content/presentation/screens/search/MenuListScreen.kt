package com.example.mobdevproject.content.presentation.screens.search

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobdevproject.content.data.MenuMainData
import com.example.mobdevproject.content.presentation.composable.MenuItemCompose
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.debounce

@OptIn(ExperimentalMaterial3Api::class, FlowPreview::class)
@Composable
fun MenuListScreen(
    modifier: Modifier = Modifier,
    menuMainDataList: List<MenuMainData>,
    onClick: (menuMainData: MenuMainData) -> Unit,
    onBarCode: () -> Unit,
    onSearchMenu: (name: String) -> Unit,
) {
    var searchText by rememberSaveable { mutableStateOf("") }

    val searchTextFlow: SharedFlow<String> = MutableSharedFlow()

    LaunchedEffect(key1 = searchTextFlow) {
        searchTextFlow.debounce(2000).collect {

        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        topBar = {
            TopAppBar(
                modifier = modifier.padding(top = 12.dp),
                title = {
                    OutlinedTextField(
                        modifier = modifier.fillMaxWidth(),
                        maxLines = 1,
                        shape = RoundedCornerShape(24.dp),
                        value = searchText,
                        onValueChange = { text ->
                            searchText = text.trim()
                        },
                        trailingIcon = {
                            IconButton(onClick = { onSearchMenu(searchText) }) {
                                Icon(Icons.Filled.Search, contentDescription = "Search menu")
                            }
                        }
                    )
                },
                actions = {
                    IconButton(onClick = { onBarCode() }) {
                        Icon(Icons.Filled.DateRange, contentDescription = "qr code search")
                    }
                },
            )
        }
    ) { paddingValues: PaddingValues ->

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                if (menuMainDataList.isEmpty())
                    Box(
                        modifier = modifier.fillMaxSize()
                    ) {
                        Spacer(modifier = modifier.height(60.dp))
                        Text(
                            text = "There is no list of menu",
                            modifier = modifier.align(Alignment.Center),
                            fontSize = 16.sp,
                        )
                        Spacer(modifier = modifier.height(24.dp))
                    }
            }
            items(menuMainDataList.size) {
                Spacer(modifier = modifier.height(36.dp))
                MenuItemCompose(
                    menuMainData = menuMainDataList[it],
                    onClick = { onClick(menuMainDataList[it]) }
                )
                Spacer(modifier = modifier.height(8.dp))
            }
        }
    }
}