package id.co.foodrecipe.ui.fragments.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import coil.load
import id.co.foodrecipe.R
import id.co.foodrecipe.models.Result
import kotlinx.android.synthetic.main.fragment_overview.view.*
import org.jsoup.Jsoup


class OverviewFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_overview, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable("recipeBundle")
        view.main_imageView.load(myBundle?.image)
        view.title_textView.text = myBundle?.title
        view.like_textView.text = myBundle?.aggregateLikes.toString()
        view.time_textView.text = myBundle?.readyInMinutes.toString()
        view.summary_textView.text = myBundle?.summary
        myBundle?.summary.let {
            val summary = Jsoup.parse(it).text()
            view.summary_textView.text = summary
        }

        if (myBundle?.vegetarian == true){
            view.vegetarian_imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.vegetarian_textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (myBundle?.vegan == true){
            view.vegan_imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.vegan_textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (myBundle?.glutenFree == true){
            view.glutenfree_imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.glutenfree_textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (myBundle?.dairyFree == true){
            view.dailyfree_imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.dailyfree_textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (myBundle?.veryHealthy == true){
            view.healty_imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.healty_textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (myBundle?.cheap == true){
            view.cheap_imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.cheap_textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }
        return view
    }
    
}