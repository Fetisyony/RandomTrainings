package com.ba.randomtraining.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ba.randomtraining.ui.theme.RandomTrainingTheme

@Composable
fun HomeScreen() {
    RandomTrainingTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                Text(
                    text = "Android",
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun HomeScreePreview() {
    HomeScreen()
}
