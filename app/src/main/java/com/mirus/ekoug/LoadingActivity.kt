package com.mirus.ekoug

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class LoadingActivity : AppCompatActivity() {

    val logoRecycle:ImageView by lazy {
        findViewById(R.id.logoRecycle)
    }
    private val fallAnimation:Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.fall)}
    private val swingAnimation:Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.swing) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        Glide.with(this@LoadingActivity).load(R.drawable.logo).transform(RoundedCorners(58)).into(logoRecycle)
        logoRecycle.startAnimation(fallAnimation)

        // После падения запускаем покачивание
        fallAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                logoRecycle.startAnimation(swingAnimation)
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })

        Handler(Looper.getMainLooper()).postDelayed({

            val can_go = getSharedPreferences("apps", Context.MODE_PRIVATE).getBoolean("first_look", false)
            if(can_go){
                val intent = Intent(this, DashboardActivity::class.java)
                intent.putExtra("cg", 512)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            }else{
                val intent = Intent(this, WelcomeActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            }

        }, 3000)
    }

}