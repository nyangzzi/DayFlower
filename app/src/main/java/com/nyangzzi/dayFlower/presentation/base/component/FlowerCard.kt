package com.nyangzzi.dayFlower.presentation.base.component

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.nyangzzi.dayFlower.R
import com.nyangzzi.dayFlower.data.network.ResultWrapper
import com.nyangzzi.dayFlower.domain.model.common.FlowerDetail
import com.nyangzzi.dayFlower.presentation.base.util.Utils
import com.nyangzzi.dayFlower.presentation.base.util.loadingShimmerEffect
import com.nyangzzi.dayFlower.presentation.base.util.noRippleClickable
import com.nyangzzi.dayFlower.presentation.feature.flowerDetail.FlowerDetailScreen
import com.nyangzzi.dayFlower.ui.theme.Gray1
import com.nyangzzi.dayFlower.ui.theme.Gray11
import com.nyangzzi.dayFlower.ui.theme.Gray5
import com.nyangzzi.dayFlower.ui.theme.Gray6
import com.nyangzzi.dayFlower.ui.theme.Gray9
import com.nyangzzi.dayFlower.ui.theme.PrimaryAlpha50
import com.nyangzzi.dayFlower.ui.theme.SystemRed
import com.nyangzzi.dayFlower.ui.theme.White

@Composable
fun FlowerCard(
    flower: ResultWrapper<FlowerDetail>,
    onRefresh: () -> Unit = {},
    isShowDetail: Boolean = false,
    setShowDetail: (Boolean, Int) -> Unit = { _, _ -> },
    savedFlower: List<FlowerDetail> = emptyList(),
    onSave: (Boolean, FlowerDetail) -> Unit = { _, _ -> },
    cardSize: FlowerCardSize = FlowerCardSize.LARGE
) {

    when (flower) {
        is ResultWrapper.Loading -> {
            LoadingContent(cardSize = cardSize)
        }

        is ResultWrapper.Success -> {
            if (flower.data.dataNo == null) {
                ErrorContent(msg = "오류가 발생했습니다.", cardSize = cardSize, onRefresh = onRefresh)
            } else {
                SuccessContent(
                    flower.data,
                    cardSize = cardSize,
                    isSaved = savedFlower.any { it.dataNo == flower.data.dataNo },
                    onSave = onSave,
                    showDetail = { setShowDetail(true, flower.data.dataNo) },
                )
                if (isShowDetail) {
                    FlowerDetailScreen(
                        dataNo = flower.data.dataNo,
                        onDismiss = { setShowDetail(false, -1) })
                }
            }
        }

        is ResultWrapper.Error -> {
            ErrorContent(msg = flower.errorMessage, cardSize = cardSize, onRefresh = onRefresh)
        }

        ResultWrapper.None -> {}
    }

}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalPagerApi::class)
@Composable
private fun SuccessContent(
    flower: FlowerDetail,
    cardSize: FlowerCardSize = FlowerCardSize.LARGE,
    showDetail: () -> Unit = {},
    isSaved: Boolean = false,
    onSave: (Boolean, FlowerDetail) -> Unit = { _, _ -> }
) {

    Column(
        modifier = Modifier
            .background(color = White, shape = RoundedCornerShape(12.dp))
            .border(width = 2.dp, color = Gray1, shape = RoundedCornerShape(12.dp))
            .clip(shape = RoundedCornerShape(12.dp))
            .clickable { showDetail() }
    ) {

        val imgList = listOfNotNull(
            flower.imgUrl1,
            flower.imgUrl2,
            flower.imgUrl3
        )

        val imgState = rememberPagerState()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .height(cardSize.imgHeight)
        ) {
            HorizontalPager(
                count = imgList.size,
                state = imgState,
                modifier = Modifier.clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(Utils.setImageUrl(imgList[it]))
                        .crossfade(true)
                        .build(),
                    contentDescription = flower.fileName1,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                    placeholder = painterResource(id = R.drawable.ic_loading_image)
                )
            }

            HorizontalPagerIndicator(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(20.dp),
                pagerState = imgState,
                activeColor = MaterialTheme.colorScheme.primary,
                inactiveColor = PrimaryAlpha50
            )

            Image(
                painter = painterResource(
                    id = if (isSaved) R.drawable.ic_empty_heart_outline
                    else R.drawable.ic_empty_heart
                ),
                modifier = Modifier
                    .padding(12.dp)
                    .size(cardSize.iconSize)
                    .align(Alignment.TopEnd)
                    .noRippleClickable {
                        onSave(isSaved, flower)
                    },
                colorFilter = if (isSaved) ColorFilter.tint(
                    SystemRed
                ) else null,
                contentDescription = null
            )
        }

        Column(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = cardSize.contentHorizontal),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.horizontalScroll(rememberScrollState())
            ) {
                flower.flowLang?.split(", ")?.map {
                    Badge(
                        text = it,
                        style = when (cardSize) {
                            FlowerCardSize.LARGE -> MaterialTheme.typography.labelMedium
                            FlowerCardSize.SMALL -> MaterialTheme.typography.labelSmall
                        }
                    )
                }
            }

            Row(
                modifier = Modifier.padding(start = 2.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                flower.flowNm?.let {
                    Text(
                        text = it,
                        style = when (cardSize) {
                            FlowerCardSize.LARGE -> MaterialTheme.typography.titleMedium
                            FlowerCardSize.SMALL -> MaterialTheme.typography.bodyMedium
                        },
                        color = Gray11,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                flower.fEngNm?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Gray6,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            flower.fContent?.let {
                Text(
                    text = it,
                    color = Gray6,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

        }
    }

}


@Composable
private fun LoadingContent(cardSize: FlowerCardSize = FlowerCardSize.LARGE) {

    val brush = loadingShimmerEffect()

    Column(
        modifier = Modifier
            .background(color = White, shape = RoundedCornerShape(12.dp))
            .border(width = 2.dp, color = Gray1, shape = RoundedCornerShape(12.dp)),
    ) {

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(cardSize.imgHeight)
                .background(brush, shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(vertical = 12.dp, horizontal = cardSize.contentHorizontal)
        ) {

            Spacer(
                modifier = Modifier
                    .height(cardSize.textHeight)
                    .width(cardSize.textWidth * 2)
                    .background(brush, shape = RoundedCornerShape(12.dp))
            )

            Spacer(
                modifier = Modifier
                    .height(cardSize.textHeight)
                    .width(cardSize.textWidth)
                    .background(brush, shape = RoundedCornerShape(12.dp))
            )

            Spacer(
                modifier = Modifier
                    .height(cardSize.textHeight)
                    .fillMaxWidth()
                    .background(brush, shape = RoundedCornerShape(12.dp))
            )
        }
    }
}

@Composable
private fun ErrorContent(
    msg: String?,
    cardSize: FlowerCardSize = FlowerCardSize.LARGE,
    onRefresh: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .background(color = White, shape = RoundedCornerShape(12.dp))
            .border(width = 2.dp, color = Gray1, shape = RoundedCornerShape(12.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(cardSize.imgHeight)
                .background(Gray1, shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_empty_img),
                contentDescription = "empty_img"
            )
        }

        Column(
            modifier = Modifier.height(130.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(onClick = { onRefresh() }) {
                Icon(imageVector = Icons.Rounded.Refresh, contentDescription = null, tint = Gray5)
            }
            Text(msg ?: "조회에 실패했습니다. 다시 시도해주세요", color = Gray9)
        }
    }
}


@Preview
@Composable
fun PreviewSuccessFlowerCard() {
    SuccessContent(
        flower = FlowerDetail(
            flowLang = "꽃말1, 꽃말2",
            flowNm = "꽃명",
            fEngNm = "영문",
            fContent = "설명입니다."
        ),
        isSaved = false
    )
}

@Preview
@Composable
fun PreviewLoadingFlowerCard() {
    LoadingContent()
}

@Preview
@Composable
fun PreviewErrorFlowerCard() {
    ErrorContent(msg = "오류 발생")
}


enum class FlowerCardSize(
    val imgHeight: Dp,
    val textHeight: Dp,
    val textWidth: Dp,
    val contentHorizontal: Dp,
    val iconSize: Dp,
) {
    LARGE(
        imgHeight = 220.dp,
        textHeight = 26.dp,
        textWidth = 100.dp,
        contentHorizontal = 18.dp,
        iconSize = 36.dp
    ),
    SMALL(
        imgHeight = 132.dp,
        textHeight = 16.dp,
        textWidth = 50.dp,
        contentHorizontal = 12.dp,
        iconSize = 20.dp
    )
}
