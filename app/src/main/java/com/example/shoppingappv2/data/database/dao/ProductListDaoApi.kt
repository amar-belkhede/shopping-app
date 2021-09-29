package com.example.shoppingappv2.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.shoppingappv2.data.database.entity.ProductListEntity
import com.example.shoppingappv2.data.model.CartModel
import com.example.shoppingappv2.data.model.ProductModel
import kotlinx.coroutines.flow.Flow

/**
 * All the DAO's
 * */
@Dao
interface ProductListDaoApi {

    /** ProductListEntity **/

    /**
     * Insert json into PRODUCT_LIST_TABLE
     *
     * @param productListEntity productListEntity is converted to json response from the ProductApi
     * */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListProducts(productListEntity: ProductListEntity)


    /**
     * List of ProductListEntity, which is converted from json into ProductListEntity, PRODUCT_LIST_TABLE
     * */
    @Query("SELECT * FROM PRODUCT_LIST_TABLE_API ORDER BY id ASC")
    fun readProductsList(): Flow<List<ProductListEntity>>



    /** ProductModel **/

    /**
     * Insert ProductModel into PRODUCT_TABLE
     * @param productModel Model class for product
     * */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(productModel: ProductModel)

    /**
     * @return LiveData as a List of ProductModel from PRODUCT_TABLE
     * */
    @Query("SELECT * FROM PRODUCT_TABLE_API ORDER BY id ASC")
    fun readProducts(): LiveData<List<ProductModel>>

    /**
     * Updating ProductModel in PRODUCT_TABLE
     *
     * @param productModel Model class for product
     * */
    @Update
    suspend fun updateProd(productModel: ProductModel)

    /**
     * @param id product id
     * @return LiveData of ProductModel from PRODUCT_TABLE
     * */
    @Query("SELECT * FROM PRODUCT_TABLE_API WHERE id = :id")
    fun getProductById(id: Int):LiveData<ProductModel>

    /**
     * @return ProductModel from PRODUCT_TABLE
     *
     * @param id product id
     * */
    @Query("SELECT * FROM PRODUCT_TABLE_API WHERE id = :id")
    fun getProductByIdSimple(id: Int):ProductModel


    /**
     * @return LiveData as a List of ProductModel from PRODUCT_TABLE for ELECTRONIC category
     * */
    @Query("SELECT * FROM PRODUCT_TABLE_API WHERE category = 'ELECTRONIC'")
    fun getElectronic(): LiveData<List<ProductModel>>

    /**
     * @return LiveData as a List of ProductModel from PRODUCT_TABLE for CLOTHING category
     * */
    @Query("SELECT * FROM PRODUCT_TABLE_API WHERE category = 'CLOTHING'")
    fun getClothing(): LiveData<List<ProductModel>>

    /**
     * @return Get boolean, if the product is available or not
     *
     * @param id product id
     * */
    @Query("SELECT inCart FROM PRODUCT_TABLE_API WHERE id = :id")
    fun inCart(id: Int):LiveData<Boolean>


    /** cart dao **/

    /**
     * @return LiveData as a List of CartModel from CART_TABLE
     * */
    @Query("SELECT * FROM CART_TABLE ORDER BY id ASC")
    fun getAllProdFromCart(): LiveData<List<CartModel>>

    /**
     * @return LiveData of total cost of the products from CART_TABLE
     * */
    @Query("SELECT (SUM(price) * SUM(quantity)) FROM CART_TABLE")
    fun getTotalPrice():LiveData<Int>


    /**
     * NEVER USED
     * */
    @Query("SELECT * FROM CART_TABLE WHERE productType = :productType")
    fun getProdFromCart(productType: String):LiveData<List<CartModel>>


    /**
     * @return LiveData of total quantity of the products from CART_TABLE
     * */
    @Query("SELECT SUM(quantity) FROM CART_TABLE")
    fun getTotalQuantity():LiveData<Int>

    /**
     * Inserting CartModel to the CART_TABLE
     *
     * @param cartModel model for cart items
     * */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProdToCart(cartModel: CartModel)


    /**
     * Updating CartModel from CART_TABLE
     *
     * @param cartModel model for cart items
     * */
    @Update
    suspend fun updateProdToCart(cartModel: CartModel)

    /**
     * Deleting CartModel from CART_TABLE
     *
     * @param cartModel model for cart items
     * */
    @Delete
    suspend fun deleteProdFromCart(cartModel: CartModel)
}