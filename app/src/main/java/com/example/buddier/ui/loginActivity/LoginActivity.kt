package com.example.buddier.ui.loginActivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.buddier.R
import com.example.buddier.core.dialog.DialogFragmentLauncher
import com.example.buddier.core.dialog.ErrorDialog
import com.example.buddier.core.dialog.LoginSuccessDialog
import com.example.buddier.core.ex.*
import com.example.buddier.databinding.ActivityLoginBinding
import com.example.buddier.ui.homeActivity.HomeActivity
import com.example.buddier.ui.loginActivity.model.UserLoginModel
import com.example.buddier.ui.signupActivity.SignupActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity: AppCompatActivity() {

    companion object {
        fun create(context: Context): Intent =
            Intent(context, LoginActivity::class.java)

    }

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var dialogLauncher: DialogFragmentLauncher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        initListeners()
        initObservers()
        /*binding.viewBottom.tvFooter.text = span(
            getString(R.string.login_footer_unselected),
            getString(R.string.login_footer_selected)
        )*/
    }

    private fun initListeners() {
        with(binding){
            etEmail.loseFocusAfterAction(EditorInfo.IME_ACTION_NEXT)
            etEmail.onTextChanged { onFieldChanged() }

            etPassword.loseFocusAfterAction(EditorInfo.IME_ACTION_DONE)
            etPassword.setOnFocusChangeListener { _, hasFocus -> onFieldChanged(hasFocus) }
            etPassword.onTextChanged { onFieldChanged() }

            //binding.tvForgotPassword.setOnClickListener { loginViewModel.onForgotPasswordSelected() }

            //binding.viewBottom.tvFooter.setOnClickListener { loginViewModel.onSignUpSelected() }

            btnLogin.setOnClickListener {
                it.dismissKeyboard()
                loginViewModel.onLoginSelected(
                    etEmail.text.toString(),
                    etPassword.text.toString()
                )
            }
        }
    }

    private fun initObservers() {
        loginViewModel.navigateToDetails.observe(this) {
            it.getContentIfNotHandled()?.let {
                goToDetail()
            }
        }

        loginViewModel.navigateToSignUp.observe(this) {
            it.getContentIfNotHandled()?.let {
                goToSignUp()
            }
        }

        loginViewModel.navigateToForgotPassword.observe(this) {
            it.getContentIfNotHandled()?.let {
                goToForgotPassword()
            }
        }

        loginViewModel.navigateToVerifyAccount.observe(this) {
            it.getContentIfNotHandled()?.let {
                goToVerify()
            }
        }

        loginViewModel.showErrorDialog.observe(this) { userLogin ->
            if (userLogin.showErrorDialog) showErrorDialog(userLogin)
        }

        lifecycleScope.launchWhenStarted {
            loginViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
    }

    private fun updateUI(viewState: LoginViewState) {
        with(binding) {
            tilEmail.error =
                if (viewState.isValidEmail) null else getString(R.string.login_error_mail)
            tilPassword.error =
                if (viewState.isValidPassword) null else getString(R.string.login_error_password)
        }
    }

    private fun onFieldChanged(hasFocus: Boolean = false) {
        if (!hasFocus) {
            loginViewModel.onFieldsChanged(
                email = binding.etEmail.text.toString(),
                password = binding.etPassword.text.toString()
            )
        }
    }

    private fun showErrorDialog(userLogin: UserLoginModel) {
        ErrorDialog.create(
            title = getString(R.string.login_error_dialog_title),
            description = getString(R.string.login_error_dialog_body),
            negativeAction = ErrorDialog.Action(getString(R.string.login_error_dialog_negative_action)) {
                it.dismiss()
            },
            positiveAction = ErrorDialog.Action(getString(R.string.login_error_dialog_positive_action)) {
                loginViewModel.onLoginSelected(
                    userLogin.email,
                    userLogin.password
                )
                it.dismiss()
            }
        ).show(dialogLauncher, this)
    }

    private fun goToForgotPassword() {
        toast(getString(R.string.feature_coming_soon))
    }

    private fun goToSignUp() {
        startActivity(Intent(this@LoginActivity, SignupActivity::class.java))
    }

    private fun goToDetail() {
        LoginSuccessDialog.create().show(dialogLauncher, this)
    }

    private fun goToVerify() {

    }
}
