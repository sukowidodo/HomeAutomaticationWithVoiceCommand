package com.sukowidodo.controlsuara.retrofit


import com.sukowidodo.controlsuara.model.Result
import io.reactivex.Observable
import retrofit2.http.*

interface RetroInterface{
    @GET("args")
    fun Action(
            @Query("name")name:String,
            @Query("value")value:Int
    ):Observable<Result>
}
