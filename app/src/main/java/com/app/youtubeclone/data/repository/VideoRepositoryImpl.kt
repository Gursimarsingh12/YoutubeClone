package com.app.youtubeclone.data.repository

import android.util.Log
import com.app.youtubeclone.domain.model.Video
import com.app.youtubeclone.domain.repository.VideosRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.filter.TextSearchType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(
    private val supabaseClient: SupabaseClient
): VideosRepository {
    override suspend fun getAllVideos(): Flow<List<Video>> = withContext(Dispatchers.IO) {
        return@withContext flow {
            val response = supabaseClient.from("videos").select().decodeList<Video>()
            if (response.isNotEmpty()) {
                Log.d("new", response.toString())
                emit(response)
            }
        }
    }
    override suspend fun searchVideos(query: String): Flow<List<Video>> = withContext(Dispatchers.IO){
        return@withContext flow {
            val response = supabaseClient.from("videos").select(Columns.ALL, request = {
                filter {
                    or{
                        textSearch(column = "title", textSearchType = TextSearchType.PLAINTO, query = query)
                        textSearch(column = "description", textSearchType = TextSearchType.PLAINTO, query = query)
                        textSearch(column = "channelName", textSearchType = TextSearchType.PLAINTO, query = query)
                    }
                }
            }).decodeList<Video>()
            emit(response.map {
                video ->
                video.copy(
                    id = video.id,
                    channelName = video.channelName,
                    description = video.description,
                    videoThumbnail = video.videoThumbnail,
                    title = video.title,
                    likes = video.likes,
                    channelThumbnail = video.channelThumbnail,
                    views = video.views
                )
            })
            Log.d("tag", emit(response.map {
                    video ->
                video.copy(
                    id = video.id,
                    channelName = video.channelName,
                    description = video.description,
                    videoThumbnail = video.videoThumbnail,
                    title = video.title,
                    likes = video.likes,
                    channelThumbnail = video.channelThumbnail,
                    views = video.views
                )
            }).toString())
        }
    }

}
