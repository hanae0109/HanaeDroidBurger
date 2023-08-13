package fr.isen.lopez.hanaedroidburger.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface RetroInterfaceService {

        @POST("user/order")
        @Headers("Accept:application/json", "Content-Type:application/json")
        fun createOrders(@Body params: Order): Call<Order>

        @POST("listorders")
        @Headers("Accept:application/json", "Content-Type:application/json")
        fun getOrderResponses(@Body params: RequestParams): Call<OrderResponse>

}