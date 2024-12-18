package com.example.gymlocator

import android.service.autofill.OnClickAction
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.ContentAlpha
import com.example.gymlocator.ui.theme.GymLocatorTheme
import com.example.gymlocator.ui.theme.Pink80


@Composable
fun GymScreen() {
    val gymVM: GymsViewModel = viewModel()


    LazyColumn {
        items(gymVM.state) {gym ->
            GymLocatorCard(gym) {
                gymVM.toggleFavoritesState(it)
            }
        }

    }

}
//isFavorites = !isFavorites
@Composable
fun GymLocatorCard(gym :Gym,onClick:(Int)->Unit) {
    val icon =  if (gym.isFavorites){
        Icons.Filled.Favorite
    }else{
        Icons.Filled.FavoriteBorder
    }

    Card(elevation = CardDefaults.cardElevation(5.dp), modifier = Modifier.padding(10.dp)) {
        Row(modifier = Modifier
            .padding(5.dp)
            .align(Alignment.CenterHorizontally)) {

            GymIcon(Icons.Filled.LocationOn,
                Modifier
                    .weight(0.15f)
                    .align(Alignment.CenterVertically)
            ) {}
            GymDetails(gym,Modifier.weight(0.70f))
            GymIcon(icon,
                Modifier
                    .weight(0.15f)
                    .align(Alignment.CenterVertically)
            ) {
                onClick(gym.id)
            }

        }
    }

}

@Composable
fun GymDetails(gym  : Gym,modifier: Modifier) {


    Column(modifier = modifier) {
        Text(
            text = gym.name,
            style = MaterialTheme.typography.headlineLarge,
            color = Pink80
        )
        Text(
            text =gym.location,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.alpha(ContentAlpha.medium)
        )


    }
}

@Composable
fun GymIcon(icon: ImageVector, modifier: Modifier , onClick:() -> Unit = {}) {

    Image(
        imageVector = icon,
        contentDescription = "location gym",
        modifier = modifier
            .padding(8.dp)
            .clickable {
                onClick()
            },
        colorFilter = ColorFilter.tint(
            Color.DarkGray
        )
    )
}

@Preview(showBackground = true)
@Composable
fun x(){
    GymLocatorTheme {
        GymScreen()
    }
}
