package com.liyaan.mytinyvideo

import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavGraphNavigator
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.liyaan.mytinyvideo.utils.NavGraphBuilder

class MainActivity : AppCompatActivity(),BottomNavigationView.OnNavigationItemSelectedListener {
    private val fragment by lazy {
        supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
    }
    private var navController:NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(setOf(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
        navView.setOnNavigationItemSelectedListener(this)
        if (fragment != null) {
            NavGraphBuilder.build(this,navController!!, fragment!!.id)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        navController?.navigate(item.itemId)
        return !TextUtils.isEmpty(item.title)
    }

}