package com.example.creditagricolproject.ui

import AccountDetailScreen
import MyScreen
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.creditagricolproject.data.repository.AccountsRepository
import com.example.creditagricolproject.ui.theme.CreditAgricolProjectTheme
import com.example.creditagricolproject.util.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var accountsRepository: AccountsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        installSplashScreen()

        setContent {
            val viewModel: MainViewModel = hiltViewModel()
            val uiState = viewModel.uiState.value
            val accountsResponse = viewModel.accountsResponse
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = "mainScreen"
            ) {
                composable("mainScreen") {
                    MyScreen(
                        uiState = uiState,
                        response = accountsResponse,
                        navController = navController
                    )
                }
                composable("detailScreen/{accountId}") { backStackEntry ->
                    val accountId = backStackEntry.arguments?.getString("accountId")
                    val selectedAccount = accountsResponse.data
                        .flatMap { it.accounts }
                        .firstOrNull { it.id == accountId }

                    if (selectedAccount != null) {
                        AccountDetailScreen(
                            account = selectedAccount,
                            navController = navController
                        )
                    } else {
                        Log.d(localClassName,"Compte introuvable")
                    }
                }
            }
        }
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        CreditAgricolProjectTheme {
            Greeting("Android")
        }
    }
}