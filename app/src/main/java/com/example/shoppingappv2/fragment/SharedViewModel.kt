package com.example.shoppingappv2.fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.shoppingappv2.data.model.CartModel

class SharedViewModel(application: Application): AndroidViewModel(application) {

    /**
     * Boolean MutableLiveData, which keeps the boolean of if the data is present or not
     * */
    val emptyDataBase: MutableLiveData<Boolean> = MutableLiveData(true)

    /**
     * Assigns the boolean value to the emptyDataBase
     *
     * @param cartData List of CartModel
     * */
    fun checkIfDatabaseEmpty(cartData: List<CartModel>){
        emptyDataBase.value = cartData.isEmpty()
    }



}