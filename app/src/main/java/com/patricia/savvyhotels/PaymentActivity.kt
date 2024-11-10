package com.patricia.savvyhotels

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.patricia.savvyhotels.constants.Constants
import com.patricia.savvyhotels.helpers.ApiHelper
import org.json.JSONArray
import org.json.JSONObject

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_payment)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val textamount = findViewById<TextView>(R.id.textpay)
        val phoneview  = findViewById<TextView>(R.id.phone)
        val btnpay = findViewById<Button>(R.id.pay)

        val total_amount = intent.extras?.getString("total_amount")
        val invoice_no = intent.extras?.getString("invoice_no")
        textamount.text = "Please pay: 50,000"
        btnpay.setOnClickListener {
            val phone = phoneview.text.toString()
            if (phone.isEmpty() && phone.length < 12){
                Toast.makeText(applicationContext,"Enter a valid phone number", Toast.LENGTH_SHORT).show()
            } else {
                val helper = ApiHelper(applicationContext)
                val api = Constants.BASEURL+"member_make_payment"
                val body = JSONObject()
                body.put("phone",phone)
                body.put("total_amount",total_amount)
                body.put("invoice_no",invoice_no)

                helper.post(api,body, object : ApiHelper.CallBack{
                    override fun onSuccess(result: JSONArray?) {

                    }

                    override fun onSuccess(result: JSONObject?) {
                        Toast.makeText(applicationContext,result.toString(), Toast.LENGTH_SHORT).show()
                        startActivity(Intent(applicationContext,MainActivity::class.java))

                    }

                    override fun onFailure(result: String?) {
                        Toast.makeText(applicationContext,result.toString(), Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }
    }
}