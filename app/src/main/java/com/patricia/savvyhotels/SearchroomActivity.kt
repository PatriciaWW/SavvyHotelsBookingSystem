package com.patricia.savvyhotels

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SearchroomActivity : AppCompatActivity() {

    private lateinit var buttonDatePicker: Button
    private lateinit var editTextDate: EditText
    private lateinit var buttonDatepicker: Button
    private lateinit var editTextdate: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_searchroom)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val noGuests: TextInputEditText = findViewById(R.id.noGuests)
        val noRooms: TextInputEditText = findViewById((R.id.noRooms))

        val hotel_name = intent.extras?.getString("hotel_name")

        val lets_go: MaterialButton = findViewById(R.id.lets_go)

        buttonDatePicker= findViewById(R.id.buttonDatePicker)
        editTextDate = findViewById(R.id.editTextDate)


        buttonDatePicker.setOnClickListener{
            showDatePickerDialog(editTextDate)
        }

        buttonDatepicker= findViewById(R.id.buttonDatepicker)
        editTextdate = findViewById(R.id.editTextdate)

        buttonDatepicker.setOnClickListener{
            showDatePickerDialog(editTextdate)
        }

        lets_go.setOnClickListener {

            val x = Intent(applicationContext,FilteredroomsActivity::class.java)
            x.putExtra("noGuests", noGuests.text.toString())
            x.putExtra("noRooms", noRooms.text.toString())
            val hotel_id = intent.extras?.getString("hotel_id")
            x.putExtra("hotel_id", hotel_id)
            startActivity(x)

//            helper.post(api,body,object: ApiHelper.CallBack{
//                override fun onSuccess(result: JSONArray?) {
//                    Toast.makeText(applicationContext, result.toString(), Toast.LENGTH_SHORT).show()
//                    startActivity(Intent(applicationContext,FilteredroomsActivity::class.java))
//                }
//
//                override fun onSuccess(result: JSONObject?) {
//
//                }
//
//                override fun onFailure(result: String?) {
//                    Toast.makeText(applicationContext, result.toString(), Toast.LENGTH_SHORT).show()                    }
//            })
//
//
//            val api2 = Constants.BASEURL+"book_room"
//            val body2 = JSONObject()
//            body2.put("no_guests", noGuests.text.toString())
//            body2.put("checkin_date", editTextDate.text.toString())
//            body2.put("checkout_date", editTextdate.text.toString())
//
//            helper.post(api2,body2,object: ApiHelper.CallBack{
//                override fun onSuccess(result: JSONArray?) {
//
//                }
//
//                override fun onSuccess(result: JSONObject?) {
//                    Toast.makeText(applicationContext, result.toString(), Toast.LENGTH_SHORT).show()
//                }
//
//                override fun onFailure(result: String?) {
//                    Toast.makeText(applicationContext, result.toString(), Toast.LENGTH_SHORT).show()                    }
//            })
        }
    }

    private fun showDatePickerDialog(editTextDate: EditText){
        val calendar =  Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            {
                    _: DatePicker, year: Int, month: Int, day: Int ->
                val selectedDate = formatDate(year,month,day)
                editTextDate.setText(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.datePicker.maxDate = System.currentTimeMillis() - 1000
        datePickerDialog.show()
    }

    private fun formatDate (year: Int, month:Int, day:Int): String{
        val calendar = java.util.Calendar.getInstance()
        calendar.set(year,month, day)
        val dateFormat = SimpleDateFormat("yyyy-mm-dd", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }
}