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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_ui)

        val provider= arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())

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
            val user=FirebaseAuth.getInstance()
            user.signOut()
            btnLogin.visibility=View.VISIBLE
            btnLogout.visibility= View.GONE
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int,
                                  data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==SIGN_IN_CODE&&resultCode==Activity.RESULT_OK){

            val response=IdpResponse.fromResultIntent(data)
            if(requestCode==Activity.RESULT_OK){
                val user = FirebaseAuth.getInstance().currentUser
                Log.d(this::class.java.simpleName,user?.email)
                btnLogin.visibility=View.GONE
                btnLogout.visibility= View.VISIBLE
            }else{
                Toast.makeText(this,
                    response?.error?.message,Toast.LENGTH_SHORT).show()
            }

        }
    }
}
