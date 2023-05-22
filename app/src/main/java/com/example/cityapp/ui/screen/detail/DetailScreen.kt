package com.example.cityapp.ui.screen.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cityapp.JetCitiesApp
import com.example.cityapp.di.Injection
import com.example.cityapp.ui.ViewModelFactory
import com.example.cityapp.ui.common.UiState
import com.example.cityapp.ui.theme.CityAppTheme

@Composable
fun DetailScreen(
    cityId: Long,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateBack: () -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getCityById(cityId)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    data.photo,
                    data.name,
                    data.description,
                    onBackClick = navigateBack,
                )
            }
            is UiState.Error -> {}
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailContent(
    @DrawableRes photo: Int,
    name: String,
    description: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
        DetailTopBar(
            name = name,
            onBackClick = onBackClick,
        )
    }) {
        DetailBody(
            photo = photo,
            description = description,
            modifier = Modifier.padding(it)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopBar(name: String, onBackClick: () -> Unit, modifier: Modifier = Modifier){
    TopAppBar(
        title = { Text(text = name) },
        modifier = modifier,
        navigationIcon = {
             IconButton(onClick = onBackClick) {
                 Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
             }
        }
    )
}

@Composable
fun DetailBody(photo: Int, description: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Column(modifier = Modifier
            .verticalScroll(rememberScrollState())
            .weight(1f))
        {
            Box{
               Image(
                   painter = painterResource(id = photo),
                   contentDescription = null,
                   contentScale = ContentScale.Crop,
                   modifier = modifier
                       .height(400.dp)
                       .fillMaxWidth()
                       .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
               )
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = description,
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Normal
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailContentPreview() {
    CityAppTheme {
        DetailContent(
            photo = 0,
            name = "City Name",
            description = "City Description",
            onBackClick = {}
        )
    }
}
