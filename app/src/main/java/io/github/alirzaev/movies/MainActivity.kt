package io.github.alirzaev.movies

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
        }
    }

    override fun onClick(id: Int) {
        supportFragmentManager.beginTransaction().apply {
            add(R.id.fragment_container, MovieDetailsFragment.newInstance(id))
            addToBackStack(null)
            commit()
        }
    }
}