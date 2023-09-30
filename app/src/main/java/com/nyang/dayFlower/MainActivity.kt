package com.nyang.dayFlower

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.nyang.dayFlower.presentation.feature.flowerDetail.FlowerDetailScreen
import com.nyang.dayFlower.presentation.navigation.NavGraph
import com.nyang.dayFlower.presentation.navigation.Screens
import com.nyang.dayFlower.presentation.navigation.onNavigateNext
import com.nyang.dayFlower.ui.theme.DayFlowerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DayFlowerTheme {
                val navHostController = rememberNavController()
                val isCalendar = remember {
                    mutableStateOf(false)
                }
                Scaffold(topBar = {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp)
                        .height(56.dp)){
                        Text("하루, 꽃", modifier = Modifier.align(Alignment.Center))

                        IconButton(onClick = {
                            navHostController.onNavigateNext(if(isCalendar.value) Screens.FlowerDetail else Screens.FlowerCalendar)
                        }, modifier = Modifier.align(Alignment.CenterEnd)) {
                            Image(painter = painterResource(id = if(isCalendar.value) R.drawable.ic_calendar else R.drawable.ic_day), contentDescription = null)
                        }
                    }

                }) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        NavGraph(navController = navHostController) {
                            isCalendar.value = it == Screens.FlowerCalendar
                        }
                    }
                }

            }
        }
    }
}
