package org.sample.sampleTest.data

import com.google.gson.annotations.SerializedName

data class SpaceContent(
    @SerializedName("result") val result: String,
    @SerializedName("page") val page: String,
    @SerializedName("data") val data: Content

)