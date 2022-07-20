package io.github.alirzaev.movies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.alirzaev.movies.data.models.Movie
import io.github.alirzaev.movies.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), FragmentMoviesList.OnMovieClickListener {

    private lateinit var binding: ActivityMainBinding

    private val moviesListFragment = FragmentMoviesList().apply {
        setListener(this@MainActivity)
    }

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

    override fun onClick(movie: Movie) {
        supportFragmentManager.beginTransaction().apply {
            add(R.id.fragment_container, FragmentMoviesDetails())
            addToBackStack(null)
            commit()
        }
    }
}