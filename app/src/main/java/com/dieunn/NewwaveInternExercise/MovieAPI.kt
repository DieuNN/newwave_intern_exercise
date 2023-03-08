package com.dieunn.NewwaveInternExercise

import com.dieunn.NewwaveInternExercise.models.APIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPI {

    @GET("/3/discover/movie/")
    suspend fun getResponse(@Query("page") page: Int) : APIResponse
}