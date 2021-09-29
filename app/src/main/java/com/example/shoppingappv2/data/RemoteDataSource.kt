package com.example.shoppingappv2.data

import com.example.shoppingappv2.api.ProductApi
import com.example.shoppingappv2.data.model.ProductListModel
import retrofit2.Response
import javax.inject.Inject


class RemoteDataSource @Inject constructor(
    private val productApi: ProductApi
) {

    suspend fun getProducts(): Response<ProductListModel>{
        return productApi.getProducts()
    }

}