package com.nyangzzi.dayFlower

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.nyangzzi.dayFlower.presentation.navigation.NavGraph
import com.nyangzzi.dayFlower.ui.theme.DayFlowerTheme
import com.nyangzzi.dayFlower.ui.theme.White
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            DayFlowerTheme {
                val navHostController = rememberNavController()
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = White
                ) {
                    NavGraph(navController = navHostController)
                }

            }
        }
    }
}