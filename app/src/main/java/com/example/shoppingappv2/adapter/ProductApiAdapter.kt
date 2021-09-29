package com.example.shoppingappv2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingappv2.data.model.CartModel
import com.example.shoppingappv2.data.model.ProductListModel
import com.example.shoppingappv2.data.model.ProductModel
import com.example.shoppingappv2.utli.ProductsDiffUtil
import com.example.shoppingappv2.viewModel.CartViewModel
import com.example.shoppingappv2.viewModel.ProductViewModel
import com.example.shoppingappv2.databinding.CardProductApiBinding
import com.example.shoppingappv2.fragment.home.HomeFragmentDirections
import kotlinx.android.synthetic.main.card_product_api.view.*

/**
 * It is for the HomeFragment for the recycleview
 *
 * @param context Context from the HomeFragment
 * @param mCartViewModel CartViewModel from the HomeFragment
 * @param mProductViewModel ProductViewModel from the HomeFragment
 * @param viewLifecycleOwner LifecycleOwner from the HomeFragment
 * */
class ProductApiAdapter(
    val context: Context,
    val mCartViewModel: CartViewModel,
    val mProductViewModel: ProductViewModel,
    val viewLifecycleOwner: LifecycleOwner
):RecyclerView.Adapter<ProductApiAdapter.MyViewHolder>() {



    /**
     * Empty list of ProductModel
     * */
    private var products = emptyList<ProductModel>()




    class MyViewHolder(private val binding: CardProductApiBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductModel){
            binding.product = product
            binding.executePendingBindings()




        }

        companion object{
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CardProductApiBinding.inflate(layoutInflater,parent, false)
                return MyViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//         var currentProduct: ProductModel = mProductViewModel.getProductByIdSimple(products[position].id)

        var currentProduct = products[position]
        holder.bind(currentProduct)


        /**
         * after clicking on card detail, navigate to product detail fragment
         * **/
        holder.itemView.cardProdDetail.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(products[position])
            holder.itemView.findNavController().navigate(action)
        }


        /**
         * Add the product to cart
         * **/
        holder.itemView.cardAddProdToCart.setOnClickListener{

            if(currentProduct.inCart){
                Toast.makeText(context,"Already added to the Cart!!", Toast.LENGTH_SHORT).show()
            }
            else{
                mCartViewModel.insertProdToCart(
                    CartModel(
                        0,
                        currentProduct.id,
                        currentProduct.category,
                        currentProduct.productType,
                        currentProduct.imageview,
                        currentProduct.price,
                        currentProduct.title,
                        currentProduct.description,
                        1)
                )

                /**
                 * updating product table to notify that the product is in the cart
                 **/
                currentProduct.inCart =true
                mProductViewModel.updateProduct(currentProduct)
//                    Toast.makeText(context,"${it.title} added", Toast.LENGTH_SHORT).show()
            }
        }

        /**
         * toggle ImageView between add the product and added the product
         **/
        if(currentProduct.inCart){
            holder.itemView.cardAddProdToCart?.visibility = View.INVISIBLE
            holder.itemView.cardAddedProdToCart?.visibility = View.VISIBLE
        }else{
            holder.itemView.cardAddProdToCart?.visibility = View.VISIBLE
            holder.itemView.cardAddedProdToCart?.visibility = View.INVISIBLE
        }



    }

    override fun getItemCount(): Int {
        return products.size
    }

    /**
     * updating the data in the recycleview
     * @param newData ProductListModel
     * */
    fun setData(newData: ProductListModel){
        val productsDiffUtil = ProductsDiffUtil(products, newData.products)
        val diffUtilResult = DiffUtil.calculateDiff(productsDiffUtil)
        products = newData.products
        diffUtilResult.dispatchUpdatesTo(this)
//        notifyDataSetChanged()
    }


}