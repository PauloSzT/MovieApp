package com.example.movieapp.ui.login

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.movieapp.R
import com.example.movieapp.navigation.NavItem
import com.example.movieapp.ui.theme.MovieAppTheme
import com.example.movieapp.utils.Constants.LOG_IN
import com.example.movieapp.utils.Constants.PASSWORD_TEXT
import com.example.movieapp.utils.Constants.USER_TEXT
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun LoginScreen(
    viewModel: LoginScreenViewModel,
    navController: NavHostController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(color = MaterialTheme.colorScheme.primary)
    ) {
        LoginScreenContent(
            loginUiState = viewModel.loginUiState,
            modifier = Modifier.align(Alignment.Center),
            navigateToSearch = { navController.navigate(NavItem.Search.route) }
        )
    }
}

@Composable
fun LoginScreenContent(
    loginUiState: LoginUiState,
    modifier: Modifier,
    navigateToSearch: () -> Unit
) {
    val localContext = LocalContext.current
    val userTextFieldValue by loginUiState.userTextFieldValue.collectAsState()
    val passwordTextFieldValue by loginUiState.passwordTextFieldValue.collectAsState()
    val isUserLoggedIn by loginUiState.isUserLoggedIn.collectAsState()

    LaunchedEffect(key1 = isUserLoggedIn) {
        if (isUserLoggedIn) {
            navigateToSearch()
        }
    }

    Column(
        modifier = modifier
    ) {
        HeaderImage(Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.padding(16.dp))
        UserField(
            userTextFieldValue = userTextFieldValue,
            onUserValueChange = loginUiState.onUserValueChange
        )
        Spacer(modifier = Modifier.padding(4.dp))
        PasswordField(
            passwordTextFieldValue = passwordTextFieldValue,
            onPasswordValueChange = loginUiState.onPasswordValueChange
        )
        Spacer(modifier = Modifier.padding(16.dp))
        LoginButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            localContext = localContext,
            login = loginUiState.login,
            navigateToSearch = { navigateToSearch() }
        )
    }
}

@Composable
fun LoginButton(
    modifier: Modifier,
    localContext: Context,
    login: () -> Boolean,
    navigateToSearch: () -> Unit
) {
    Button(
        onClick = {
            if (!login()) {
                Toast.makeText(
                    localContext,
                    localContext.getString(R.string.user_password_incorrect),
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else {
                navigateToSearch()
            }
        },
        modifier = modifier
            .height(48.dp)
            .width(250.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = Color.Transparent,
        )
    ) {
        Text(text = LOG_IN)
    }
}

@Composable
fun PasswordField(
    passwordTextFieldValue: String,
    onPasswordValueChange: (String) -> Unit
) {
    TextField(
        value = passwordTextFieldValue,
        onValueChange = onPasswordValueChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = PASSWORD_TEXT) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.colors(MaterialTheme.colorScheme.onBackground)
    )
}

@Composable
fun UserField(
    userTextFieldValue: String,
    onUserValueChange: (String) -> Unit
) {
    TextField(
        value = userTextFieldValue,
        onValueChange = onUserValueChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = USER_TEXT) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.colors(MaterialTheme.colorScheme.onBackground)
    )
}

@Composable
fun HeaderImage(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.login_image),
        contentDescription = null,
        modifier = modifier
            .height(250.dp)
            .width(250.dp)
    )
}

@Preview
@Composable
fun LoginScreenPreview() {
    val loginUiState = LoginUiState(
        userTextFieldValue = MutableStateFlow(""),
        passwordTextFieldValue = MutableStateFlow(""),
        isUserLoggedIn = MutableStateFlow(false),
        login = { true },
        onPasswordValueChange = {},
        onUserValueChange = {}

    )
    MovieAppTheme {
        LoginScreenContent(
            loginUiState = loginUiState,
            modifier = Modifier,
            navigateToSearch = {}
        )
    }
}
