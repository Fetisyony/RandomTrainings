package com.ba.randomtraining.ui.main

import android.content.Context
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ba.randomtraining.R
import com.ba.randomtraining.data.repository.FetchError
import com.ba.randomtraining.ui.components.CustomAlertDialog
import com.ba.randomtraining.ui.components.JasonGrid
import com.ba.randomtraining.viewmodel.MainViewModel


fun FetchError.getErrorMessage(context: Context): String = when (this) {
    FetchError.Ok -> context.getString(R.string.fetch_error_message_ok)
    FetchError.NetworkError -> context.getString(R.string.fetch_error_message_network)
    FetchError.NoDataLeftError -> context.getString(R.string.fetch_error_message_no_data_left)
    is FetchError.UnexpectedError -> context.getString(R.string.fetch_error_message_unexpected)
}

// turning on experimental due to PullToRefreshBox
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val context = LocalContext.current

    val viewModel: MainViewModel = viewModel()
    val jasonItems = viewModel.jasonItems
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
        JasonGrid(
            jasonItems=jasonItems,
            isLoading,
            errorStatus,
            onLoadMore = {
                viewModel.fetchJason()
            }
        )

        if (errorStatus.fetchError != FetchError.Ok) {
            if (jasonItems.isEmpty()) {
                ErrorLoadingScreen(
                    errorStatus.fetchError.getErrorMessage(context)
                ) {
                    viewModel.fetchJason(refresh = true)
                }
            } else if (!errorStatus.seen) {
                CustomAlertDialog(
                    message = errorStatus.fetchError.getErrorMessage(context),
                    onConfirm = { viewModel.markErrorAsSeen() },
                )
            }
        }
    }
}
