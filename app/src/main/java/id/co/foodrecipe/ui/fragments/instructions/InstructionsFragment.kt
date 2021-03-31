package id.co.foodrecipe.ui.fragments.instructions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import id.co.foodrecipe.R
import id.co.foodrecipe.models.Result
import id.co.foodrecipe.util.Constans
import kotlinx.android.synthetic.main.fragment_instructions.view.*


class InstructionsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_instructions, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(Constans.RECIPE_RESULT_KEY)

        view.instruction_webview.webViewClient = object : WebViewClient(){}
        val websiteUrl: String = myBundle!!.sourceUrl
        view.instruction_webview.loadUrl(websiteUrl)

        return view

    }

}