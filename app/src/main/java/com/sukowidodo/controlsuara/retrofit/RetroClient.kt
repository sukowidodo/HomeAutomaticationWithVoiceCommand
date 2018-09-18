package com.sukowidodo.controlsuara.retrofit

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroClient{
    companion object {
        val ROOT_URL : String = "http://192.168.8.109/"
        val gson : Gson = GsonBuilder().create()
        fun getRetrofitInstance() : Retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson)).baseUrl(ROOT_URL).build()
    }
}