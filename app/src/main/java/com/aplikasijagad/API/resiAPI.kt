package com.aplikasijagad.API

//import com.aplikasijagad.Model.ResiBarang
import com.aplikasijagad.Model.APICoronaProv
import com.aplikasijagad.Model.ApiSPB
import com.aplikasijagad.Model.DataAPI
import retrofit2.Response
import retrofit2.http.GET

interface  resiAPI {

    @GET("spb")
    suspend fun getResi(): Response<List<DataAPI>>

}