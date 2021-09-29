package com.example.shoppingappv2.fragment.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingappv2.R
import com.example.shoppingappv2.adapter.CartAdapter
import com.example.shoppingappv2.viewModel.CartViewModel
import com.example.shoppingappv2.viewModel.ProductViewModel
import com.example.shoppingappv2.fragment.SharedViewModel
import kotlinx.android.synthetic.main.fragment_cart.view.*

class CartFragment : Fragment() {

    private lateinit var  mCartViewModel: CartViewModel

    private val mSharedViewModel: SharedViewModel by viewModels()

    private lateinit var mProductViewModel: ProductViewModel



    private val cartAdapter: CartAdapter by lazy { CartAdapter(
                                                    mCartViewModel,
                                                    mProductViewModel,
                                                    viewLifecycleOwner,
                                                    requireContext()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCartViewModel = ViewModelProvider(requireActivity()).get(CartViewModel::class.java)
        mProductViewModel = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_cart, container, false)


        //recycleview
        val recycleView = view.recycleViewCart
        recycleView.adapter = cartAdapter
        recycleView.layoutManager = LinearLayoutManager(requireContext())


        /**
         * Observes and set the products from the CART_TABLE in the recycleview adapter
         * */
        mCartViewModel.getAllProdFromCart.observe(viewLifecycleOwner, { data ->
            mSharedViewModel.checkIfDatabaseEmpty(data)
            cartAdapter.setData(data)
        })

        /**
         * Observes wheather the cart is empty or not
         * */
        mSharedViewModel.emptyDataBase.observe( viewLifecycleOwner,  {
            showEmptyDatabaseView(it)
        })


        /**
         * Observe and set the total price of the items in the cart
         * */
        mCartViewModel.getTotalPrice.observe(viewLifecycleOwner,  {
            if(it != null){
                view.cartTotal.text = it.toString()
            }else{
                view.cartTotal.text = "0.0"
            }

        })



        return view
    }


    /**
     * toggle the visibility of empty imageview, visible when cart is empty
     *
     *  @param emptyDatabase boolean value
     * */
    private fun showEmptyDatabaseView(emptyDatabase: Boolean){
        if(emptyDatabase){
            view?.no_data_imageView?.visibility = View.VISIBLE
            view?.no_data_textView?.visibility = View.VISIBLE
        }else{
            view?.no_data_imageView?.visibility = View.INVISIBLE
            view?.no_data_textView?.visibility = View.INVISIBLE
        }
    }

}