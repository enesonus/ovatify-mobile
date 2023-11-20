package com.sabanci.ovatify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.sabanci.ovatify.databinding.ActivityMainBinding
import com.sabanci.ovatify.fragment.DashboardFragment
import com.sabanci.ovatify.fragment.ExploreFragment
import com.sabanci.ovatify.fragment.HomeFragment
import com.sabanci.ovatify.fragment.LibraryFragment

class HomePageActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var ishomebuttonclicked=true
        var isexplorebuttonclicked=false
        var islibrarybuttonclicked=false
        var isdashboardbuttonclicked=false

        binding.navhome.setOnClickListener{
            if (!ishomebuttonclicked){
                ishomebuttonclicked=true
                isexplorebuttonclicked=false
                islibrarybuttonclicked=false
                isdashboardbuttonclicked=false
                binding.homeLogo.setImageResource(R.drawable.home_clicked)
                binding.exploreLogo.setImageResource(R.drawable.explore_nonclicked)
                binding.libraryLogo.setImageResource(R.drawable.library_nonclicked)
                binding.dashboardLogo.setImageResource(R.drawable.dashboard_nonclicked)
                replaceFragment(HomeFragment())
            }

        }
        binding.navexplore.setOnClickListener{
            if (!isexplorebuttonclicked){
                ishomebuttonclicked=false
                isexplorebuttonclicked=true
                islibrarybuttonclicked=false
                isdashboardbuttonclicked=false
                binding.homeLogo.setImageResource(R.drawable.home_nonclicked)
                binding.exploreLogo.setImageResource(R.drawable.explore_clicked)
                binding.libraryLogo.setImageResource(R.drawable.library_nonclicked)
                binding.dashboardLogo.setImageResource(R.drawable.dashboard_nonclicked)
                replaceFragment(ExploreFragment())
            }
        }
        binding.navlibrary.setOnClickListener{
            if (!islibrarybuttonclicked){
                ishomebuttonclicked=false
                isexplorebuttonclicked=false
                islibrarybuttonclicked=true
                isdashboardbuttonclicked=false
                binding.homeLogo.setImageResource(R.drawable.home_nonclicked)
                binding.exploreLogo.setImageResource(R.drawable.explore_nonclicked)
                binding.libraryLogo.setImageResource(R.drawable.library_clicked)
                binding.dashboardLogo.setImageResource(R.drawable.dashboard_nonclicked)
                replaceFragment(LibraryFragment())
            }
        }
        binding.navdashboard.setOnClickListener{
            if (!isdashboardbuttonclicked){
                ishomebuttonclicked=false
                isexplorebuttonclicked=false
                islibrarybuttonclicked=false
                isdashboardbuttonclicked=true
                binding.homeLogo.setImageResource(R.drawable.home_nonclicked)
                binding.exploreLogo.setImageResource(R.drawable.explore_nonclicked)
                binding.libraryLogo.setImageResource(R.drawable.library_nonclicked)
                binding.dashboardLogo.setImageResource(R.drawable.dashboard_clicked)
                replaceFragment(DashboardFragment())
            }
        }

    }
    fun replaceFragment(fragment: Fragment){
        val fragmentmanager= supportFragmentManager
        val trans=fragmentmanager.beginTransaction()
        trans.replace(R.id.fragmentContainerView,fragment)
        trans.commit()
    }
}