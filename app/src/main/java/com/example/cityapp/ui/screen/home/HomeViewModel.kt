package com.example.cityapp.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cityapp.data.CityRepository
import com.example.cityapp.model.City
import com.example.cityapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: CityRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<City>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<City>>>
        get() = _uiState

    fun getAllCities() {
        viewModelScope.launch {
            repository.getAllCities()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

}