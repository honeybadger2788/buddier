package com.example.buddier.ui.signupActivity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.buddier.databinding.ActivitySignupBinding
import com.example.buddier.ui.loginActivity.LoginActivity

class SignupActivity: AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            btnLogin.setOnClickListener{
                goToLogin()
            }
        }
    }

    private fun goToLogin() {
        startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
    }
}