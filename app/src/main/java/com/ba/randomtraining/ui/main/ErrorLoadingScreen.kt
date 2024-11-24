package com.ba.randomtraining.ui.main

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ba.randomtraining.R
import com.ba.randomtraining.ui.components.RetryButton
import com.ba.randomtraining.ui.theme.ErrorScreenColor

@Composable
fun ErrorLoadingScreen(onTryAgain: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.round_cloud_off_24),
            contentDescription = "Error loading")
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "Error happened while loading.\nCheck your internet connection",
            textAlign = TextAlign.Center,
            color = ErrorScreenColor
        )
        Spacer(modifier = Modifier.height(21.dp))
        RetryButton(onTryAgain)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ErrorLoadingScreenPreview() {
    ErrorLoadingScreen {
        Log.d("Debug", "Button clicked")
    }
}
