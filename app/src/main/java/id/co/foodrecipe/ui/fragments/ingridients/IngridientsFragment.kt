package id.co.foodrecipe.ui.fragments.ingridients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import id.co.foodrecipe.R
import id.co.foodrecipe.adapter.IngredientsAdapter
import id.co.foodrecipe.models.Result
import id.co.foodrecipe.util.Constans
import kotlinx.android.synthetic.main.fragment_ingridients.view.*


class IngridientsFragment : Fragment() {

    private val mAdapter: IngredientsAdapter by lazy { IngredientsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_ingridients, container, false)
        val args = arguments
        val myBundle: Result? = args?.getParcelable(Constans.RECIPE_RESULT_KEY)

        setupRecyclerView(view)
        myBundle?.extendedIngredients?.let {
            mAdapter.setData(it)
        }

        return view
    }


    private fun setupRecyclerView(view: View){
        view.ingredients_recyclerview.adapter = mAdapter
        view.ingredients_recyclerview.layoutManager = LinearLayoutManager(requireContext())
    }

}