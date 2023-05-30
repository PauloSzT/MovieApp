package com.example.movieapp.navigation

import com.example.movieapp.R
import com.example.movieapp.utils.Constants.FAVORITES_ROUTE
import com.example.movieapp.utils.Constants.FAVORITES_TITLE
import com.example.movieapp.utils.Constants.LOGIN_ROUTE
import com.example.movieapp.utils.Constants.LOGIN_TITLE
import com.example.movieapp.utils.Constants.SEARCH_ROUTE
import com.example.movieapp.utils.Constants.SEARCH_TITLE

sealed class NavItem(var title: String, var icon: Int, var route: String) {

    object Login : NavItem(LOGIN_TITLE, R.drawable.ic_login, LOGIN_ROUTE)
    object Search : NavItem(SEARCH_TITLE, R.drawable.ic_search, SEARCH_ROUTE)
    object Favorites : NavItem(FAVORITES_TITLE, R.drawable.ic_favorites, FAVORITES_ROUTE)

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
