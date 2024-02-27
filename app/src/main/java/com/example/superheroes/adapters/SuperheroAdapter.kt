package com.example.superheroes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.superheroes.R
import com.example.superheroes.data.Superhero
import com.example.superheroes.databinding.ItemSuperheroBinding
import com.squareup.picasso.Picasso

class SuperheroAdapter(private var items:List<Superhero> = listOf(),
                       private val onClickListener: (position:Int) -> Unit
) : RecyclerView.Adapter<SuperheroViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperheroViewHolder {
        val binding = ItemSuperheroBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SuperheroViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: SuperheroViewHolder, position: Int) {
        holder.render(items[position])
        holder.itemView.setOnClickListener { onClickListener(position) }
    }

    fun updateItems(results: List<Superhero>) {
        items = results
        notifyDataSetChanged()
    }
}

class SuperheroViewHolder(val binding:ItemSuperheroBinding) : RecyclerView.ViewHolder(binding.root) {

    fun render(superhero: Superhero) {
        binding.nameTextView.text = superhero.name
        Picasso.get().load(superhero.image.url).into(binding.photoImageView)
    }

}