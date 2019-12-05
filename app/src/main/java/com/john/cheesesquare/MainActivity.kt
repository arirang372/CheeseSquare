package com.john.cheesesquare

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_list_viewpager.*

class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupToolbar()
        drawerLayout = findViewById(R.id.drawer_layout)
        setupDrawerContent()
        setupViewPager()
        setupFloatingActionButton()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        var ab = supportActionBar
        ab?.setHomeAsUpIndicator(R.drawable.ic_menu)
        ab?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupDrawerContent() {
        nav_view?.setNavigationItemSelectedListener { item ->
            item.isChecked = true
            drawerLayout.closeDrawers()
            true
        }
    }

    private fun setupFloatingActionButton() {
        fab.setOnClickListener {
            Snackbar.make(it, "Here is a snack bar", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    private fun setupViewPager() {
        var adapter = Adapter(supportFragmentManager)
        adapter.addFragment(CheeseListFragment(), "Category 1")
        adapter.addFragment(CheeseListFragment(), "Category 2")
        adapter.addFragment(CheeseListFragment(), "Category 3")
        viewpager.adapter = adapter
        tabs.setupWithViewPager(viewpager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.sample_actions, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        when (AppCompatDelegate.getDefaultNightMode()) {
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM ->
                menu?.findItem(R.id.menu_night_mode_system)?.isChecked = true
            AppCompatDelegate.MODE_NIGHT_AUTO ->
                menu?.findItem(R.id.menu_night_mode_auto)?.isChecked = true
            AppCompatDelegate.MODE_NIGHT_YES ->
                menu?.findItem(R.id.menu_night_mode_night)?.isChecked = true
            AppCompatDelegate.MODE_NIGHT_NO ->
                menu?.findItem(R.id.menu_night_mode_day)?.isChecked = true
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> drawerLayout.openDrawer(GravityCompat.START)
            R.id.menu_night_mode_system -> setNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            R.id.menu_night_mode_day -> setNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            R.id.menu_night_mode_night -> setNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            R.id.menu_night_mode_auto -> setNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setNightMode(@AppCompatDelegate.NightMode nightMode: Int) {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        if (Build.VERSION.SDK_INT >= 11)
            recreate()
    }

    class Adapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        private val mFragments: ArrayList<Fragment> = arrayListOf()
        private val mFragmentTitles: ArrayList<String> = arrayListOf()

        fun addFragment(fragment: Fragment, title: String) {
            mFragments.add(fragment)
            mFragmentTitles.add(title)
        }

        override fun getItem(position: Int): Fragment {
            return mFragments[position]
        }

        override fun getCount(): Int {
            return mFragments.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitles[position]
        }
    }
}
