package com.ubaya.cerbungnmp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.cerbung.CerbungNav
import com.google.android.material.snackbar.Snackbar
import com.ubaya.cerbungnmp.databinding.ActivityMainCerbungBinding

class MainActivityCerbung : AppCompatActivity() {
    private lateinit var binding:ActivityMainCerbungBinding
    val fragments: ArrayList<Fragment> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainCerbungBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fragments.add(HomeFragment())
        fragments.add(FollowFragment())
        fragments.add(CreateFragment())
        fragments.add(UsersFragment())
        fragments.add(PrefFragment())

        binding.viewPager2.adapter = CerbungNav(this, fragments)

        binding.viewPager2.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.bottomNav.selectedItemId =
                    binding.bottomNav.menu.getItem(position).itemId
            }
        })

        binding.bottomNav.setOnItemSelectedListener {
            binding.viewPager2.currentItem = when(it.itemId) {
                R.id.itemHome -> 0
                R.id.itemFollow -> 1
                R.id.itemCreate -> 2
                R.id.itemUsers -> 3
                R.id.itemPrefs -> 4
                else -> 0 // default to home
            }
            true
        }

        //var prefFragment = PrefFragment.newInstance(id)
        /*supportFragmentManager.beginTransaction().apply {
            add(binding.container.id, prefFragment)
            commit()
        }*/

        //setSupportActionBar(binding.toolbars)
        //supportActionBar?.title = "Cerbung"

        //supportActionBar?.setDisplayHomeAsUpEnabled(false)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbox_menu, menu)
        return true
    }

    companion object{

    }
}
