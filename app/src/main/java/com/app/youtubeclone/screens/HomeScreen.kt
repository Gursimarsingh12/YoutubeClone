package com.app.youtubeclone.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.app.youtubeclone.R
import com.app.youtubeclone.presentation.VideoViewModel

@Composable
fun HomeScreen(videoViewModel: VideoViewModel = hiltViewModel()) {
    val videos = videoViewModel.videos.collectAsState().value
    val query = videoViewModel.query.collectAsState().value
    val searchedVideos = videoViewModel.searchedVideo.collectAsState().value
    val videosToShow = if (query.isEmpty()) {
        videoViewModel.getAllVideos()
        videos
    } else {
        searchedVideos
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.youtube),
                contentDescription = "",
                modifier = Modifier.padding(start = 8.dp, top = 20.dp)
            )
            Spacer(modifier = Modifier.padding(5.dp))
            OutlinedTextField(
                value = query,
                onValueChange = {
                    videoViewModel.searchVideos(it)
                },
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "Search videos!!",
                        color = Color.DarkGray,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                },
                colors = TextFieldDefaults.colors(
                    disabledContainerColor = Color.White,
                    focusedContainerColor = Color.White
                ),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "",
                        modifier = Modifier.padding(end = 10.dp)
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(30.dp)
            )
            Spacer(modifier = Modifier.padding(10.dp))
            if (videosToShow.isEmpty() && query.isNotEmpty()) {
                Text(
                    text = "No videos found for '$query'",
                    color = Color.Gray,
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            } else {
                LazyColumn {
                    items(videosToShow) { video ->
                        VideoCard(
                            channelThumbnail = video.channelThumbnail,
                            channelName = video.channelName,
                            title = video.title,
                            description = video.description,
                            likes = video.likes,
                            videoThumbnail = video.videoThumbnail,
                            views = video.views
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun VideoCard(
    channelThumbnail: String,
    channelName: String,
    title: String,
    description: String,
    likes: String,
    videoThumbnail: String,
    views: String
) {
    Column(
        modifier = Modifier
            .padding(start = 3.dp, end = 3.dp)
            .fillMaxWidth()
    ) {
        OutlinedCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(197.dp)
        ) {
            AsyncImage(
                model = videoThumbnail,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentScale = ContentScale.FillBounds
            )
        }
        Spacer(modifier = Modifier.padding(5.dp))
        Row(
            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = channelThumbnail,
                contentDescription = "",
                modifier = Modifier
                    .clip(CircleShape)
                    .width(36.dp)
                    .height(36.dp),
                contentScale = ContentScale.FillBounds
            )
            Text(
                text = title,
                modifier = Modifier.padding(start = 6.dp),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight(400),
                    color = Color(0xFFFFFFFF)
                )
            )
        }
        Spacer(modifier = Modifier.padding(3.dp))
        Row(
            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .width(36.dp)
            )
            Text(
                text = channelName,
                modifier = Modifier.padding(start = 6.dp),
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF979797),
                )
            )
            VerticalDivider(
                modifier = Modifier
                    .height(15.dp)
                    .padding(start = 5.dp, end = 5.dp)
            )
            Text(
                text = "$views views",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF979797),
                )
            )
            VerticalDivider(
                modifier = Modifier
                    .height(15.dp)
                    .padding(start = 5.dp, end = 5.dp)
            )
            Text(
                text = "$likes ❤\uFE0F",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF979797),
                )
            )
        }
        Spacer(modifier = Modifier.padding(3.dp))
        Row(
            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .width(36.dp)
            )
            Text(
                text = description,
                modifier = Modifier.padding(start = 6.dp),
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF979797)
                )
            )
        }
        Spacer(modifier = Modifier.padding(10.dp))
    }
}