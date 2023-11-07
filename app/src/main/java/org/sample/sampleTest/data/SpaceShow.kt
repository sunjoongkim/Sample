package org.sample.sampleTest.data

import com.google.gson.annotations.SerializedName

// 전시
data class SpaceShow(
    @SerializedName("result") val result: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: Show
)

data class Show(
    @SerializedName("title") val result: String,        // 제목
    @SerializedName("desc") val message: String,        // 내용
    @SerializedName("link_url") val linkUrl: String,    // URL
    @SerializedName("originImgList") val originImgList: List<OriginImgList>,
    @SerializedName("thumImgList") val thumImgList: List<ThumImgList>
)
// 원본
data class OriginImgList(
    @SerializedName("seq") val seq: String,
    @SerializedName("img_url") val imgUrl: String
)

//썸네일
data class ThumImgList(
    @SerializedName("seq") val seq: String,
    @SerializedName("img_url") val imgUrl: String
)