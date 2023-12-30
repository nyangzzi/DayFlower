package com.nyangzzi.dayFlower.presentation.feature.mainFlower.flowerMonth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nyangzzi.dayFlower.domain.model.common.FlowerDetail
import com.nyangzzi.dayFlower.presentation.base.Utils
import com.nyangzzi.dayFlower.presentation.base.component.BaseFixedGrid
import com.nyangzzi.dayFlower.presentation.feature.mainFlower.MainFlowerEvent

@Composable
fun FlowerCalendar(flowerMonth: List<FlowerDetail>, onEvent: (MainFlowerEvent) -> Unit) {
    BaseFixedGrid(
        gridList = flowerMonth.sortedWith(compareBy<FlowerDetail> { it.fMonth }.thenBy { it.fDay }),
        splitSize = 7
    ) { flowerDetail ->
        (flowerDetail as FlowerDetail)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
                .clickable {
                    onEvent(
                        MainFlowerEvent.IsChangeView(
                            isCalendar = false,
                            month = flowerDetail.fMonth,
                            day = flowerDetail.fDay
                        )
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            FlowerCalendarImage(imageUrl = flowerDetail.imgUrl3 ?: "")
            Text(text = flowerDetail.fDay.toString(), color = Color.White)
        }

    }
}

@Composable
private fun FlowerCalendarImage(imageUrl: String) {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(4.dp)) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(Utils.setImageUrl(imageUrl))
                .crossfade(true)
                .build(),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}