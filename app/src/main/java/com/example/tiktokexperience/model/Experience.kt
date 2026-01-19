package com.example.tiktokexperience.model

data class Experience(
    val id: String,
    val title: String,
    val imageUrl: String,
    val userAvatar: String,
    val userName: String,
    val likes: Int,
    var isLiked: Boolean = false,
    val contentHeight: Int = (200..400).random() // 模拟不同高度
)