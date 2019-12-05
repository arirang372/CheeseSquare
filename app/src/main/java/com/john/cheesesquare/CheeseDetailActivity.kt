package com.john.cheesesquare

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*


class CheeseDetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_NAME: String = "cheese_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setUpActionBar()
        collapsing_toolbar.title = intent.getStringExtra(EXTRA_NAME)
        loadBackdrop()
    }

    private fun setUpActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun loadBackdrop() {
        Glide.with(this).load(Cheeses.getRandomCheeseDrawable()).into(backdrop)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.sample_actions, menu)
        return true
    }
}