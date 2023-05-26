package com.example.movieapp.navigation

import com.example.movieapp.R

sealed class NavItem(var title: String, var icon: Int, var route: String) {

    object Login : NavItem("Login", R.drawable.ic_login, "login")
    object Search : NavItem("Search", R.drawable.ic_search, "search")
    object Favorites : NavItem("Favorites", R.drawable.ic_favorites, "favorites")

    companion object {
        fun String.title(): String {
            return when (this) {
                Login.route -> Login.title
                Search.route -> Search.title
                Favorites.route -> Favorites.title
                else -> ""
            }
        }
    }
}
