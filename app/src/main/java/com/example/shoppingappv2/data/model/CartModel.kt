package com.example.shoppingappv2.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.shoppingappv2.utli.Constants.Companion.CART_TABLE
import kotlinx.android.parcel.Parcelize

@Entity(tableName = CART_TABLE)
@Parcelize
data class CartModel (
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var productId: Int,
    var category: String,
    var productType: String,
    var imageview: String,
    var price: Int,
    var title: String,
    var description: String,
    var quantity:Int
): Parcelable