package com.example.shoppingappv2.viewModel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.shoppingappv2.data.Repository
import com.example.shoppingappv2.data.model.CartModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//ViewModel: Helps to create, store and retrieve data and communicates with other components belonging to the same lifecycle.
//Lifecycle Owner: It’s an interface implemented by activity and fragment, to observe changes to the lifecycle of owners.
//LiveData: Allows observing changes in data across diff components in the same lifecycle.


class CartViewModel @ViewModelInject constructor (
    private val repository: Repository,
    application: Application,

): AndroidViewModel(application){

    /**
     * @return LiveData as a List of CartModel from CART_TABLE
     * */
    val getAllProdFromCart: LiveData<List<CartModel>> = repository.local.getAllProdFromCart

    /**
     * @return LiveData of total cost of the products from CART_TABLE
     * */
    val getTotalPrice: LiveData<Int> = repository.local.getTotalPrice

    /**
     * @return LiveData of total quantity of the products from CART_TABLE
     * */
    val getTotalQuantity: LiveData<Int> = repository.local.getTotalQuantity

    // never used
    fun getProdFromCart(productType: String) :LiveData<List<CartModel>>{

        return  repository.local.getProdFromCart(productType)

    }

    /**
     * Inserting CartModel to the CART_TABLE
     *
     * @param cartModel model for cart items
     * */
    fun insertProdToCart(cartModel: CartModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertProdToCart(cartModel)
        }
    }

    /**
     * Updating CartModel from CART_TABLE
     *
     * @param cartModel model for cart items
     * */
    fun updateProdToCart(cartModel: CartModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.updateProdToCart(cartModel)
        }
    }

    /**
     * Deleting CartModel from CART_TABLE
     *
     * @param cartModel model for cart items
     * */
    fun deleteProdFromCart(cartModel: CartModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteProdFromCart(cartModel)
        }
    }
}