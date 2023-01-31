package com.amin.cryptocoin.apiManager


import com.amin.cryptocoin.apiManager.model.CoinsData
import com.amin.cryptocoin.apiManager.model.NewsData
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @Headers(API_KEY)
    @GET("v2/news/")
    fun getPopularNews(
            @Query("SortOrder") sortOrder : String = "popular"
    ) : Call<NewsData>

    @Headers(API_KEY)
    @GET("top/totalvolfull")
    fun getTopCoins(
        @Query("tsym") toSymbol : String = "USD" ,
        @Query("limit") limit : Int = 30
    ) : Call<CoinsData>

}