package com.example.buddier.ui.buddy

data class Spot(
    val id: Long = counter++,
    val name: String,
    val description: String,
    val url: String
) {
    companion object {
        private var counter = 0L
    }
}