package com.example.buddier.ui.homeActivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.buddier.R
import com.bumptech.glide.Glide
import com.example.buddier.ui.homeActivity.model.PetModel

class CardStackAdapter(
    private var pets: List<PetModel> = emptyList()
) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.card_buddy, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pet = pets[position]
        holder.name.text = "${pet.name}, ${pet.age} years old"
        holder.description.text = pet.description
        Glide.with(holder.image)
            .load(pet.url)
            .into(holder.image)
        holder.itemView.setOnClickListener { v ->
            Toast.makeText(v.context, pet.name, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return pets.size
    }

    fun setSpots(petModels: List<PetModel>) {
        this.pets = petModels
    }

    fun getSpots(): List<PetModel> {
        return pets
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.item_name)
        var description: TextView = view.findViewById(R.id.item_description)
        var image: ImageView = view.findViewById(R.id.item_image)
    }

}