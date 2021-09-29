package com.example.shoppingappv2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.shoppingappv2.R
import com.example.shoppingappv2.data.model.CartModel
import com.example.shoppingappv2.viewModel.CartViewModel
import com.example.shoppingappv2.viewModel.ProductViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.cart_item.view.*


/**
 * It is for the CartFragment for the recycleview
 *
 * @param mCartViewModel CartViewModel from the CartFragment
 * @param mProductViewModel ProductViewModel from the CartFragment
 * @param viewLifecycleOwner LifecycleOwner from the CartFragment
 * @param context Context from the CartFragment
 * */
class CartAdapter(
    val mCartViewModel: CartViewModel,
    val mProductViewModel: ProductViewModel,
    val viewLifecycleOwner: LifecycleOwner,
    val context: Context
) : RecyclerView.Adapter<CartAdapter.MyViewHolder>() {

    /**
     * list of cartmodel
     * */
    var dataList = emptyList<CartModel>()

    lateinit var snackBarLimitReached: Snackbar


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.cartProdImg.load(dataList[position].imageview){
            crossfade(600)
            error(R.drawable.ic_error_outline)
        }
        holder.itemView.cartProdName.text = dataList[position].title
        holder.itemView.cartProdDescription.text = dataList[position].description
        holder.itemView.cartProdPrice.text = dataList[position].price.toString()
        holder.itemView.cartProdQuant.setText(dataList[position].quantity.toString())


        /**
         * delete the product from the cart
         **/
        holder.itemView.cartProdDel.setOnClickListener {

            // when item in cart is deleted, productItem is updated to false in (incart) value
            val currentId = dataList[position].productId

            mProductViewModel.getProductById(currentId).observe(viewLifecycleOwner,  {
                it.inCart = false
                mProductViewModel.updateProduct(it)
//                Toast.makeText(context,"${it.title} updated", Toast.LENGTH_SHORT).show()
            })

            // then delete the item from cart
            mCartViewModel.deleteProdFromCart(dataList[position])

        }

        /**
         * set the quantity in the itemView in the card
         **/
        holder.itemView.cartProdQuant.setOnClickListener {
            val quantity = it.cartProdQuant.text.toString()
            if( quantity!="" && quantity.toInt()<50 && quantity.toInt()>1){
                dataList[position].quantity = quantity.toInt()
                mCartViewModel.updateProdToCart(dataList[position])
            }


        }

        /**
         * increment the quantity
         **/

        holder.itemView.cartQuantInc.setOnClickListener {
            if(dataList[position].quantity<50){
                dataList[position].quantity +=1
                mCartViewModel.updateProdToCart(dataList[position])
            }else{
                Toast.makeText(context,"Limit reached!!", Toast.LENGTH_SHORT).show()
            }

        }

        /**
         * decrement the quantity
         **/
        holder.itemView.cartQuantDec.setOnClickListener {
            if(dataList[position].quantity>1){
                dataList[position].quantity -=1
                mCartViewModel.updateProdToCart(dataList[position])
            }

        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


    /**
     * updating the data in the recycleview
     * @param cartData list of CartModel
     * */
    fun setData(cartData: List<CartModel>){
        this.dataList = cartData
        // compare this with diff util
        notifyDataSetChanged()
    }


}