package com.example.cityapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cityapp.ui.navigation.Screen
import com.example.cityapp.ui.screen.detail.DetailScreen
import com.example.cityapp.ui.screen.home.HomeScreen
import com.example.cityapp.ui.screen.profile.ProfileScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JetCitiesApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        topBar = {
            if (currentRoute == Screen.Home.route) {
                TopBar(navController = navController)
            }
        }, modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(navigateToDetail = { cityId ->
                    navController.navigate(Screen.DetailCity.createRoute(cityId))
                })
            }

            composable(Screen.Profile.route) {
                ProfileScreen(navigateBack = { navController.navigateUp() })
            }

            composable(
                Screen.DetailCity.route,
                arguments = listOf(navArgument("cityId") { type = NavType.LongType })
            ) {
                val id = it.arguments?.getLong("cityId") ?: -1L
                DetailScreen(
                    cityId = id,
                    navigateBack = { navController.navigateUp() },
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    TopAppBar(
        modifier = modifier.shadow(elevation = 4.dp),
        title = { Text(text = stringResource(R.string.jetcities)) },
        actions = {
            IconButton(onClick = {
                navController.navigate(Screen.Profile.route)
            }) {
                Icon(imageVector = Icons.Default.Person, contentDescription = stringResource(R.string.about_page))
            }
        },

        )
}

@Preview(showBackground = true)
@Composable
fun JetCitiesAppPreview() {
    JetCitiesApp()
}

