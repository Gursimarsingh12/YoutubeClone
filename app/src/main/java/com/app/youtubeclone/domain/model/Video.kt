package com.app.youtubeclone.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Video(
    val id: Int = 0,
    val channelThumbnail: String = "",
    val channelName: String = "",
    val title: String = "",
    val description: String = "",
    val likes: String = "",
    val videoThumbnail: String = "",
    val views: String = ""
)
