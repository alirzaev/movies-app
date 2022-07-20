package io.github.alirzaev.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.alirzaev.movies.data.models.Actor
import io.github.alirzaev.movies.data.models.Movie

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
    ),
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

class FragmentMoviesDetails : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actorsListRecycleView = view.findViewById<RecyclerView>(R.id.rv_actors_list)
        actorsListRecycleView?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        actorsListRecycleView?.adapter = ActorsListAdapter()
        (actorsListRecycleView?.adapter as ActorsListAdapter).apply {
            bindActors(actors)
        }
        actorsListRecycleView?.addItemDecoration(
            SpacingItemDecoration(
                requireContext().resources.getDimensionPixelSize(
                    R.dimen.space_2x
                )
            )
        )
    }

    companion object {
        fun newInstance(movie: Movie) = FragmentMoviesDetails()
    }
}

class ActorsListAdapter : RecyclerView.Adapter<ActorsListAdapter.ViewHolder>() {
    private var actors: List<Actor> = emptyList()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.actor_name)

        private val image: ImageView = itemView.findViewById(R.id.actor_image)

        fun bind(actor: Actor) {
            name.text = actor.name
            image.setImageResource(actor.image)
        }
    }

    fun bindActors(actors: List<Actor>) {
        this.actors = actors
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_actor, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(actors[position])
    }

    override fun getItemCount(): Int {
        return actors.size
    }
}