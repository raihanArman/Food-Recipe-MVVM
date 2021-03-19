package id.co.foodrecipe.ui.fragments.recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.co.foodrecipe.MainViewModel
import id.co.foodrecipe.R
import id.co.foodrecipe.adapter.RecipesAdapter
import id.co.foodrecipe.util.Constans.Companion.API_KEY
import id.co.foodrecipe.util.NetworkResult
import kotlinx.android.synthetic.main.fragment_recipes.view.*


@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private val adapter by lazy { RecipesAdapter() }
    private lateinit var mView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_recipes, container, false)
        mainViewModel= ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        setupRecyclerView()
        requestApiData()
        return mView
    }

    private fun requestApiData(){
        mainViewModel.getRecipes(applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner, Observer {response ->
            when(response){
                is NetworkResult.Success ->{
                    hideShimmerEffect()
                    response.data?.let {
                        adapter.setData(it)
                    }
                }
                is NetworkResult.Error ->{
                    hideShimmerEffect()
                    Toast.makeText(context, response.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading ->{
                    showShimmerEffect()
                }
            }
        })
    }

    private fun applyQueries(): HashMap<String, String>{
        val queries: HashMap<String, String> = HashMap()
        queries["number"] = "50"
        queries["apiKey"] = API_KEY
        queries["type"] = "snack"
        queries["diet"] = "vegan"
        queries["addRecipeInformation"] = "true"
        queries["fillIngredients"] = "true"
        return queries
    }

    private fun setupRecyclerView(){
        mView.shimmerRecyclerView.adapter = adapter
        mView.shimmerRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    private fun showShimmerEffect(){
        mView.shimmerRecyclerView.showShimmer()
    }

    private fun hideShimmerEffect(){
        mView.shimmerRecyclerView.hideShimmer()
    }

}