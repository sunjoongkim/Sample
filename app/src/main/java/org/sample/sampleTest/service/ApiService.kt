package org.sample.sampleTest.service

import org.sample.sampleTest.data.Space
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {

    @GET("/api/space/spaceList.json")
    fun getSpaceList(
        @Header("x-selvers-api-v2-access-token") key: String,
        @Query("page") page: String,
        @Query("member_id") member_id: String
    ): Call<Space>


}