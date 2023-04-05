package com.example.buddier.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.buddier.databinding.ActivityMainBinding
import com.example.buddier.ui.loginActivity.LoginActivity
import com.example.buddier.ui.signupActivity.SignupActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            btnLogin.setOnClickListener{
                goToLogin()
            }

            btnSignup.setOnClickListener {
                goToSignup()
            }
        }
    }

    private fun goToSignup() {
        startActivity(Intent(this@MainActivity, SignupActivity::class.java))
    }

    private fun goToLogin() {
        startActivity(Intent(this@MainActivity, LoginActivity::class.java))
    }
}