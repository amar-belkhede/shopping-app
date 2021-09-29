package com.example.shoppingappv2.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.shoppingappv2.data.model.ProductListModel
import com.example.shoppingappv2.utli.Constants.Companion.PRODUCT_LIST_TABLE

@Entity(tableName = PRODUCT_LIST_TABLE)
class ProductListEntity(
    var productListModel: ProductListModel
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}