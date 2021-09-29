package com.example.shoppingappv2.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.shoppingappv2.adapter.ProductApiAdapter
import com.example.shoppingappv2.data.DataStoreRepository
import com.example.shoppingappv2.enumList.Category
import com.example.shoppingappv2.data.Repository
import com.example.shoppingappv2.data.database.entity.ProductListEntity
import com.example.shoppingappv2.data.model.ProductListModel
import com.example.shoppingappv2.utli.Constants.Companion.DEFAULT_SIZE_TYPE
import com.example.shoppingappv2.utli.Constants.Companion.DEFAULT_PRICE_RANGE_TYPE
import com.example.shoppingappv2.fragment.home.HomeFragment
import com.example.shoppingappv2.utli.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel @ViewModelInject constructor (
    private val repository: Repository,
    application: Application,
    private val dataStoreRepository: DataStoreRepository
): AndroidViewModel(application) {

    private var sizeType = DEFAULT_SIZE_TYPE
    private var pricceRangeType = DEFAULT_PRICE_RANGE_TYPE

    val readSizeAndPriceRange = dataStoreRepository.readSizeAndPriceRange


    fun saveSizeAndPriceRange(sizeType: String,
                              sizeTypeId: Int,
                              priceRangeType: String,
                              priceRangeTypeId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveSizeAndPriceRange(sizeType, sizeTypeId, priceRangeType, priceRangeTypeId)
        }

    /** ROOM DATABASE **/

    /**
     * List of ProductListEntity, which is converted from json into ProductListEntity, PRODUCT_LIST_TABLE
     * @return it returns the list of ProductListEntity
     * */
    val readProductsList: LiveData<List<ProductListEntity>> = repository.local.readProductList().asLiveData()


    /**
     * Insert json into PRODUCT_LIST_TABLE
     * @param productListEntity productListEntity is converted to raw json from the api
     * */
    private fun insertProductsList(productListEntity: ProductListEntity){
        viewModelScope.launch ( Dispatchers.IO ){
            repository.local.insertProductsList(productListEntity)
        }
    }



        /** RETROFIT **/
    var productsResponse: MutableLiveData<NetworkResult<ProductListModel>> = MutableLiveData()

    /**
     * main method for getting the products
     * */
    fun getProducts() = viewModelScope.launch {
        getProductsSafeCall()
    }

    /**
     * Call the mock up server or database, based on internet connection and data present in the database
     * */
    private suspend fun getProductsSafeCall() {
        productsResponse.value = NetworkResult.Loading()

        if(hasInternetConniction()){
            try {
                val response = repository.remote.getProducts()
                productsResponse.value = handleProductsResponse(response)

                val productList = productsResponse.value!!.data
                if(productList!=null){
                    offlineCacheProducts(productList)
                }

            }catch (e:Exception){
                productsResponse.value = NetworkResult.Error("${e} product not found")
            }
        }else{
            productsResponse.value = NetworkResult.Error("No Internet Connection")
        }
    }

    /**
     *
     * Insert json into PRODUCT_LIST_TABLE
     * @param productListModel productListModel
     * */
    private fun offlineCacheProducts(productListModel: ProductListModel) {
        val productEntity = ProductListEntity(productListModel)
        insertProductsList(productEntity)


    }

    private fun handleProductsResponse(response: Response<ProductListModel>): NetworkResult<ProductListModel> {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
//            response.code() == 402 -> {
//                return NetworkResult.Error("API Key Limited.")
//            }
            response.body()!!.products.isNullOrEmpty() -> {
                return NetworkResult.Error("Product not found. null")
            }
            response.isSuccessful -> {
                val products = response.body()
                return NetworkResult.Success(products!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    private fun hasInternetConniction(): Boolean{
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false

        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when{
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            // this is disabled to used on emulator
//            else -> false

            else -> true
        }
    }




    /**
     * radio button
     * */
     var radioButtonClicked = Category.All

}