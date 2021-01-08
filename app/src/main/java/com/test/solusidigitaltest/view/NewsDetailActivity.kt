package com.test.solusidigitaltest.view

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.test.solusidigitaltest.R
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.collapstoolbar_activity_detail.*
import java.text.SimpleDateFormat


class NewsDetailActivity : AppCompatActivity() {
    private var collapsingToolbar: CollapsingToolbarLayout? = null
    var title2 : String = ""
    var subtitle2 : String = ""
    var date2 : String = ""
    var desc2 : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.collapstoolbar_activity_detail)


        (this as AppCompatActivity).supportActionBar

//        collapsingToolbar =
//            findViewById<View>(R.id.toolbar_layout) as CollapsingToolbarLayout
//        collapsingToolbar!!.title = intent.getStringExtra(getString(R.string.intent_title))

        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm")
        val output: String = formatter.format(parser.parse(intent.getStringExtra(getString(R.string.publishedAt))))

        val requestOption = RequestOptions()
        requestOption.placeholder(R.drawable.ic_launcher_background)
        requestOption.error(R.drawable.ic_launcher_background)
        Glide.with(poster_news_detail).setDefaultRequestOptions(requestOption)
            .load(intent.getStringExtra(getString(R.string.image))).into(poster_news_detail)

        tv_title!!.text = intent.getStringExtra(getString(R.string.intent_title))
        tv_subtitle!!.text = intent.getStringExtra(getString(R.string.author))
        tv_date!!.text = output
        expand_text_view.text ="Description"
        expandable_text!!.text = intent.getStringExtra(getString(R.string.description))

        //set back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    }
}