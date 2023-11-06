package org.sample.sampleTest.data

import com.google.gson.annotations.SerializedName


data class SpaceTTS(
    @SerializedName("result") val result: String,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: TTS
)

data class TTS(
    @SerializedName("desc") val desc: String,                   // TTS 내용
    @SerializedName("script_disp_yn") val scriptDispYn: String
)
