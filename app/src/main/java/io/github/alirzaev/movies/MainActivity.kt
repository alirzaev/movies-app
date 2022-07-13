package io.github.alirzaev.movies

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
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

    override fun onClick() {
        supportFragmentManager.beginTransaction().apply {
            add(R.id.fragment_container, FragmentMoviesDetails())
            addToBackStack(null)
            commit()
        }
    }
}