package com.ubaya.cerbungnmp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.cerbung.CerbungNav
import com.google.android.material.snackbar.Snackbar
import com.ubaya.cerbungnmp.databinding.ActivityMainCerbungBinding

class MainActivityCerbung : AppCompatActivity() {
    private lateinit var binding: ActivityMainCerbungBinding
    val fragments: ArrayList<Fragment> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainCerbungBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        fragments.add(HomeFragment())
        fragments.add(FollowFragment())
        fragments.add(CreateFragment())
        fragments.add(UsersFragment())
        fragments.add(PrefFragment())
        fragments.add(ReadFragment())

        binding.viewPager.adapter = CerbungNav(this, fragments)

        //hubungin bottom nav bar ke viewpager
        binding.viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.bottomNav.selectedItemId =
                    binding.bottomNav.menu.getItem(position).itemId
                // Or: binding.bottomNav.selectedItemId =
                binding.bottomNav.menu[position].itemId
            }
        })

        //nampilin fragment paling kiri (index 0) itu home, dst
        binding.bottomNav.setOnItemSelectedListener {
            binding.viewPager.currentItem = when(it.itemId) {
                R.id.itemHome -> 0
                R.id.itemFollow -> 1
                R.id.itemCreate -> 2
                R.id.itemPrefs -> 3
                R.id.itemPrefs -> 4
                else -> 0 // default to home
            }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbox_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.itemNotif) {
            Snackbar.make(this, binding.root, "Notif", Snackbar.LENGTH_LONG).show()
        }
        return true
    }
    //override fun onBackPressed() {
        // Periksa apakah aktivitas sebelumnya adalah signin
        //if (isCerbungLogin()) {

        //} else {
            // Jalankan logika bawaan "Back"
            //super.onBackPressed()
        //}
    //}

    //private fun isCerbungLogin(): Boolean {
        //val callingActivity = callingActivity
        //return callingActivity?.className == "com.example.cerbung.SignIn"
    //}
}
