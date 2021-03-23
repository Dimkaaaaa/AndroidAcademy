package com.example.androidacademy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidacademy.model.Actor

class AdapterActorList(
    context: Context,
    var actors: List<Actor>
): RecyclerView.Adapter<AdapterActorList.ActorListViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    fun getItem(position: Int): Actor = actors[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterActorList.ActorListViewHolder {
        return ActorListViewHolder(inflater.inflate(R.layout.view_holder_actor, parent,false))
    }

    override fun onBindViewHolder(holder: AdapterActorList.ActorListViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun getItemCount(): Int {
        return actors.size
    }

    class ActorListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val name: TextView = itemView.findViewById(R.id.tv_actor_ame)

        fun onBind(actor: Actor){
            name.text = actor.name
        }

    }

}

