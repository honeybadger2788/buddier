package com.example.buddier.ui.homeActivity

import androidx.recyclerview.widget.DiffUtil
import com.example.buddier.ui.homeActivity.model.PetModel

class SpotDiffCallback(
    private val old: List<PetModel>,
    private val new: List<PetModel>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return old.size
    }

    override fun getNewListSize(): Int {
        return new.size
    }

    override fun areItemsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return old[oldPosition].id == new[newPosition].id
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return old[oldPosition] == new[newPosition]
    }

}