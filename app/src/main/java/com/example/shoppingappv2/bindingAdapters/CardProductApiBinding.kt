package com.example.shoppingappv2.bindingAdapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import com.example.shoppingappv2.R

/**
 * card_product_api binder
 * */
class CardProductApiBinding {

    companion object{

        @BindingAdapter("setPrice")
        @JvmStatic
        fun setPrice(textView: TextView, price:Int){
            textView.text = price.toString()
        }



        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, imageUrl:String){
            imageView.load(imageUrl){
                crossfade(600)
                error(R.drawable.ic_error_outline)
            }
        }

    }
}