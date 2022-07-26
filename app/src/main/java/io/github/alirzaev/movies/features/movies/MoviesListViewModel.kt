package io.github.alirzaev.movies.features.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.alirzaev.movies.data.source.MoviesRepository
import kotlinx.coroutines.launch

class MoviesListViewModel : ViewModel() {

    private val _uiState = MutableLiveData(MoviesListUiState())

    val uiState: LiveData<MoviesListUiState> get() = _uiState

    init {
        loadMovies()
    }

    private fun loadMovies() {
        _uiState.value = _uiState.value?.copy(isLoading = true)

        viewModelScope.launch {
            try {
                val movies = MoviesRepository.getMoviesPaginated().results

                _uiState.value =
                    _uiState.value?.copy(movies = movies, isLoading = false, error = null)
            } catch (ex: Exception) {
                _uiState.value = _uiState.value?.copy(
                    isLoading = false,
                    error = ex
                )
            }
        }
    }
}