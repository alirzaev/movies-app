package io.github.alirzaev.movies.features.movies

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import io.github.alirzaev.movies.data.source.MoviesRepository
import io.github.alirzaev.movies.data.models.Movie
import io.github.alirzaev.movies.databinding.FragmentMoviesListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviesListFragment : Fragment() {

    private var _binding: FragmentMoviesListBinding? = null

    private val binding get() = _binding!!

    private var onMovieClickListener: OnMovieClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnMovieClickListener) {
            onMovieClickListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesListBinding.inflate(layoutInflater)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            moviesList.layoutManager = GridLayoutManager(context, 2)
            moviesList.adapter = MoviesListAdapter {
                this@MoviesListFragment.onMovieClickListener?.onClick(it)
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                loadMovies()
            }
        }
    }

    override fun onDetach() {
        super.onDetach()

        onMovieClickListener = null
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

    private suspend fun loadMovies() = withContext(Dispatchers.Main) {
        val movies = MoviesRepository.getMovies(requireContext())

        (binding.moviesList.adapter as MoviesListAdapter).apply {
            bindMovies(movies)
        }
    }

    interface OnMovieClickListener {
        fun onClick(movie: Movie)
    }

    companion object {
        @JvmStatic
        fun newInstance() = MoviesListFragment()
    }
}