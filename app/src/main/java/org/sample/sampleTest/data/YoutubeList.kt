package org.sample.sampleTest.data

import com.google.gson.annotations.SerializedName

data class YoutubeList(
    @SerializedName("panodetail_id") val panodetail_id: String,
    @SerializedName("youtube_video_url") val youtube_video_url: String,
    @SerializedName("youtube_thum_url") val youtube_thum_url: String
)