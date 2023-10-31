package org.sample.sampleTest.data

import com.google.gson.annotations.SerializedName

data class Picture(
    @SerializedName("panodetail_id") override val panodetailId: String,
    @SerializedName("picture_url") val pictureUrl: String
) : PanoDetail
