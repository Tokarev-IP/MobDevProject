package com.example.mobdevproject.content.presentation.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.mobdevproject.content.data.MenuData
import com.example.mobdevproject.content.data.MenuMainData
import com.example.mobdevproject.content.presentation.composable.DishItemCompose

@Composable
fun MenuScreen(
    modifier: Modifier = Modifier,
    menuMainData: MenuMainData,
    menuDataList: List<MenuData>,
    corner: Dp = 24.dp,
) {
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { paddingValues: PaddingValues ->

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                Column(
                    modifier = modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    menuMainData.pictureUri?.let { uri ->
                        AsyncImage(
                            modifier = modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(bottomEnd = corner, bottomStart = corner)),
                            model = uri,
                            contentDescription = "Menu picture",
                            contentScale = ContentScale.Fit,
                        )
                    }
                    Spacer(modifier = modifier.height(16.dp))
                    Text(
                        text = menuMainData.name,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = modifier.height(16.dp))
                }
            }
            items(menuDataList.size) {
                DishItemCompose(menuData = menuDataList[it], onClick = {})
            }
        }
    }
}