package com.example.movieapp.ui.logout

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.movieapp.MainActivityViewModel
import com.example.movieapp.R
import com.example.movieapp.navigation.NavItem
import com.example.movieapp.ui.theme.MovieAppTheme

@Composable
fun LogoutDrawerSheet(
    navController: NavHostController,
    viewModel: MainActivityViewModel,
    closeDrawer: () -> Unit
) {
    val userName by viewModel.mainActivityUiState.userName.collectAsState()
    val loggedTime by viewModel.mainActivityUiState.loggedTime.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "MOVIE APP",
            fontSize = 16.sp,
            letterSpacing = 1.sp,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.login_image),
            contentDescription = null,
            modifier = Modifier
                .height(250.dp)
                .width(250.dp)
        )
        Text(
            text = "User: $userName",
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Text(
            text = "Last Connection: $loggedTime ",
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Spacer(modifier = Modifier.height(100.dp))
        NavigationDrawerItem(
            label = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Logout",
                    textAlign = TextAlign.Center
                )
            },
            selected = false,
            onClick = {
                viewModel.mainActivityUiState.deleteAllItems()
                navController.navigate(NavItem.Login.route)
                closeDrawer()
            },
            modifier = Modifier.width(200.dp),
            colors = NavigationDrawerItemDefaults.colors(
                unselectedContainerColor = MaterialTheme.colorScheme.primary,
                unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
                unselectedIconColor = MaterialTheme.colorScheme.onPrimary,
                unselectedBadgeColor = MaterialTheme.colorScheme.onPrimary
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LogoutDrawerPreview() {
    MovieAppTheme {
        LogoutDrawerSheet(
            navController = NavHostController(LocalContext.current),
            viewModel = MainActivityViewModel(LocalContext.current),
            closeDrawer = {}
        )
    }
}
