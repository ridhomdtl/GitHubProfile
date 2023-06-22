package com.example.githubprofile.apiservice

import com.example.githubprofile.usermodeldatabase.UserDataResponse
import com.example.githubprofile.usermodeldatabase.UserDetail
import com.example.githubprofile.usermodeldatabase.UserInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearch(@Query("q") query: String): Call<UserDataResponse>

    @GET("users/{userlogin}")
    @Headers("Authorization: token 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")
    fun getDetail(@Path("userlogin") userlogin: String): Call<UserDetail>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(@Path("username") username: String): Call<ArrayList<UserInfo>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(@Path("username") username: String): Call<ArrayList<UserInfo>>
}