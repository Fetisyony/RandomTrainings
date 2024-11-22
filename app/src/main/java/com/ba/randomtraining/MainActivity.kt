package com.ba.randomtraining

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ba.randomtraining.ui.main.HomeScreen
import com.ba.randomtraining.ui.theme.RandomTrainingTheme
import com.ba.randomtraining.utils.RetrofitInstance

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val repository = RetrofitInstance.exerciseRepository

        setContent {
            RandomTrainingTheme {
                HomeScreen(repository)
            }
        }
    }
}
