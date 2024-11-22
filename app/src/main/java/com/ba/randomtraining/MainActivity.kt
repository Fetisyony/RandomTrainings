package com.ba.randomtraining

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.ba.randomtraining.data.api.ApiService
import com.ba.randomtraining.ui.main.HomeScreen
import com.ba.randomtraining.ui.theme.RandomTrainingTheme
import com.ba.randomtraining.utils.RetrofitInstance
import com.ba.randomtraining.viewmodel.MainViewModel
import kotlinx.coroutines.launch

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
