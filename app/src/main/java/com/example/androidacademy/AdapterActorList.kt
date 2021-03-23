package com.example.androidacademy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidacademy.databinding.ViewHolderActorBinding
import com.example.androidacademy.model.Actor

class AdapterActorList: ListAdapter<Actor, AdapterActorList.ActorListViewHolder>(DiffCallback())  {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorListViewHolder {
        val binding =
                ViewHolderActorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActorListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ActorListViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class ActorListViewHolder(private val binding: ViewHolderActorBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(actor: Actor) {
            binding.tvActorAme.text = actor.name
            Glide.with(binding.root)
                    .load(actor.imageUrl)
                    .into(binding.ivActor)
        }
    }

}

class DiffCallback : DiffUtil.ItemCallback<Actor>() {
    override fun areItemsTheSame(oldItem: Actor, newItem: Actor): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Actor, newItem: Actor): Boolean {
        return oldItem == newItem
    }
}



