package com.mirus.ekoug

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.material.bottomnavigation.BottomNavigationView

class DashboardActivity : AppCompatActivity() {

    private lateinit var mMap: GoogleMap
    private var currentFragmentId: Int = R.id.item_1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val legitHere = intent.getIntExtra("cg", 404) == 512
        if(!legitHere){
            Log.e("eco-ug", "Попытка взлома!")
            startActivity(Intent(this, WelcomeActivity::class.java))
        }

        initBottomNavView(savedInstanceState)

        val dispatcher = onBackPressedDispatcher
        dispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Log.e("eco-ug","Попытка нажатия кнопки назад")
            }
        })
    }

    private fun initBottomNavView(savedInstanceState: Bundle?) {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
        }

        bottomNavigationView.setOnItemSelectedListener { item ->
            if (item.itemId != currentFragmentId) {
                val nextFragment = when (item.itemId) {
                    R.id.item_1 -> HomeFragment()
                    R.id.item_2 -> MapFragment()
                    R.id.item_3 -> StatesFragment()
                    R.id.item_4 -> CalendarFragment()
                    else -> null
                }

                nextFragment?.let {
                    animateFragmentTransition(item.itemId, it)
                    currentFragmentId = item.itemId
                }
            }
            true
        }
    }

    private fun animateFragmentTransition(itemId: Int, fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()

        if (itemId > currentFragmentId) {
            transaction.setCustomAnimations(
                R.anim.slide_in_right,  // Входящий фрагмент
                R.anim.slide_out_left,  // Исходящий фрагмент
                R.anim.slide_in_left,   // Возврат к предыдущему
                R.anim.slide_out_right  // Уходящий при возврате
            )
        } else if (itemId < currentFragmentId) {
            transaction.setCustomAnimations(
                R.anim.slide_in_left,
                R.anim.slide_out_right,
                R.anim.slide_in_right,
                R.anim.slide_out_left
            )
        }

        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }
}
