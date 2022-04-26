package com.example.workroute.initActivities

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import com.example.workroute.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var layoutEmail:TextInputLayout
    private lateinit var edEmail:TextInputEditText
    private lateinit var btnSend:MaterialButton
    private lateinit var toolbar:MaterialToolbar
    private lateinit var progressDialog:ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Register)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        init()
    }

    private fun init(){
        toolbar=findViewById(R.id.toolbar_recovery_password)
        layoutEmail=findViewById(R.id.layout_email_recover_password)
        edEmail=findViewById(R.id.ed_email_recover_password)
        btnSend=findViewById(R.id.btn_send_email)
        progressDialog= ProgressDialog(this,R.style.ProgressDialog)
        progressDialog.setMessage("Please wait...")
        progressDialog.setTitle("Sending email")
        progressDialog.setCancelable(false)
        initListeners()
    }

    private fun initListeners(){
        toolbar.setNavigationOnClickListener {
            onBackPressed()
            finish()
        }

        btnSend.setOnClickListener {
            if(edEmail.text.toString().trim().isEmpty()){
                Snackbar.make(it,"Yoy must fill the email field",Snackbar.LENGTH_SHORT).show()
                clearEmail()
            }else if(!Patterns.EMAIL_ADDRESS.matcher(edEmail.text.toString().trim()).matches()){
                Snackbar.make(it,"Yoy must insert a valid email",Snackbar.LENGTH_SHORT).show()
                clearEmail()
            }else{
                progressDialog.show()
                sendEmail(it)
            }
        }
    }

    private fun sendEmail(view:View){
        val auth=FirebaseAuth.getInstance()
        val emailAddress=edEmail.text.toString()

        auth.sendPasswordResetEmail(emailAddress).addOnCompleteListener{
            if(it.isSuccessful){
                progressDialog.dismiss()
                Snackbar.make(view,"Email send",Snackbar.LENGTH_SHORT).show()
                startActivity(Intent(this,ConfirmLogin::class.java))
                finish()
            }else{
                progressDialog.dismiss()
                Snackbar.make(view,"The email is incorrect",Snackbar.LENGTH_SHORT).show()
                clearEmail()
            }
        }
    }

    private fun clearEmail(){
        edEmail.setText("")
    }
}