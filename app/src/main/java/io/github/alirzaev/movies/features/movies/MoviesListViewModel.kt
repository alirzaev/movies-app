package io.github.alirzaev.movies.features.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.alirzaev.movies.data.source.MoviesRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _uiState = MutableLiveData(MoviesListUiState())

    val uiState: LiveData<MoviesListUiState> get() = _uiState

    init {
        loadMovies()
    }

    fun loadMovies(force: Boolean = false) {
        _uiState.value = _uiState.value?.copy(isLoading = true)

        viewModelScope.launch {
            if (!force) {
                moviesRepository.getCachedMovies()
                    .onSuccess {
                        _uiState.value =
                            _uiState.value?.copy(movies = it, error = null)
                    }.onFailure {
                        _uiState.value = _uiState.value?.copy(error = it)
                    }
            }

            moviesRepository.getMovies()
                .onSuccess {
                    _uiState.value =
                        _uiState.value?.copy(movies = it.results, isLoading = false, error = null)

                    moviesRepository.saveMovies(it.results)
                }.onFailure {
                    _uiState.value = _uiState.value?.copy(
                        isLoading = false,
                        error = it
                    )
                }
        }
    }
}