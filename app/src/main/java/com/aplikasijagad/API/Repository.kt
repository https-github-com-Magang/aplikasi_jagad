package com.aplikasijagad.API

//import com.aplikasijagad.Model.ResiBarang
import com.aplikasijagad.Model.APICoronaProv
import com.aplikasijagad.Model.ApiSPB
import com.aplikasijagad.Model.DataAPI
import retrofit2.Response

class Repository {
    suspend fun getResiRepository(): Response<List<DataAPI>> {
        return RetrofitInstance.api.getResi()
    }
}