package com.example.shoppingappv2.data.model


import com.google.gson.annotations.SerializedName

data class ProductListModel(
    @SerializedName("products")
    val products: List<ProductModel>
)