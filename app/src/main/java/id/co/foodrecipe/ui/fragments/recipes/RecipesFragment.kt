package id.co.foodrecipe.ui.fragments.recipes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.co.foodrecipe.viewmodels.MainViewModel
import id.co.foodrecipe.R
import id.co.foodrecipe.adapter.RecipesAdapter
import id.co.foodrecipe.databinding.FragmentRecipesBinding
import id.co.foodrecipe.util.Constans.Companion.API_KEY
import id.co.foodrecipe.util.NetworkResult
import id.co.foodrecipe.util.observeOnce
import id.co.foodrecipe.viewmodels.RecipesViewModel
import kotlinx.android.synthetic.main.fragment_recipes.view.*
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private var _binding: FragmentRecipesBinding?= null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipesViewModel: RecipesViewModel
    private val adapter by lazy { RecipesAdapter() }
    private lateinit var mView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel= ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        recipesViewModel  = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel


        setupRecyclerView()
        readDatabase()

        return binding.root
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observeOnce(viewLifecycleOwner, Observer {database ->
                if (database.isNotEmpty()){
                    Log.d("Recipes Fragment", "readDatabase: Called")
                    adapter.setData(database[0].foodRecipe)
                    hideShimmerEffect()
                }else{
                    requestApiData()
                }
            })
        }
    }

    private fun requestApiData(){
        Log.d("Recipes Fragment", "requestApiData: Called")
        mainViewModel.getRecipes(recipesViewModel.applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner, Observer {response ->
            when(response){
                is NetworkResult.Success ->{
                    hideShimmerEffect()
                    response.data?.let {
                        adapter.setData(it)
                    }
                }
                is NetworkResult.Error ->{
                    loadDataFromCache()
                    hideShimmerEffect()
                    Toast.makeText(context, response.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading ->{
                    showShimmerEffect()
                }
            }
        })
    }


    private fun setupRecyclerView(){
        binding.shimmerRecyclerView.adapter = adapter
        binding.shimmerRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    private fun loadDataFromCache(){
        lifecycleScope.launch {
            mainViewModel.readRecipes.observe(viewLifecycleOwner){database ->
                if (database.isNotEmpty()){
                    adapter.setData(database[0].foodRecipe)
                }
            }
        }
    }

    private fun showShimmerEffect(){
        binding.shimmerRecyclerView.showShimmer()
    }

    private fun hideShimmerEffect(){
        binding.shimmerRecyclerView.hideShimmer()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}