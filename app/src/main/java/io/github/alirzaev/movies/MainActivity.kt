package io.github.alirzaev.movies

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import io.github.alirzaev.movies.databinding.ActivityMainBinding
import io.github.alirzaev.movies.features.moviedetails.MovieDetailsFragment
import io.github.alirzaev.movies.features.movies.MoviesListFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MoviesListFragment.OnMovieClickListener {

    private lateinit var binding: ActivityMainBinding

    private val moviesListFragment = MoviesListFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                add(R.id.fragment_container, moviesListFragment)
                commit()
            }

            if (intent != null) {
                handleIntent(intent)
            }
        }
    }

    override fun onClick(id: Int) {
        supportFragmentManager.beginTransaction().apply {
            add(R.id.fragment_container, MovieDetailsFragment.newInstance(id))
            addToBackStack(null)
            commit()
        }
    }

    private fun handleIntent(intent: Intent) {
        when (intent.action) {
            Intent.ACTION_VIEW -> intent
                .data
                ?.lastPathSegment
                ?.toIntOrNull()
                ?.let { movieId ->
                    supportFragmentManager.beginTransaction().apply {
                        add(R.id.fragment_container, MovieDetailsFragment.newInstance(movieId))
                        addToBackStack(null)
                        commit()
                    }
                }
        }
    }
}