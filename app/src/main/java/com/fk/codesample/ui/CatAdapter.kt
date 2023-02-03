package com.fk.codesample.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fk.codesample.R
import com.fk.codesample.entities.Cat

class CatListAdapter :
    ListAdapter<Cat, CatListAdapter.CatViewHolder>(CatDiffCallback) {

    class CatViewHolder(private val view: View) :
        RecyclerView.ViewHolder(view) {
        private val image: ImageView = view.findViewById(R.id.image)
        private val breedName: TextView = view.findViewById(R.id.breedName)
        private val temperament: TextView = view.findViewById(R.id.temperament)
        private val origin: TextView = view.findViewById(R.id.origin)

        fun bind(cat: Cat) {
            breedName.text = cat.breedName
            temperament.text = cat.breedTemperament
            origin.text = cat.origin
            Glide.with(view)
                .load(cat.imageUrl)
                .fitCenter()
                .into(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cat_list_item, parent, false)
        return CatViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: CatViewHolder, position: Int) {
        val cat = getItem(position)
        viewHolder.bind(cat)
    }
}

object CatDiffCallback : DiffUtil.ItemCallback<Cat>() {
    override fun areItemsTheSame(oldItem: Cat, newItem: Cat): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Cat, newItem: Cat): Boolean {
        return oldItem == newItem
    }
}
