package com.mirus.ekoug

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class WelcomeActivity : AppCompatActivity() {
    private val logoRecycle: ImageView by lazy {
        findViewById(R.id.logoRecycle)
    }
    private val text_start: ConstraintLayout by lazy {
        findViewById(R.id.text_start)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        Glide.with(this@WelcomeActivity).load(R.drawable.logo).transform(RoundedCorners(58)).into(logoRecycle)

        val dispatcher = onBackPressedDispatcher
        dispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Log.e("welcome-activity","Попытка нажатия кнопки назад")
            }
        })

        text_start.setOnClickListener { goToNextActivity("ycg") }
    }

    private fun goToNextActivity(s: String){
        when(s){
            "ycg" -> {
                val preferenses = getSharedPreferences("apps", Context.MODE_PRIVATE).edit()
                preferenses.putBoolean("first_look", true).apply()

                val intent = Intent(this, DashboardActivity::class.java)
                intent.putExtra("cg", 512)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

            }
            else -> {
                Log.e("welcome-activity", "Хорошая попытка, жаль провальная!)")

            }
        }
    }
}