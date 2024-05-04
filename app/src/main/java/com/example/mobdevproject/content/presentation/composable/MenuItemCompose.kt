package com.example.mobdevproject.content.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.mobdevproject.content.data.MenuMainData

@Composable
fun MenuItemCompose(
    modifier: Modifier = Modifier,
    menuMainData: MenuMainData,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
    ) {
        menuMainData.pictureUri?.let { uri ->
            AsyncImage(
                modifier = modifier
                    .height(60.dp)
                    .width(60.dp)
                    .clip(CircleShape),
                model = uri,
                contentDescription = "Menu picture",
                contentScale = ContentScale.Fit,
            )
        }

        Spacer(modifier = modifier.width(20.dp))
        Text(
            text = menuMainData.name,
            fontSize = 16.sp,
        )
    }
}