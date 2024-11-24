package com.ba.randomtraining.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ba.randomtraining.R

@Composable
fun ErrorButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Gray
        ),
        modifier = Modifier
            .width(125.dp)
            .height(40.dp),
        border = BorderStroke(1.dp, Color.Gray),
        contentPadding = PaddingValues(0.dp),
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun RetryButton(onClick: () -> Unit) {
    ErrorButton(
        text = stringResource(R.string.retry)
    ) {
        onClick()
    }
}
