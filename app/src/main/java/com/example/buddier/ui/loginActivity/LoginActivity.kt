package com.example.buddier.ui.loginActivity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.buddier.databinding.ActivityLoginBinding
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
                goToSignup()
            }
            btnLogin.setOnClickListener {
                goToHome()
            }
        }
    }

    private fun goToHome() {
        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
    }

    private fun goToSignup() {
        startActivity(Intent(this@LoginActivity, SignupActivity::class.java))
    }
}