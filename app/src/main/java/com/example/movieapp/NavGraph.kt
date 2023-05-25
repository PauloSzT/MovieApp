package com.example.movieapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.navigation.NavItem
import com.example.movieapp.ui.favorites.FavoritesScreen
import com.example.movieapp.ui.login.LoginScreen
import com.example.movieapp.ui.search.SearchScreen

@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = NavItem.Login.route,
        modifier = modifier.fillMaxSize()
    ){
        composable (route = NavItem.Login.route) {
            LoginScreen()
        }
        composable(route = NavItem.Login.route){
            FavoritesScreen()
        }
        composable(route = NavItem.Login.route){
            SearchScreen()
        }
    }
}

@Composable
fun BottomNav(navController: NavHostController){
    val items = listOf(NavItem.Favorites, NavItem.Search)
    NavigationBar{
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            NavigationBarItem(
                modifier = Modifier
                    .height(50.dp)
                    .align(alignment = Alignment.CenterVertically),
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
