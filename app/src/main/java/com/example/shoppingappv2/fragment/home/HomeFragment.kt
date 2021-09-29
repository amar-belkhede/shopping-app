package com.example.shoppingappv2.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingappv2.enumList.Category
import com.example.shoppingappv2.R
import com.example.shoppingappv2.adapter.ProductApiAdapter
import com.example.shoppingappv2.data.model.ProductListModel
import com.example.shoppingappv2.utli.NetworkResult
import com.example.shoppingappv2.viewModel.CartViewModel
import com.example.shoppingappv2.viewModel.MainViewModel
import com.example.shoppingappv2.viewModel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val mProductViewModel: ProductViewModel  by viewModels()

    private val mCartViewModel: CartViewModel  by viewModels()
    private val mProductApiAdapter by lazy{ ProductApiAdapter(requireContext(),mCartViewModel,mProductViewModel, viewLifecycleOwner) }
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mView: View



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_home, container, false)

        //THIS IS FOR THE API
        setupRecycleView()
        readDatabase()




        /**
         * Observe and set the products to the recycleview of home screen
         * */
        mProductViewModel.readProducts.observe(viewLifecycleOwner,{ prodList->
            mView.allButton.isChecked = true
            mProductApiAdapter.setData(ProductListModel(prodList))
        })


        /**
         * setOnClickListener and set the all products to the recycleview of home screen
         * */
        mView.allButton.setOnClickListener {
            mainViewModel.radioButtonClicked = Category.All
            mProductViewModel.readProducts.observe(viewLifecycleOwner,{ prodList->
                mProductApiAdapter.setData(ProductListModel(prodList))
            })
        }

        /**
         * setOnClickListener and set the CLOTHING products to the recycleview of home screen
         * */
        mView.clothingButton.setOnClickListener {
            mainViewModel.radioButtonClicked = Category.CLOTHING
            mProductViewModel.getClothing.observe(viewLifecycleOwner,{ prodList->
                mProductApiAdapter.setData(ProductListModel(prodList))
            })
        }

        /**
         * setOnClickListener and set the ELECTRONIC products to the recycleview of home screen
         * */
        mView.electronicButton.setOnClickListener {
            mainViewModel.radioButtonClicked = Category.ELECTRONIC
            mProductViewModel.getElectronic.observe(viewLifecycleOwner,{ prodList->
                mProductApiAdapter.setData(ProductListModel(prodList))
            })
        }

        mView.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_productBottomSheetFragment)
        }




        //set menu
        setHasOptionsMenu(true)




        return mView
    }




    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_cart){
            findNavController().navigate(R.id.action_homeFragment_to_cartFragment)
        }
        return super.onOptionsItemSelected(item)
    }



    private fun setupRecycleView(){
        mView.recycleViewProductCardApi.adapter = mProductApiAdapter
        mView.recycleViewProductCardApi.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun readDatabase() {
        lifecycleScope.launch{
            mainViewModel.readProductsList.observe(viewLifecycleOwner, { database->
                if(database.isNotEmpty()){
                    Log.d("HomeFragment","readDatabase called")
                    mProductApiAdapter.setData(database[0].productListModel)

                    mProductViewModel.readProducts.observe(viewLifecycleOwner, {
                        if(it.size == 0) {
                            mProductViewModel.insertProductsFromList(database[0].productListModel.products)
                        }
                    })

                }else{
                    requestApiData()
                }
            })
        }
    }
    
/// add in the view model
    private fun requestApiData(){
        mainViewModel.getProducts()

        mainViewModel.productsResponse.observe(viewLifecycleOwner,{response ->

            when(response){
                is NetworkResult.Success -> {
                    response.data?.let {  mProductApiAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading ->{
                    Toast.makeText(
                        requireContext(),
                        "Is Loading",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }





}