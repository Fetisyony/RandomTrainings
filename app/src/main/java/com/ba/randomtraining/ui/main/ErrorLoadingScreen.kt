package com.ba.randomtraining.ui.main

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "Error happened while loading.\nCheck your internet connection",
            textAlign = TextAlign.Center,
            color = ErrorScreenColor
        )
        Spacer(modifier = Modifier.height(15.dp))
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
