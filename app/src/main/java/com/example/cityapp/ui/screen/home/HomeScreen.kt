package com.example.cityapp.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cityapp.di.Injection
import com.example.cityapp.model.City
import com.example.cityapp.ui.ViewModelFactory
import com.example.cityapp.ui.common.UiState
import com.example.cityapp.ui.component.CityItem

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllCities()
            }

            is UiState.Success -> {
                HomeContent(
                    cities = uiState.data,
                    modifier = modifier,
                    navigateToDetail = navigateToDetail,
                )
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun HomeContent(
    cities: List<City>,
    modifier: Modifier,
    navigateToDetail: (Long) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        items(cities) { city ->
            CityItem(
                name = city.name,
                photo = city.photo,
                modifier = Modifier.clickable { navigateToDetail(city.id) },
            )
        }
    }
}
