package com.example.buddier.ui.loginActivity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.buddier.R
import com.example.buddier.databinding.ActivityLoginBinding
import com.example.buddier.databinding.ActivityMainBinding
import com.example.buddier.ui.homeActivity.HomeActivity
import com.example.buddier.ui.signupActivity.SignupActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            btnSignup.setOnClickListener {
                startActivity(Intent(this@LoginActivity, SignupActivity::class.java))
            }
            btnLogin.setOnClickListener {
                startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
            }
        }
    }
}