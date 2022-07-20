package io.github.alirzaev.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.alirzaev.movies.data.models.Actor
import io.github.alirzaev.movies.data.models.Movie
import io.github.alirzaev.movies.databinding.FragmentMoviesListBinding
import kotlin.math.roundToInt

private val actors = arrayListOf(
    Actor(
        "Robert Downey Jr.",
        R.drawable.sample_actor_image_robert_downey_jr
    ),
    Actor(
        "Chris Evans",
        R.drawable.sample_actor_image_chris_evans
    ),
    Actor(
        "Mark Ruffalo",
        R.drawable.sample_actor_image_mark_ruffalo
    ),
    Actor(
        "Chris Hemsworth",
        R.drawable.sample_actor_image_chris_h
    )
)

private val movies = arrayListOf(
    Movie(
        "Avengers: End Game",
        4.0,
        13,
        R.drawable.sample_card_poster_avenger_end_game,
        arrayListOf("Action", "Adventure", "Drama"),
        (0..125).map { "" },
        137,
        actors,
        false
    ),
    Movie(
        "Tenet",
        5.0,
        16,
        R.drawable.sample_card_poster_tenet,
        arrayListOf("Action", "Sci-Fi", "Thriller "),
        (0..98).map { "" },
        97,
        actors,
        true
    ),
    Movie(
        "Black Widow",
        4.0,
        13,
        R.drawable.sample_card_poster_black_widow,
        arrayListOf("Action", "Adventure", "Sci-Fi"),
        (0..38).map { "" },
        102,
        actors,
        false
    ),
    Movie(
        "Wonder Woman 1984",
        5.0,
        13,
        R.drawable.sample_card_poster_wonder_women,
        arrayListOf("Action", "Adventure", "Fantasy"),
        (0..74).map { "" },
        120,
        actors,
        false
    )
)

class FragmentMoviesList : Fragment() {

    private lateinit var binding: FragmentMoviesListBinding

    private var listener: OnMovieClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            rvMoviesList.layoutManager = GridLayoutManager(context, 2)
            rvMoviesList.adapter = MoviesListAdapter(object : OnMovieClickListener {
                override fun onClick(movie: Movie) {
                    this@FragmentMoviesList.listener?.onClick(movie)
                }
            })
            (rvMoviesList.adapter as MoviesListAdapter).apply {
                bindMovies(movies)
            }
        }
    }

    fun setListener(l: OnMovieClickListener) {
        listener = l
    }

    interface OnMovieClickListener {
        fun onClick(movie: Movie)
    }
}

class MoviesListAdapter(private val listener: FragmentMoviesList.OnMovieClickListener) :
    RecyclerView.Adapter<MoviesListAdapter.ViewHolder>() {
    private var movies: List<Movie> = emptyList()

    class ViewHolder(
        itemView: View,
        private val listener: FragmentMoviesList.OnMovieClickListener
    ) : RecyclerView.ViewHolder(itemView) {
        private val card: ConstraintLayout = itemView.findViewById(R.id.movie_card)

        private val name: TextView = itemView.findViewById(R.id.movie_card_movie_name)

        private val rating: List<ImageView> = arrayListOf(
            itemView.findViewById(R.id.rating_star_1),
            itemView.findViewById(R.id.rating_star_2),
            itemView.findViewById(R.id.rating_star_3),
            itemView.findViewById(R.id.rating_star_4),
            itemView.findViewById(R.id.rating_star_5)
        )

        private val tags: TextView = itemView.findViewById(R.id.movie_card_tags_list)

        private val reviews: TextView = itemView.findViewById(R.id.movie_card_reviews)

        private val duration: TextView = itemView.findViewById(R.id.movie_card_movie_duration)

        private val ageRestriction: TextView =
            itemView.findViewById(R.id.movie_card_age_restriction)

        private val poster: ImageView = itemView.findViewById(R.id.movie_card_poster)

        private val liked: ImageView = itemView.findViewById(R.id.movie_card_like)

        fun bind(movie: Movie) {
            val context = itemView.context

            name.text = movie.name
            tags.text = movie.tags.joinToString(", ")
            reviews.text = context.getString(R.string.label_movie_reviews, movie.reviews.size)
            duration.text = context.getString(R.string.label_movie_running_time, movie.runningTime)
            ageRestriction.text =
                context.getString(R.string.label_movie_age_restriction, movie.ageRestriction)
            poster.setImageResource(movie.poster)
            liked.setImageResource(if (movie.liked) R.drawable.ic_like_fill else R.drawable.ic_like)

            val roundRating = movie.rating.roundToInt()
            for (star in rating.dropLast(rating.size - roundRating)) {
                star.setImageResource(R.drawable.ic_star_fill)
            }
            for (star in rating.drop(roundRating)) {
                star.setImageResource(R.drawable.ic_star)
            }

            card.setOnClickListener {
                this@ViewHolder.listener.onClick(movie)
            }
        }
    }

    fun bindMovies(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie, parent, false),
            listener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}