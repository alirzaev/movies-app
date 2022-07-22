package io.github.alirzaev.movies.features.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import io.github.alirzaev.movies.R
import io.github.alirzaev.movies.data.models.Movie
import io.github.alirzaev.movies.databinding.FragmentMovieDetailsBinding
import kotlin.math.roundToInt

private const val MOVIE_ARG = "MOVIE_ARG"

class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null

    private val binding get() = _binding!!

    private lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movie = arguments?.getParcelable(MOVIE_ARG)
            ?: throw IllegalStateException("'${MOVIE_ARG}' not found")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieDetailsBinding.inflate(layoutInflater)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            movieName.text = movie.title
            movieGenres.text = movie.genres.joinToString(", ") { it.name }
            movieReviews.text =
                requireContext().getString(R.string.label_movie_reviews, movie.voteCount)
            storyline.text = movie.overview
            Glide.with(requireContext())
                .load(movie.backdrop)
                .into(movieBackdrop)
            ageRestriction.text =
                requireContext().getString(
                    R.string.label_movie_age_restriction,
                    (if (movie.adult) 18 else 12)
                )

            val rating: List<ImageView> = arrayListOf(
                ratingStar1,
                ratingStar2,
                ratingStar3,
                ratingStar4,
                ratingStar5,
            )
            val roundRating = (movie.rating / 2).roundToInt()
            for (star in rating.dropLast(rating.size - roundRating)) {
                star.setImageResource(R.drawable.ic_star_fill)
            }
            for (star in rating.drop(roundRating)) {
                star.setImageResource(R.drawable.ic_star)
            }

            castList.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            castList.adapter = ActorsListAdapter()
            (castList.adapter as ActorsListAdapter).apply {
                bindActors(movie.actors)
            }
            castList.addItemDecoration(
                HorizontalGapItemDecoration(
                    requireContext().resources.getDimensionPixelSize(
                        R.dimen.space_2x
                    )
                )
            )
            if (movie.actors.isEmpty()) {
                castList.visibility = View.GONE
                castLabel.visibility = View.GONE
            } else {
                castList.visibility = View.VISIBLE
                castLabel.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(movie: Movie) = MovieDetailsFragment().apply {
            arguments = Bundle().apply { putParcelable(MOVIE_ARG, movie) }
        }
    }
}