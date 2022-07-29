package io.github.alirzaev.movies.features.moviedetails

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.github.alirzaev.movies.R
import io.github.alirzaev.movies.data.models.Actor

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val name: TextView = itemView.findViewById(R.id.actor_name)

    private val image: ImageView = itemView.findViewById(R.id.actor_image)

    fun bind(actor: Actor) {
        name.text = actor.name
        Glide.with(itemView.context)
            .load(actor.image)
            .placeholder(R.drawable.image_placeholder)
            .into(image)
        image.clipToOutline = true
    }
}

class ActorsListAdapter : RecyclerView.Adapter<ViewHolder>() {

    private var actors: List<Actor> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
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