package org.sample.sampleTest.data

import com.google.gson.annotations.SerializedName

data class Youtube(
    @SerializedName("panodetail_id") override val panodetailId: String,
    @SerializedName("youtube_video_url") val youtubeVideoUrl: String,
    @SerializedName("youtube_thum_url") val youtubeThumUrl: String
) : PanoDetail