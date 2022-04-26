package com.example.workroute.initActivities

import android.app.ProgressDialog
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.View.OnFocusChangeListener
import androidx.appcompat.app.AppCompatActivity
import com.example.workroute.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
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
    private lateinit var cardEmail:MaterialCardView

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
        cardEmail=findViewById(R.id.card_email)
        initListeners()
    }

    private fun initListeners(){
        toolbar.setNavigationOnClickListener {
            onBackPressed()
            finish()
        }

        edEmail.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                setColorsFocus(cardEmail, edEmail, layoutEmail)
            } else {
                removeColorsFocus(cardEmail, edEmail, layoutEmail)
            }
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

    private fun setColorsFocus(
        card: MaterialCardView,
        ed: TextInputEditText,
        txt: TextInputLayout
    ) {
        card.strokeColor = Color.parseColor("#0391FF")
        card.strokeWidth = 3
        txt.hintTextColor = ColorStateList.valueOf(getColor(R.color.secondary))
        txt.setStartIconTintList(ColorStateList.valueOf(getColor(R.color.secondary)))
        ed.setTextColor(getColor(R.color.secondary))
        txt.setEndIconTintList(ColorStateList.valueOf(getColor(R.color.secondary)))
    }

    private fun removeColorsFocus(
        card: MaterialCardView,
        ed: TextInputEditText,
        txt: TextInputLayout
    ) {
        card.strokeWidth = 0
        ed.setTextColor(Color.parseColor("#BABABA"))
        txt.hintTextColor = ColorStateList.valueOf(Color.parseColor("#D5D5D5"))
        txt.setStartIconTintList(ColorStateList.valueOf(Color.parseColor("#D5D5D5")))
        txt.setEndIconTintList(ColorStateList.valueOf(Color.parseColor("#D5D5D5")))
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