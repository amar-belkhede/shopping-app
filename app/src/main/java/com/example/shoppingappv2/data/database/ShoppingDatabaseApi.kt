package com.example.shoppingappv2.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.shoppingappv2.data.database.dao.ProductListDaoApi
import com.example.shoppingappv2.data.database.entity.ProductListEntity
import com.example.shoppingappv2.data.model.CartModel
import com.example.shoppingappv2.data.model.ProductModel


@Database(
    entities = [ProductListEntity::class, ProductModel::class, CartModel::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ProductsTypeConverter::class)
abstract class ShoppingDatabaseApi: RoomDatabase() {

    /**
     * Dao abstract for ShoppingDatabaseApi
     * */
    abstract fun productListDaoApi(): ProductListDaoApi

}