package com.example.cityapp.ui.screen.detail

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cityapp.R
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
fun DetailTopBar(name: String, onBackClick: () -> Unit, modifier: Modifier = Modifier) {
    TopAppBar(
        title = { Text(text = name) },
        modifier = modifier
            .shadow(elevation = 4.dp),
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Filled.ArrowBack, contentDescription = stringResource(id = R.string.back))
            }
        }
    )
}

@Composable
fun DetailBody(
    @DrawableRes photo: Int,
    description: String,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .verticalScroll(rememberScrollState())

    )
    {
        Image(
            painter = painterResource(id = photo),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .height(200.dp)
                .width(200.dp)
                .clip(
                    RoundedCornerShape(
                        bottomStart = 16.dp,
                        bottomEnd = 16.dp,
                        topStart = 16.dp,
                        topEnd = 16.dp
                    )
                )
        )
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = description,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Justify,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Normal
                )
            )
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
