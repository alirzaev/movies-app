package io.github.alirzaev.movies.features.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.github.alirzaev.movies.R
import io.github.alirzaev.movies.data.models.Movie
import kotlin.math.roundToInt

class ViewHolder(
    itemView: View,
    private val listener: (movie: Movie) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    private val card: ConstraintLayout = itemView.findViewById(R.id.movie_card)

    private val name: TextView = itemView.findViewById(R.id.movie_card_name)

    private val rating: List<ImageView> = arrayListOf(
        itemView.findViewById(R.id.rating_star_1),
        itemView.findViewById(R.id.rating_star_2),
        itemView.findViewById(R.id.rating_star_3),
        itemView.findViewById(R.id.rating_star_4),
        itemView.findViewById(R.id.rating_star_5)
    )

    private val genres: TextView = itemView.findViewById(R.id.movie_card_genres)

    private val reviews: TextView = itemView.findViewById(R.id.movie_card_reviews)

    private val ageRestriction: TextView =
        itemView.findViewById(R.id.age_restriction)

    private val poster: ImageView = itemView.findViewById(R.id.movie_card_poster)

    private val liked: ImageView = itemView.findViewById(R.id.movie_card_like)

    fun bind(movie: Movie) {
        val context = itemView.context

        card.clipToOutline = true
        name.text = movie.title
        genres.text = movie.genres.joinToString(", ") { it.name }
        reviews.text = context.getString(R.string.label_movie_reviews, movie.voteCount)
        ageRestriction.text =
            context.getString(
                R.string.label_movie_age_restriction,
                (if (movie.adult) 18 else 12)
            )
        Glide.with(context)
            .load(movie.poster)
            .placeholder(R.drawable.image_placeholder)
            .into(poster)
        poster.clipToOutline = true
//            liked.setImageResource(if (movie.liked) R.drawable.ic_like_fill else R.drawable.ic_like)
        liked.setImageResource(R.drawable.ic_like)

        val roundRating = (movie.rating / 2).roundToInt()
        for (star in rating.dropLast(rating.size - roundRating)) {
            star.setImageResource(R.drawable.ic_star_fill)
        }
        for (star in rating.drop(roundRating)) {
            star.setImageResource(R.drawable.ic_star)
        }

        card.setOnClickListener {
            this@ViewHolder.listener(movie)
        }
    }
}

class MoviesListAdapter(private val listener: (movie: Movie) -> Unit) :
    RecyclerView.Adapter<ViewHolder>() {

    private var movies: List<Movie> = emptyList()

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