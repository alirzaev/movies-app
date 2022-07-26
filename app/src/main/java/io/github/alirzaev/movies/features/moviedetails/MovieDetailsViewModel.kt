package io.github.alirzaev.movies.features.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.alirzaev.movies.data.models.MovieDetails
import io.github.alirzaev.movies.data.source.MoviesRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class MovieDetailsViewModel : ViewModel() {

    private val _uiState = MutableLiveData(MovieDetailsUiState())

    private val _movie = MutableLiveData<MovieDetails>()

    val movie: LiveData<MovieDetails> get() = _movie

    val uiState: LiveData<MovieDetailsUiState> get() = _uiState

    fun loadMovie(id: Int) {
        _uiState.value = _uiState.value?.copy(isLoading = true)

        viewModelScope.launch {
            try {
                val movie = MoviesRepository.getMovie(id)

                _uiState.value =
                    _uiState.value?.copy(movie = movie, isLoading = false, error = null)
            } catch (ex: Exception) {
                _uiState.value = _uiState.value?.copy(
                    isLoading = false,
                    error = ex
                )
            }
        }
    }
}