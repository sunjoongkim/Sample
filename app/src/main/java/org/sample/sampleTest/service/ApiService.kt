package org.sample.sampleTest.service

import org.sample.sampleTest.data.Space
import org.sample.sampleTest.data.SpaceContentList
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
        @Query("member_id") member_id: String
    ): Call<Space>

    // 컨텐츠 리스트
    @GET("/api/space/spaceContentList.json")
    fun getSpaceContentList(
        @Header("x-selvers-api-v2-access-token") key: String,
        @Query("space_id") page: String,
        @Query("member_id") member_id: String
    ) : Call<SpaceContentList>

}