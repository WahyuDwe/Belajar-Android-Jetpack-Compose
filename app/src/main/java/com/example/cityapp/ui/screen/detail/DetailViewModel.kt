package com.example.cityapp.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cityapp.data.CityRepository
import com.example.cityapp.model.City
import com.example.cityapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: CityRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<City>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<City>>
        get() = _uiState

    fun getCityById(cityId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getCityById(cityId))
        }
    }
}