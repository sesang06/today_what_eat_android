package com.sesang06.todaywhateat

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var pagerAdapter: MainPagerAdapter


    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                view_pager.currentItem = 0
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                view_pager.currentItem = 1
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        pagerAdapter = MainPagerAdapter(supportFragmentManager)
        view_pager.adapter = pagerAdapter
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId  == R.id.info_edit) {
            val intent = Intent(this, EditActivity::class.java)
            this.startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

private class MainPagerAdapter(fragmentManager: androidx.fragment.app.FragmentManager) : androidx.fragment.app.FragmentStatePagerAdapter(fragmentManager) {
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(p0: Int): androidx.fragment.app.Fragment {
        when (p0) {
            0 -> return ListFragment()
            1 -> return PickFragment()
        }
        return ListFragment()
    }
}