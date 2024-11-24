package com.ba.randomtraining.ui.components

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.compose.SubcomposeAsyncImage
import coil3.gif.AnimatedImageDecoder
import coil3.gif.GifDecoder
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.ba.randomtraining.R
import com.ba.randomtraining.data.model.JasonSearchResultItem
import com.ba.randomtraining.data.repository.FetchError
import com.ba.randomtraining.ui.theme.CustomLightGray
import com.ba.randomtraining.viewmodel.ErrorStatus


fun isHighTimeToLoadNew(lastVisibleItemIndex: Int?, isLoading: Boolean, itemsSize: Int, errorStatus: ErrorStatus): Boolean {
    val loadNewWhenLeftUntilBottom = 5

    return lastVisibleItemIndex != null &&
            !isLoading &&
            lastVisibleItemIndex >= itemsSize - loadNewWhenLeftUntilBottom &&
            errorStatus.fetchError == FetchError.Ok
}

@Suppress("NonSkippableComposable")
@Composable
fun JasonGrid(
    jasonItems: List<JasonSearchResultItem>,
    isLoading: Boolean,
    errorStatus: ErrorStatus,
    onLoadMore: () -> Unit
) {
    val listState = rememberSaveable(saver = LazyListState.Saver) {
        LazyListState()
    }

    LaunchedEffect(listState, isLoading) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .collect { visibleItems ->
                val lastVisibleItemIndex = visibleItems.lastOrNull()?.index
                if (isHighTimeToLoadNew(lastVisibleItemIndex, isLoading, jasonItems.size, errorStatus)) {
                    onLoadMore()
                }
            }
    }

    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 45.dp)
            .padding(horizontal = 25.dp),
    ) {
        items(jasonItems) { jasonItem ->
            ElevatedCard(
                elevation = CardDefaults.elevatedCardElevation(10.dp),
                colors = CardColors(
                    containerColor = CustomLightGray,
                    contentColor = Color.White,
                    disabledContainerColor = Color.White,
                    disabledContentColor = Color.White,
                )
            ) {
                JasonBox(jasonItem)
            }
        }

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(top = 0.dp, bottom = 30.dp),
                contentAlignment = Alignment.Center
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.Black,
                    )
                }
                if (errorStatus.fetchError != FetchError.Ok && jasonItems.isNotEmpty())
                    RetryButton(onLoadMore)
            }
        }
    }

}

@Suppress("NonSkippableComposable")
@Composable
fun JasonBox(jasonItem: JasonSearchResultItem) {
    val context = LocalContext.current

    val decoderFactory = if (SDK_INT >= 28) AnimatedImageDecoder.Factory() else GifDecoder.Factory()
    val imageLoader = remember {
        ImageLoader.Builder(context)
            .components {
                add(decoderFactory)
            }
            .build()
    }
    val model = remember(jasonItem.mediaFormats.gif.url) {
        ImageRequest.Builder(context)
            .data(jasonItem.mediaFormats.gif.url)
            .crossfade(true)
            .build()
    }

    SubcomposeAsyncImage(
        model = model,
        imageLoader = imageLoader,
        contentDescription = "Jason",
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(jasonItem.mediaFormats.gif.getRatio()),
        contentScale = ContentScale.Crop,
        error = {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.round_cloud_off_24),
                    contentDescription = "Error loading",
                    modifier = Modifier
                        .size(80.dp)
                )
            }
        },
        loading = {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .size(15.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularProgressIndicator(
                    color = Color.Black,
                    strokeWidth = 5.dp,
                )
            }
        }
    )
}
