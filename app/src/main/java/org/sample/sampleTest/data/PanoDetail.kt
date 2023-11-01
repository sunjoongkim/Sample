package org.sample.sampleTest.data

import com.google.gson.annotations.SerializedName

interface PanoDetail {
    val panodetailId: String
}

data class Youtube(
    @SerializedName("panodetail_id") override val panodetailId: String,
    @SerializedName("youtube_video_url") val youtubeVideoUrl: String,
    @SerializedName("youtube_thum_url") val youtubeThumUrl: String
) : PanoDetail

data class Picture(
    @SerializedName("panodetail_id") override val panodetailId: String,
    @SerializedName("picture_url") val pictureUrl: String
) : PanoDetail
