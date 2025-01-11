package com.example.gymlocator.gyms.persentation.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gymlocator.gyms.persentation.gymsList.GymDetails
import com.example.gymlocator.gyms.persentation.gymsList.GymIcon

@Composable
fun GymDetailsScreen() {

    val viewModel: GymsDetailsViewModel = viewModel()
    val item = viewModel.state
    item?.let {
        Column(horizontalAlignment = Alignment.CenterHorizontally , modifier = Modifier.fillMaxSize().padding(16.dp)) {
            GymIcon(
                icon = Icons.Filled.LocationOn,
                modifier = Modifier.padding(top = 32.dp, bottom = 32.dp)
            )
            GymDetails(
                gym = item,
                modifier = Modifier.padding(bottom = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            Text(
                text = if (item.isOpen) "Gym is Open" else "Gym is Closed",
                color = if (item.isOpen) Color.DarkGray else Color.Red
            )
        }
    }
}