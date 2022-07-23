package io.github.alirzaev.movies.features.movies

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.alirzaev.movies.data.models.Movie
import io.github.alirzaev.movies.data.source.MoviesRepository
import kotlinx.coroutines.launch

class MoviesListViewModel : ViewModel() {
    private val _movies = MutableLiveData<List<Movie>>(emptyList())

    val movies: LiveData<List<Movie>> get() = _movies

    fun loadMovies(context: Context) {
        viewModelScope.launch {
            _movies.value = MoviesRepository.getMovies(context)
        }
    }
}