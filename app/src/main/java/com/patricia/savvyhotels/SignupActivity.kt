package com.patricia.savvyhotels

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.patricia.savvyhotels.constants.Constants
import com.patricia.savvyhotels.helpers.ApiHelper
import org.json.JSONArray
import org.json.JSONObject

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val linktologin = findViewById<TextView>(R.id.linktologin)
        linktologin.setOnClickListener{
            startActivity(Intent(applicationContext,SigninActivity::class.java))
        }

        val signup = findViewById<MaterialButton>(R.id.create)
        signup.setOnClickListener {
            val username=findViewById<TextInputEditText>(R.id.username)
            val email=findViewById<TextInputEditText>(R.id.email)
            val phone =findViewById<TextInputEditText>(R.id.phone)
            val  password=findViewById<TextInputEditText>(R.id.password)
            val confirm_password=findViewById<TextInputEditText>(R.id.confirm)


            if(password.text.toString() != confirm_password.text.toString()){
                Toast.makeText(applicationContext,"Passwords do not match", Toast.LENGTH_SHORT).show()
            }else{
                val api = Constants.BASEURL+"member_signup"
                val helper = ApiHelper(applicationContext)
                val body = JSONObject()
                body.put("username", username.text.toString())
                body.put("email", email.text.toString())
                body.put("phone", phone.text.toString())
                body.put("password", password.text.toString())

                helper.post(api,body,object: ApiHelper.CallBack{
                    override fun onSuccess(result: JSONArray?) {

                    }

                    override fun onSuccess(result: JSONObject?) {
                        Toast.makeText(applicationContext, result.toString(), Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(result: String?) {
                        Toast.makeText(applicationContext, result.toString(), Toast.LENGTH_SHORT).show()                    }
                })
            }
        }

    }
}