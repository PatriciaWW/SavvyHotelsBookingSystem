package com.patricia.savvyhotels

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.patricia.savvyhotels.constants.Constants
import com.patricia.savvyhotels.helpers.ApiHelper
import com.patricia.savvyhotels.helpers.PrefsHelper
import org.json.JSONArray
import org.json.JSONObject

class SigninActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signin)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val email: TextInputEditText = findViewById(R.id.email)
        val password: TextInputEditText = findViewById((R.id.password))
        val login: MaterialButton = findViewById(R.id.login)

        login.setOnClickListener {
            val val_email = email.text.toString()
            val val_password = password.text.toString()

            if(val_email == "" || val_password == ""){
                Toast.makeText(applicationContext,"Enter all credentials to login!!", Toast.LENGTH_SHORT).show()
            }else{
                val body = JSONObject()
                body.put("email",val_email!!)
                body.put("password",val_password!!)
                val api = Constants.BASEURL +"member_signin"
                val helper = ApiHelper(this@SigninActivity)

                helper.post2(api,body, object :ApiHelper.CallBack{
                    override fun onSuccess(result: JSONArray?) {

                    }

                    override fun onSuccess(result: JSONObject?) {
                        if (result!!.has("token")){
                            val member_id = result.getString("member_id")
                            val token = result.getString("token")
                            Toast.makeText(applicationContext,"Login successful", Toast.LENGTH_SHORT).show()
//                            add user data to shared prefs
                            PrefsHelper.savePrefs(applicationContext, "token",token )
                            getMemberData(member_id)

                            startActivity(Intent(applicationContext,MainActivity::class.java))
                        } else {
                            Toast.makeText(applicationContext,"Invalid credentials", Toast.LENGTH_SHORT).show()
                        }

                    }

                    override fun onFailure(result: String?) {
                        Toast.makeText(applicationContext,"An error occured", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }

    private fun getMemberData(member_id: String){
        val body = JSONObject()
        body.put("member_id",member_id)
        val helper = ApiHelper(applicationContext)
        val api = Constants.BASEURL+"member_profile"

        helper.post2(api,body,object : ApiHelper.CallBack{
            override fun onSuccess(result: JSONArray?) {

            }

            override fun onSuccess(result: JSONObject?) {
                if (result!!.has("_id")){
                    PrefsHelper.savePrefs(applicationContext,"member_id", result.getString("_id"))
                    PrefsHelper.savePrefs(applicationContext,"username", result.getString("username"))
                    PrefsHelper.savePrefs(applicationContext,"email", result.getString("email"))
                    PrefsHelper.savePrefs(applicationContext,"phone", result.getString("phone"))
                }
            }

            override fun onFailure(result: String?) {
                Toast.makeText(applicationContext,result.toString(), Toast.LENGTH_SHORT).show()
            }

        })
    }

}