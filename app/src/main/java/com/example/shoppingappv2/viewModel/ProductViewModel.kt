package com.example.shoppingappv2.viewModel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.shoppingappv2.data.Repository
import com.example.shoppingappv2.data.model.ProductModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//ViewModel: Helps to create, store and retrieve data and communicates with other components belonging to the same lifecycle.
//Lifecycle Owner: It’s an interface implemented by activity and fragment, to observe changes to the lifecycle of owners.
//LiveData: Allows observing changes in data across diff components in the same lifecycle.


class ProductViewModel @ViewModelInject constructor (
    private val repository: Repository,
    application: Application
): AndroidViewModel(application){

    /**
     * @return LiveData as a List of ProductModel from PRODUCT_TABLE
     * */
    val readProducts: LiveData<List<ProductModel>> = repository.local.readProduct()

    /**
     * @return LiveData as a List of ProductModel from PRODUCT_TABLE for ELECTRONIC category
     * */
    val getElectronic: LiveData<List<ProductModel>> = repository.local.getElectronic()


    /**
     * @return LiveData as a List of ProductModel from PRODUCT_TABLE for CLOTHING category
     * */
    val getClothing: LiveData<List<ProductModel>> = repository.local.getClothing()


    /**
     * @param id product id
     * @return LiveData of ProductModel from PRODUCT_TABLE
     * */
    fun getProductById(id: Int):LiveData<ProductModel> {
        return repository.local.getProductById(id)
    }

    fun getProductByIdSimple(id: Int):ProductModel {
        return repository.local.getProductByIdSimple(id)
    }

    fun insertProducts(productData: ProductModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertProducts(productData)
        }
    }

    /**
     * Updating ProductModel in PRODUCT_TABLE
     *
     * @param productModel Model class for product
     * */
    fun updateProduct(productModel: ProductModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.updateProduct(productModel)
        }
    }

    /**
     * Insert ProductModels into PRODUCT_TABLE
     * @param productModelList list of productModelList
     * */
    fun insertProductsFromList(productModelList: List<ProductModel>){
        for(p in productModelList){
            val newProd = ProductModel(
                p.id,
                p.category,
                p.productType,
                p.title,
                p.imageview,
                p.description,
                p.price,
                p.inCart
            )

            viewModelScope.launch ( Dispatchers.IO ){
                repository.local.insertProducts(newProd)
            }
        }
    }

    /**
     * @return Get boolean, if the product is available or not
     *
     * @param id product id
     * */
    fun inCart(id: Int):LiveData<Boolean> {
        return repository.local.inCart(id)
    }

}