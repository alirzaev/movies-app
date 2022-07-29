package io.github.alirzaev.movies.features.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.alirzaev.movies.data.models.MovieDetails
import io.github.alirzaev.movies.data.source.MoviesRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _uiState = MutableLiveData(MovieDetailsUiState())

    private val _movie = MutableLiveData<MovieDetails>()

    val movie: LiveData<MovieDetails> get() = _movie

    val uiState: LiveData<MovieDetailsUiState> get() = _uiState

    fun loadMovie(id: Int) {
        _uiState.value = _uiState.value?.copy(isLoading = true)

        viewModelScope.launch {
            moviesRepository.getCachedMovie(id)
                .onSuccess {
                    _uiState.value =
                        _uiState.value?.copy(movie = it, error = null)
                }.onFailure {
                    _uiState.value = _uiState.value?.copy(
                        error = it
                    )
                }

            moviesRepository.getMovie(id)
                .onSuccess {
                    _uiState.value =
                        _uiState.value?.copy(movie = it, isLoading = false, error = null)

                    moviesRepository.saveMovieDetails(it)
                }
                .onFailure {
                    _uiState.value = _uiState.value?.copy(
                        isLoading = false,
                        error = it
                    )
                }
        }
    }
}