package com.example.shoppingappv2.fragment.productDetail

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.shoppingappv2.R
import com.example.shoppingappv2.data.model.CartModel
import com.example.shoppingappv2.viewModel.CartViewModel
import com.example.shoppingappv2.viewModel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_product_detail.view.*

class ProductDetailFragment : Fragment() {

    // Arguments passing from one fragment to another (in this case home to product details)
    private val args by navArgs<ProductDetailFragmentArgs>()

    private lateinit var  mCartViewModel: CartViewModel
    private lateinit var mProductViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCartViewModel = ViewModelProvider(requireActivity()).get(CartViewModel::class.java)
        mProductViewModel = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_product_detail, container, false)

        view.prodDetailImage.load(args.currentProduct.imageview){
                crossfade(600)
                error(R.drawable.ic_error_outline)
        }
        view.prodDetailName.setText(args.currentProduct.title)
        view.prodDetailDesc.setText(args.currentProduct.description)
        view.prodDetailPrice.setText(args.currentProduct.price.toString())



        /**
         * Toggle between added to the cart and add to the cart button(ProdDetailAddToCart)
         * */
        mProductViewModel.getProductById(args.currentProduct.id).observe(viewLifecycleOwner,  {
            if(it.inCart){
                view.ProdDetailAddToCart.text = getString(R.string.added_to_the_cart)

            }else{
                view.ProdDetailAddToCart.text = getString(R.string.Add_to_cart)
            }
        })

        /**
         * after clicking ProdDetailAddToCart button, product is added to the cart
         * */
        view.ProdDetailAddToCart.setOnClickListener {
            mProductViewModel.getProductById(args.currentProduct.id).observe(viewLifecycleOwner,  {
                if(it.inCart){
//                    Toast.makeText(requireContext(),"Already added to the Cart!!", Toast.LENGTH_SHORT).show()
                }else{
                    mCartViewModel.insertProdToCart(

                        CartModel(
                            0,
                            it.id,
                            it.category,
                            it.productType,
                            it.imageview,
                            it.price,
                            it.title,
                            it.description,
                            1

                        )

                    )

                    var updateProd = it
                    updateProd.inCart = true
                    mProductViewModel.updateProduct(updateProd)

                    Toast.makeText(requireContext(),"${updateProd.title} added", Toast.LENGTH_SHORT).show()
                }
            })


        }


        //set menu
        setHasOptionsMenu(true)


        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.product_detail_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.product_detail_cart){
            findNavController().navigate(R.id.action_productDetailFragment_to_cartFragment)
        }
        return super.onOptionsItemSelected(item)
    }
}