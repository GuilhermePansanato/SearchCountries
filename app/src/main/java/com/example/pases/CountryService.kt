package com.example.pases

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CountryService {



        @GET("all")
        fun getAllCountries(): Call<List<Country>>

 }
