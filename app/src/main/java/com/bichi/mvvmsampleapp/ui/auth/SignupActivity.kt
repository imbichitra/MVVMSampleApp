package com.bichi.mvvmsampleapp.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bichi.mvvmsampleapp.R
import com.bichi.mvvmsampleapp.data.db.entities.User
import com.bichi.mvvmsampleapp.databinding.ActivityLoginBinding
import com.bichi.mvvmsampleapp.databinding.ActivitySignupBinding
import com.bichi.mvvmsampleapp.ui.home.HomeActivity
import com.bichi.mvvmsampleapp.util.hide
import com.bichi.mvvmsampleapp.util.show
import com.bichi.mvvmsampleapp.util.snackbar
import kotlinx.android.synthetic.main.activity_login.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class SignupActivity : AppCompatActivity() ,AuthListener,KodeinAware{

    override val kodein by kodein()

    private val factory: AuthViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivitySignupBinding = DataBindingUtil.setContentView(this,R.layout.activity_signup)
        val viewModel = ViewModelProvider(this,factory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel

        viewModel.authListener = this

        viewModel.getLoggedInUser().observe(this, Observer { user->
            if (user != null){
                Intent(this, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        })
    }

    override fun onStarted() {
        progress_bar.show()
    }

    override fun onSuccess(user: User) {
        progress_bar.hide()
        //toast("${user.name} is Logged In")
        //root_layout.snackbar("${user.name} is Logged In")
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        //toast(message)
        root_layout.snackbar(message)
    }
}