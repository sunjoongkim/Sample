package org.sample.sampleTest.data

import com.google.gson.annotations.SerializedName

data class PictureList(
    @SerializedName("panodetail_id") val panodetail_id: String,
    @SerializedName("picture_url") val picture_url: String
)
