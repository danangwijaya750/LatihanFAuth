package com.dngwjy.latihanfauth

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_auth_ui.*

class AuthUIActivity : AppCompatActivity() {

    val SIGN_IN_CODE=111
    lateinit var user:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_ui)

        val provider= mutableListOf(AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build())

        user=FirebaseAuth.getInstance()

        btnLogin.setOnClickListener {
            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(provider)
                    .build(),
                SIGN_IN_CODE
                )
        }

        btnLogout.setOnClickListener {
            user.signOut()
            changeState()
        }
        changeState()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int,
                                  data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==SIGN_IN_CODE&&resultCode==Activity.RESULT_OK){

            val response=IdpResponse.fromResultIntent(data)
            if(resultCode==Activity.RESULT_OK){

                Log.e(this::class.java.simpleName, user.currentUser?.email.toString())
                changeState()

            }else{
                Toast.makeText(this,
                    response?.error?.message,Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun changeState(){
        if(user.currentUser!=null){
            btnLogin.visibility=View.GONE
            btnLogout.visibility= View.VISIBLE
        }else{
            btnLogin.visibility=View.VISIBLE
            btnLogout.visibility= View.GONE
        }
    }
}
