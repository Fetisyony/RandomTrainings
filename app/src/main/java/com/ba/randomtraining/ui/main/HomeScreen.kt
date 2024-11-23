package com.ba.randomtraining.ui.main

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.ba.randomtraining.data.repository.TenorRepository
import com.ba.randomtraining.ui.components.JasonsGrid
import com.ba.randomtraining.viewmodel.MainViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ba.randomtraining.viewmodel.FetchError
import com.ba.randomtraining.viewmodel.MainViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(tenorRepository: TenorRepository) {
    val viewModel: MainViewModel = viewModel(
        factory = MainViewModelFactory(tenorRepository)
    )
    val jasonItems by viewModel.jasonItems.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val errorStatus by viewModel.errorStatus.collectAsState()

    val refreshState = rememberPullToRefreshState()
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        state = refreshState,
        onRefresh = {
            viewModel.fetchJason(refresh = true)
        }
    ) {
        JasonsGrid(
            jasonItems=jasonItems,
            isLoading,
            errorStatus,
            onLoadMore = {
                viewModel.fetchJason()
            }
        )

        if (errorStatus.fetchError != FetchError.Ok) {
            if (jasonItems.isEmpty()) {
                ErrorLoadingScreen {
                    viewModel.fetchJason(refresh = true)
                }
            } else if (errorStatus.updated) {
                AlertDialog(
                    onDismissRequest = { errorStatus.updated = false },
                    title = { Text("Error") },
                    text = { Text(errorStatus.fetchError.getErrorMessage()) },
                    confirmButton = {
                        Button(onClick = { errorStatus.updated = false }) {
                            Text("OK")
                        }
                    }
                )
            }
        }
    }
}
