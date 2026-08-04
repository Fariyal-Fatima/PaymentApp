package com.security.zarpay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.security.zarpay.ui.components.BottomNavBar
import com.security.zarpay.ui.screen.HistoryScreen
import com.security.zarpay.ui.screen.HomeScreen
import com.security.zarpay.ui.screen.LoginScreen
import com.security.zarpay.ui.screen.PayScreen
import com.security.zarpay.ui.screen.ProfileScreen
import com.security.zarpay.ui.screen.SignupScreen
import com.security.zarpay.ui.theme.ZarPayTheme
import com.security.zarpay.ui.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZarPayTheme {
                ZarPayApp()
            }
        }
    }

    @Composable
    fun ZarPayApp() {
        val navController = rememberNavController()
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = currentBackStackEntry?.destination?.route ?: "home"

        val authViewModel: AuthViewModel = viewModel()
        val startDestination = if (authViewModel.getCurrentUserId() != null) "home" else "login"

        // Bottom bar sirf in screens pe dikhana hai
        val showBottomBar = currentRoute !in listOf("login", "signup")
        Scaffold(
            bottomBar = {
                BottomNavBar(
                    selectedRoute = currentRoute,
                    onNavigate = { title ->
                        val route = title.trim().lowercase()
                        navController.navigate(route) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                        }
                    }
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = startDestination,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(route = "login") {
                    LoginScreen(
                        onLoginSuccess = {
                            navController.navigate("home") {
                                popUpTo("login") { inclusive = true }
                            }
                        },
                        onNavigateToSignup = { navController.navigate("signup") }
                    )
                }
                composable(route = "signup") {
                    SignupScreen(
                        onSignupSuccess = {
                            navController.navigate("home") {
                                popUpTo("signup") { inclusive = true }
                            }
                        },
                        onNavigateToLogin = { navController.navigate("login") }
                    )
                }
                composable(route = "home") {
                    HomeScreen(onNavigate = { route -> navController.navigate(route) })
                }
                composable(route = "pay") {
                    PayScreen(
                        onContactSelected = { },
                        onBack = { navController.popBackStack() }
                    )
                }
                composable(route = "history") {
                    HistoryScreen(onBack = { navController.popBackStack() })
                }
                composable(route = "profile") {
                    ProfileScreen(
                        userName = "Fariyal Fatima",
                        onLogout = {
                            FirebaseAuth.getInstance().signOut()
                            navController.navigate("login") {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    )
                }
            }
        }
    }
}