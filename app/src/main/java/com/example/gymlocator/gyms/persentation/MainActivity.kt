package com.example.gymlocator.gyms.persentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gymlocator.gyms.persentation.details.GymDetailsScreen
import com.example.gymlocator.gyms.persentation.gymsList.GymScreen
import com.example.gymlocator.gyms.persentation.gymsList.GymsViewModel
import com.example.gymlocator.ui.theme.GymLocatorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GymLocatorTheme {
                GymAroundApp()
//                GymScreen()
                //    GymDetailsScreen()
            }
        }
    }
}

@Composable
fun GymAroundApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "gyms") {
        composable(route = "gyms") {
            val vm:GymsViewModel = hiltViewModel()
            GymScreen(
                state = vm.state.value,
                onGymLocatorClicked = {id ->
                    navController.navigate("gyms/$id")},
                onFavoritesIconClick = { id, oldValue -> vm.toggleFavoritesState(id,oldValue)}
            )
        }
        composable(
            route = "gyms/{gym_id}", arguments = listOf(
                navArgument("gym_id") {
                    type = NavType.IntType
                },
            )
        ) {
            GymDetailsScreen()
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
    GymLocatorTheme {
        Greeting("Android")
    }
}




