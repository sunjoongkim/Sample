package org.sample.sampleTest.data

import com.google.gson.annotations.SerializedName

data class Content(
    @SerializedName("pictureList") val pictureList: List<Picture>,
    @SerializedName("youtubeList") val youtubeList: List<Youtube>
)