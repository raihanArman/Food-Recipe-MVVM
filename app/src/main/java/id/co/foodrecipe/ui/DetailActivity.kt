package id.co.foodrecipe.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import id.co.foodrecipe.R
import id.co.foodrecipe.adapter.PagerAdapter
import id.co.foodrecipe.ui.fragments.ingridients.IngridientsFragment
import id.co.foodrecipe.ui.fragments.instructions.InstructionsFragment
import id.co.foodrecipe.ui.fragments.overview.OverviewFragment
import id.co.foodrecipe.util.Constans.Companion.RECIPE_RESULT_KEY
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    private val args by navArgs<DetailActivityArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragments = ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngridientsFragment())
        fragments.add(InstructionsFragment())

        val titles = ArrayList<String>()
        titles.add("Overview")
        titles.add("Ingredients")
        titles.add("Instructions")

        val resultBundle = Bundle()
        resultBundle.putParcelable(RECIPE_RESULT_KEY, args.result)

        val adapter = PagerAdapter(
                resultBundle,
                fragments,
                titles,
                supportFragmentManager
        )

        viewPager.adapter = adapter
        tab_layout.setupWithViewPager(viewPager)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}