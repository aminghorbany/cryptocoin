package com.amin.cryptocoin.apiManager
import android.widget.Toast
import com.amin.cryptocoin.apiManager.model.CoinsData
import com.amin.cryptocoin.apiManager.model.NewsData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList

const val BASE_URL = "https://min-api.cryptocompare.com/data/"
const val BASE_URL_IMAGE = "https://www.cryptocompare.com"
const val API_KEY =
    "authorization: Apikey ff1da2f272800a47f8eaf27871bc0f9e4146b1fbfb0eeaf906f78e7b722ded63"
const val APP_NAME = "Test app"

class ApiManager {
    private val apiService: ApiService
    init {
//        val okHttpClient = OkHttpClient.Builder()
//            .addInterceptor {
//                val oldRequest = it.request()
//                val newRequest = oldRequest.newBuilder()
//                newRequest.addHeader("Authorization" , API_KEY)
//                it.proceed(newRequest.build())
//            }.build()

        val retrofit = Retrofit
            .Builder()
//            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiService = retrofit.create(ApiService::class.java)
    }

    fun getNews(aipCallBack : ApiCallback<ArrayList<Pair<String,String>>>){
        apiService.getPopularNews().enqueue(object : Callback<NewsData>{
            override fun onResponse(call: Call<NewsData>, response: Response<NewsData>) {
                val myData = response.body()!!
                val dataToSend : ArrayList<Pair<String,String>> = arrayListOf()
                myData.data.forEach {
                    dataToSend.add(Pair(it.title , it.url))
                }
                aipCallBack.onSuccess(dataToSend)
            }

            override fun onFailure(call: Call<NewsData>, t: Throwable) {
                aipCallBack.onError(t.message!!)
            }
        })
    }

    fun getTopCoinsList(apiCallback: ApiCallback<List<CoinsData.Data>>){
        apiService.getTopCoins().enqueue(object : Callback<CoinsData>{
            override fun onResponse(call: Call<CoinsData>, response: Response<CoinsData>) {
                val myData = response.body()!!
                apiCallback.onSuccess(myData.data)
            }
            override fun onFailure(call: Call<CoinsData>, t: Throwable) {
                apiCallback.onError(t.message!!)
            }
        })
    }


    interface ApiCallback<T> {
        fun onSuccess(data: T)
        fun onError(errorMessage: String)
    }

}