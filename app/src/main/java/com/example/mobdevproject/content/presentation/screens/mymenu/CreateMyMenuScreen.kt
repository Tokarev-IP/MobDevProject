package com.example.mobdevproject.content.presentation.screens.mymenu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mobdevproject.content.data.MenuData
import com.example.mobdevproject.content.data.MenuMainData

@Composable
fun CreateMyMenuScreen(
    modifier: Modifier = Modifier,
    menuMainData: MenuMainData,
    menuDataList: List<MenuData>
) {
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) {padding: PaddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
        ){
            item {
                Column {
                    AsyncImage(
                        modifier = modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)),
                        model = menuMainData.logo,
                        contentDescription = "Logo picture",
                    )
                }
            }

            items(menuDataList.size) {
                Text(text = menuDataList[it].name)
            }
        }
    }
}