package com.example.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.navigation.NavItem
import com.example.movieapp.navigation.NavItem.Companion.title
import com.example.movieapp.ui.theme.MovieAppTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var navHostController = rememberNavController()
            var title by remember { mutableStateOf("")}
            var displayTopBar by remember { mutableStateOf(true) }
            var displayBottomBar by remember { mutableStateOf(true) }
            DisposableEffect(key1 = Unit) {
                val destinationListener =
                    NavController.OnDestinationChangedListener { _, destination, _ ->
                        displayBottomBar = destination.route != NavItem.Login.route
                        displayTopBar = destination.route != NavItem.Login.route
                        title = destination.route?.title().orEmpty()
                    }
                navHostController.addOnDestinationChangedListener(destinationListener)
                onDispose { navHostController.removeOnDestinationChangedListener(destinationListener) }
            }
            MovieAppTheme {
                Scaffold(
                    topBar = {
                        if (displayTopBar) {
                            TopAppBar(
                                title = {
                                    Text(text = title)
                                },
                                navigationIcon = {
                                    IconButton(onClick = { navHostController.navigateUp() }) {
                                        Icon(Icons.Filled.ArrowBack, null)
                                    }
                                },
                                modifier = Modifier
                                    .background(color = Color.White)
                            )
                        }
                    },
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (displayBottomBar) {
                            BottomNav(navController = navHostController)
                        }
                    }
                ){innerPadding ->
                    NavigationGraph(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        navController = navHostController
                    )
                }
            }
        }
    }
}
