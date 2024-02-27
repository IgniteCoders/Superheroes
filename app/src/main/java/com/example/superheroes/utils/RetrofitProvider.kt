package com.example.superheroes.utils

import com.example.superheroes.data.SuperheroesServiceApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitProvider {
    companion object {
        fun getRetrofit(): SuperheroesServiceApi {
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(SuperheroesServiceApi::class.java)
        }
    }
}