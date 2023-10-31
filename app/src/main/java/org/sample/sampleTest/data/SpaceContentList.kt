package org.sample.sampleTest.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SpaceContentList(
    @SerializedName("result") val result: String,
    @SerializedName("page") val page: String,
    @SerializedName("data") val data: List<ContentList>

)