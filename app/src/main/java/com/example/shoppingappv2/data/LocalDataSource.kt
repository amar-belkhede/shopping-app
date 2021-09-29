package com.example.shoppingappv2.data

import androidx.lifecycle.LiveData
import com.example.shoppingappv2.data.database.dao.ProductListDaoApi
import com.example.shoppingappv2.data.database.entity.ProductListEntity
import com.example.shoppingappv2.data.model.CartModel
import com.example.shoppingappv2.data.model.ProductModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
/**class for getting data from ROOM database */
class LocalDataSource @Inject constructor(
    private val productListDaoApi: ProductListDaoApi
) {

    /**
     * List of ProductListEntity, which is converted from json into ProductListEntity, PRODUCT_LIST_TABLE
     * @return it returns the list of ProductListEntity
     * */
    fun readProductList(): Flow<List<ProductListEntity>> {
        return productListDaoApi.readProductsList()
    }

    /**
     * Insert json into PRODUCT_LIST_TABLE
     * @param productListEntity productListEntity is converted to raw json from the api
     * */
    suspend fun insertProductsList(productListEntity: ProductListEntity){
        productListDaoApi.insertListProducts(productListEntity)
    }



    /** ProductModel **/

    /**
     * @return LiveData as a List of ProductModel from PRODUCT_TABLE
     * */
    fun readProduct(): LiveData<List<ProductModel>> {
        return productListDaoApi.readProducts()
    }

    /**
     * Insert ProductModel into PRODUCT_TABLE
     * @param productModel Model class for product
     * */
    suspend fun insertProducts(productModel: ProductModel){
            productListDaoApi.insertProducts(productModel)
    }

    /**
     * @param id product id
     * @return LiveData of ProductModel from PRODUCT_TABLE
     * */
    fun getProductById(id: Int):LiveData<ProductModel>{
        return productListDaoApi.getProductById(id)
    }

    /**
     * @return ProductModel from PRODUCT_TABLE
     *
     * @param id product id
     * */
    fun getProductByIdSimple(id: Int):ProductModel{
        return productListDaoApi.getProductByIdSimple(id)
    }

    /**
     * Updating ProductModel in PRODUCT_TABLE
     *
     * @param productModel Model class for product
     * */
    suspend fun updateProduct(productModel: ProductModel){
        productListDaoApi.updateProd(productModel)
    }

    /**
     * @return LiveData as a List of ProductModel from PRODUCT_TABLE for ELECTRONIC category
     * */
    fun getElectronic(): LiveData<List<ProductModel>> {
        return productListDaoApi.getElectronic()
    }

    /**
     * @return LiveData as a List of ProductModel from PRODUCT_TABLE for CLOTHING category
     * */
    fun getClothing(): LiveData<List<ProductModel>> {
        return productListDaoApi.getClothing()
    }

    /**
     * @return Get boolean, if the product is available or not
     *
     * @param id product id
     * */
    fun inCart(id: Int):LiveData<Boolean>{
        return productListDaoApi.inCart(id)
    }


    /** Cart **/

    /**
     * @return LiveData as a List of CartModel from CART_TABLE
     * */
    val getAllProdFromCart: LiveData<List<CartModel>> = productListDaoApi.getAllProdFromCart()

    /**
     * @return LiveData of total cost of the products from CART_TABLE
     * */
    val getTotalPrice: LiveData<Int> = productListDaoApi.getTotalPrice()

    /**
     * @return LiveData of total quantity of the products from CART_TABLE
     * */
    val getTotalQuantity: LiveData<Int> = productListDaoApi.getTotalQuantity()

    fun getProdFromCart(productType: String):LiveData<List<CartModel>>{
        return productListDaoApi.getProdFromCart(productType)
    }

    /**
     * Inserting CartModel to the CART_TABLE
     *
     * @param cartModel model for cart items
     * */
    suspend fun insertProdToCart(cartModel: CartModel){
        productListDaoApi.insertProdToCart(cartModel)
    }

    /**
     * Updating CartModel from CART_TABLE
     *
     * @param cartModel model for cart items
     * */
    suspend fun updateProdToCart(cartModel: CartModel){
        productListDaoApi.updateProdToCart(cartModel)
    }

    /**
     * Deleting CartModel from CART_TABLE
     *
     * @param cartModel model for cart items
     * */
    suspend fun deleteProdFromCart(cartModel: CartModel){
        productListDaoApi.deleteProdFromCart(cartModel)
    }

}