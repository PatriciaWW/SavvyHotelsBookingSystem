package com.patricia.savvyhotels

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

class Singlerooms : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_singlerooms)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 50, systemBars.right, systemBars.bottom)
            insets
        }

        val room_name: MaterialTextView = findViewById(R.id.room_name)
        val room_category: MaterialTextView = findViewById(R.id.room_category)
        val room_desc: MaterialTextView = findViewById(R.id.room_desc)
        val room_cost: MaterialTextView = findViewById(R.id.room_cost)
        val status: MaterialTextView = findViewById(R.id.status)
        val no_guests: MaterialTextView = findViewById(R.id.no_guests)
        val bookroom: MaterialButton = findViewById(R.id.bookroom)


        val val_room_name = intent.extras?.getString("room_name")
        val val_room_category = intent.extras?.getString("room_category")
        val val_room_desc = intent.extras?.getString("room_desc")
        val val_room_cost = intent.extras?.getString("room_cost")
        val val_status = intent.extras?.getString("status")
        val val_no_guests = intent.extras?.getString("no_guests")
        val val_reg_date = intent.extras?.getString("reg_date")
        val val_hotel_id = intent.extras?.getString("hotel_id")
        val val_id = intent.extras?.getString("_id")

        room_name.text = val_room_name
        room_category.text = val_room_category
        room_desc.text = val_room_desc
        room_cost.text = val_room_cost
        status.text = val_status
        no_guests.text = val_no_guests

//        bookroom.setOnClickListener{
//            val helper = SQLiteCartHelper(applicationContext)
//            val lab_id = intent.extras?.getString("lab_id")
//            try{
//                helper.insert(val_id!!,val_room_name!!,val_room_category!!,val_room_desc!!,val_room_cost!!,val_status!!,val_no_guests!!,val_hotel_id!!)
//            }
//            catch(e: Exception){
//                Toast.makeText(applicationContext,"An error occured", Toast.LENGTH_SHORT).show()
//            }
//    }

    }
}