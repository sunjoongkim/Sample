package org.sample.sampleTest.data

import com.google.gson.annotations.SerializedName

data class Space(
    @SerializedName("result") val result: String,
    @SerializedName("message") val message: String,
    @SerializedName("page") val page: String,
    @SerializedName("total_page") val total_page: String,
    @SerializedName("total_count") val total_count: String,
    @SerializedName("data") val data: List<Data>
)