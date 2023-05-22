package com.example.cityapp.ui.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cityapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(modifier: Modifier = Modifier, navigateBack: () -> Unit) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.profile)) },
                modifier = modifier
                    .shadow(elevation = 4.dp),
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.padding(16.dp))
            Image(
                painter = painterResource(id = R.drawable.my_image),
                contentDescription = stringResource(R.string.image_detail),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(it)
                    .size(250.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Transparent, CircleShape)

            )
            Spacer(modifier = Modifier.padding(16.dp))
            Text(text = "Wahyu Dwi", fontWeight = FontWeight.Bold, fontSize = 32.sp)
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = "wahyu5161.w5@gmail.com", fontWeight = FontWeight.Bold, fontSize = 24.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProfileScreen() {
    ProfileScreen(navigateBack = {})
}
