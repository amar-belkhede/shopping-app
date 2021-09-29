package com.example.shoppingappv2.api

import com.example.shoppingappv2.data.model.ProductListModel
import retrofit2.Response
import retrofit2.http.GET


/**
 * Mock server for shopping app
 * */
interface ProductApi {


    /**
     * GET request method - to get the products list
     * */
    @GET("/all")
    suspend fun getProducts(): Response<ProductListModel>
}