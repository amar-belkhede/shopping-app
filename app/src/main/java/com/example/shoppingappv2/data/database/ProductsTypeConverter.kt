package com.example.shoppingappv2.data.database

import androidx.room.TypeConverter
//import com.example.shoppingappv2.enumList.Category
//import com.example.shoppingappv2.enumList.ProductType
import com.example.shoppingappv2.data.model.ProductListModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProductsTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun productListToString(productListModel: ProductListModel): String{
        return gson.toJson(productListModel)
    }

    @TypeConverter
    fun stringToProductList(data: String): ProductListModel{
        val listType = object : TypeToken<ProductListModel>() {}.type
        return gson.fromJson(data, listType)
    }


}