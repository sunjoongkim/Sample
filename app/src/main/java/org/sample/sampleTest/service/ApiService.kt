package org.sample.sampleTest.service

import org.sample.sampleTest.data.Space
import org.sample.sampleTest.data.SpaceContent
import org.sample.sampleTest.data.SpaceShow
import org.sample.sampleTest.data.SpaceTTS
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {

    // 공간 리스트
    @GET("/api/space/spaceList.json")
    fun getSpaceList(
        @Header("x-selvers-api-v2-access-token") key: String,
        @Query("page") page: String,
        @Query("member_id") memberId: String
    ): Call<Space>

    // 컨텐츠 리스트
    @GET("/api/space/spaceContentList.json")
    fun getSpaceContentList(
        @Header("x-selvers-api-v2-access-token") key: String,
        @Query("space_id") spaceId: String,
        @Query("member_id") memberId: String
    ) : Call<SpaceContent>

    // 전시 조회
    @GET("/api/content/getShow.json")
    fun getShow(
        @Header("x-selvers-api-v2-access-token") key: String,
        @Query("show_id") showID: String
    ) : Call<SpaceShow>

    // TTS 조회
    @GET("/api/content/getTts.json")
    fun getTTS(
        @Header("x-selvers-api-v2-access-token") key: String,
        @Query("tts_id") ttsID: String
    ) : Call<SpaceTTS>


}