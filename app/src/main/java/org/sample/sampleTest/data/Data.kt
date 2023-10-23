package org.sample.sampleTest.data


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Data(
    @SerializedName("space_id") val space_id: String,
    @SerializedName("space_name") val space_name: String,
    @SerializedName("space_mainimage") val space_mainimage: String,
    @SerializedName("space_thum") val space_thum: String,
    @SerializedName("member_id") val member_id: String,
    @SerializedName("member_name") val member_name: String,
    @SerializedName("member_image_uri") val member_image_uri: String,
    @SerializedName("like_count") val like_count: String,
    @SerializedName("like_yn") val like_yn: String,
    @SerializedName("created") val created: String,
    @SerializedName("updated") val updated: String,
    @SerializedName("space_bge_cd") val space_bge_cd: String,
    @SerializedName("space_bgm_cd") val space_bgm_cd: String,
    @SerializedName("space_url") val space_url: String,
    @SerializedName("unique_name") val unique_name: String,
    @SerializedName("deco_url") val deco_url: String,
    @SerializedName("short_url") val short_url: String,
    @SerializedName("space_desc") val space_desc: String,
    @SerializedName("space_hashtag") val space_hashtag: String,
    @SerializedName("address") val address: String,
    @SerializedName("lat") val lat: String,
    @SerializedName("lng") val lng: String,
    @SerializedName("top_fix_yn") val top_fix_yn: String,
    @SerializedName("open_range") val open_range: String,
    @SerializedName("comment_count") val comment_count: String,
    @SerializedName("space_panoimage") val space_panoimage: String

): Serializable {

}