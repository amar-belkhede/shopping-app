package com.example.shoppingappv2.data.model


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.shoppingappv2.utli.Constants
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = Constants.PRODUCT_TABLE)
@Parcelize
data class ProductModel(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    val id: Int,
    @SerializedName("category")
    val category: String,
    @SerializedName("productType")
    val productType: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("imageview")
    val imageview: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("inCart")
    var inCart: Boolean
): Parcelable