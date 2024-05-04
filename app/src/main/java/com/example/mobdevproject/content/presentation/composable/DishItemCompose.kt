package com.example.mobdevproject.content.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mobdevproject.content.data.MenuData

@Composable
fun DishItemCompose(
    modifier: Modifier = Modifier,
    menuData: MenuData,
    corner: Dp = 40.dp,
    pictureSize: Dp = 60.dp,
    onClick: () -> Unit,
) {
    OutlinedCard(
        modifier = modifier
            .clickable {
                onClick()
            }
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(
                    color = Color.Unspecified,
                    shape = RoundedCornerShape(
                        topStart = corner,
                        topEnd = corner,
                        bottomEnd = corner,
                        bottomStart = corner,
                    )
                )
                .clickable {
                    onClick()
                }
        ) {
            if (menuData.pictureUri != null) {
                AsyncImage(
                    model = menuData.pictureUri,
                    contentDescription = "Dish picture",
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .height(pictureSize)
                        .width(pictureSize)
                )
                Spacer(modifier = modifier.width(12.dp))
            } else
                Spacer(modifier = modifier.width(12.dp))
            Column {
                Text(text = menuData.name)
                Spacer(modifier = modifier.height(8.dp))
                Text(text = menuData.price.toString())
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DishItemComposePreview() {
    DishItemCompose(
        menuData = MenuData("123456789", "Eda vkusna", 15.0, null),
        onClick = {},
    )
}