package com.example.superheroes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.superheroes.R
import com.example.superheroes.data.Superhero
import com.example.superheroes.databinding.ItemSuperheroBinding

class SuperheroAdapter(private var items:List<Superhero> = listOf()) : RecyclerView.Adapter<SuperheroViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperheroViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.item_superhero, parent, false)
        return SuperheroViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: SuperheroViewHolder, position: Int) {
        holder.render(items[position])
        //holder.itemView.setOnClickListener { onClickListener(position) }
    }

    fun updateItems(results: List<Superhero>?) {
        items = results!!
        notifyDataSetChanged()
    }
}

class SuperheroViewHolder(view:View) : RecyclerView.ViewHolder(view) {

    //lateinit var binding:ItemSuperheroBinding

    var nameTextView:TextView = view.findViewById(R.id.nameTextView)

    fun render(superhero: Superhero) {
        nameTextView.text = superhero.name
    }

}