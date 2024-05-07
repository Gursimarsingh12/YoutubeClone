package com.app.youtubeclone.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.youtubeclone.data.repository.VideoRepositoryImpl
import com.app.youtubeclone.domain.model.Video
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    private val videoRepository: VideoRepositoryImpl
): ViewModel() {
    private val _videos = MutableStateFlow<List<Video>>(emptyList())
    val videos: StateFlow<List<Video>> = _videos.asStateFlow()

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    private val _searchedVideos = MutableStateFlow<List<Video>>(emptyList())
    val searchedVideo: StateFlow<List<Video>> = _searchedVideos

    fun getAllVideos() = viewModelScope.launch {
        videoRepository.getAllVideos().collect{
            videosList ->
            _videos.value = videosList.map {
                video -> video.copy(
                    id = video.id,
                    channelName = video.channelName,
                    description = video.description,
                    videoThumbnail = video.videoThumbnail,
                    title = video.title,
                    likes = video.likes,
                    channelThumbnail = video.channelThumbnail,
                    views = video.views
                )
            }
            Log.d("view", _videos.value.toString())
        }
    }

    fun searchVideos(query: String) {
        _query.value = query
        viewModelScope.launch {
            videoRepository
                .searchVideos(query).collect { videos ->
                _searchedVideos.value = videos
            }
            Log.d("videos", _videos.value.toString())
        }
    }
}