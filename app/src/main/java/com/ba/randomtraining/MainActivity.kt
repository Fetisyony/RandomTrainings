package com.ba.randomtraining

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ba.randomtraining.ui.main.HomeScreen
import com.ba.randomtraining.ui.theme.RandomTrainingTheme
import com.ba.randomtraining.data.utils.RetrofitTenorInstance

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            RandomTrainingTheme {
                HomeScreen()
            }
        }
    }
}
