package org.sample.sampleTest.data

import com.google.gson.annotations.SerializedName

data class  ContentList(
    @SerializedName("pictureList") val pictureList: List<PictureList>,
    @SerializedName("youtubeList") val youtubeList: List<YoutubeList>
)