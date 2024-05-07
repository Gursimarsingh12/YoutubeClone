package com.app.youtubeclone.domain.repository

import com.app.youtubeclone.domain.model.Video
import kotlinx.coroutines.flow.Flow

interface VideosRepository {
    suspend fun getAllVideos(): Flow<List<Video>>
    suspend fun searchVideos(query: String): Flow<List<Video>>
}