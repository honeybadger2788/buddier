package com.example.buddier.ui.homeActivity.model

data class PetModel(
    val id: Long = counter++,
    val age: Int,
    val name: String,
    val description: String,
    val url: String
) {
    companion object {
        private var counter = 0L
    }
}