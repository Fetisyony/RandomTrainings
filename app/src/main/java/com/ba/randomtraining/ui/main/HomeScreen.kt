package com.ba.randomtraining.ui.main

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.gif.AnimatedImageDecoder
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.ba.randomtraining.R
import com.ba.randomtraining.data.model.ExerciseResponse
import com.ba.randomtraining.data.repository.ExerciseRepository
import com.ba.randomtraining.viewmodel.MainViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun HomeScreen(repository: ExerciseRepository) {
    val viewModel = remember { MainViewModel(repository) }
    val exercises by viewModel.exercises.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
        onRefresh = { viewModel.fetchExercises(refresh = true) }
    ) {
        ExercisesGrid(
            exercises=exercises,
            isLoading,
            onLoadMore = { viewModel.fetchExercises() })
    }
}

@Suppress("NonSkippableComposable")
@Composable
fun ExercisesGrid(
    exercises: List<ExerciseResponse>,
    isLoading: Boolean,
    onLoadMore: () -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 45.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(exercises) { exercise ->
                Box(
                    modifier = Modifier
                        .border(2.dp, Color.Gray, RoundedCornerShape(15.dp))
                ) {
                    AsyncImage(

                        model = ImageRequest.Builder(LocalContext.current)
                            .data(exercise.gifUrl)
                            .crossfade(true)
                            .decoderFactory(AnimatedImageDecoder.Factory())
                            .build(),
                        placeholder = painterResource(R.drawable.loading),
                        contentDescription = exercise.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(CircleShape)
                            .fillMaxWidth()
                            .aspectRatio(1f)
                    )
                }
            }

            if (isLoading) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = Color.Black
                        )
                    }
                }
            }

    }
    LaunchedEffect(exercises) {
        onLoadMore()
    }
}

@Preview(showSystemUi = true)
@Composable
fun HomeScreePreview() {
//    HomeScreen()
}
