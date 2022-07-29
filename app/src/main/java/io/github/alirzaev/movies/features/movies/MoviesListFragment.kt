package io.github.alirzaev.movies.features.movies

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import io.github.alirzaev.movies.databinding.FragmentMoviesListBinding
import kotlinx.coroutines.launch

class MoviesListFragment : Fragment() {

    private var _binding: FragmentMoviesListBinding? = null

    private val binding get() = _binding!!

    private val viewModel: MoviesListViewModel by activityViewModels()

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
            val spanCount = when (resources.configuration.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> 4
                else -> 2
            }
            moviesList.layoutManager = GridLayoutManager(context, spanCount)
            moviesList.adapter = MoviesListAdapter {
                this@MoviesListFragment.onMovieClickListener?.onClick(it.id)
            }

            refreshLayout.setOnRefreshListener {
                viewModel.loadMovies(true)
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.observe(viewLifecycleOwner) { state ->
                    (binding.moviesList.adapter as MoviesListAdapter).apply {
                        bindMovies(state.movies)
                    }

                    binding.refreshLayout.isRefreshing = state.isLoading
                }
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

    interface OnMovieClickListener {
        fun onClick(id: Int)
    }

    companion object {
        @JvmStatic
        fun newInstance() = MoviesListFragment()
    }
}