package com.security.zarpay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.security.zarpay.ui.components.BottomNavBar
import com.security.zarpay.ui.screen.HistoryScreen
import com.security.zarpay.ui.screen.HomeScreen
import com.security.zarpay.ui.screen.PayScreen
import com.security.zarpay.ui.screen.ProfileScreen
import com.security.zarpay.ui.theme.ZarPayTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZarPayTheme {  }
            ZarPayApp()
        }
    }
}

@Composable
fun ZarPayApp() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route ?: "home"

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
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = "home") {
                HomeScreen(onNavigate = { route -> navController.navigate(route) })
            }
            composable(route = "pay") {
                PayScreen(
                    onContactSelected = {     },
                    onBack = { navController.popBackStack() }
                )
            }

            composable(route = "history") {
                HistoryScreen(onBack = { navController.popBackStack() })
            }
            composable(route = "profile") {
                ProfileScreen(userName = "Fariyal Fatima ")
            }
        }
    }
}