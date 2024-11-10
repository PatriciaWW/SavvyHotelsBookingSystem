package com.patricia.savvyhotels

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.button.MaterialButton
import com.google.gson.GsonBuilder
import com.patricia.savvyhotels.adapters.FilteredAdapter
import com.patricia.savvyhotels.adapters.HotelAdapter
import com.patricia.savvyhotels.adapters.RoomAdapter
import com.patricia.savvyhotels.constants.Constants
import com.patricia.savvyhotels.helpers.ApiHelper
import com.patricia.savvyhotels.models.Hotel
import com.patricia.savvyhotels.models.Room
import org.json.JSONArray
import org.json.JSONObject

class FilteredroomsActivity : AppCompatActivity() {

    lateinit var itemList: List<Room>
    lateinit var filteredAdapter: FilteredAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var swipe_refresh: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_filteredrooms)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        progressBar = findViewById(R.id.progressbar)
        recyclerView = findViewById(R.id.recycler)
        filteredAdapter = FilteredAdapter(applicationContext)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.setHasFixedSize(true)
        getFilteredRooms()

        swipe_refresh = findViewById(R.id.swipe_refresh)
        swipe_refresh.setOnRefreshListener {
            getFilteredRooms()

        }

        val book_package = findViewById<MaterialButton>(R.id.bookpackage)
        book_package.setOnClickListener{
            startActivity(Intent(applicationContext,PaymentActivity::class.java))
        }

    }

    private fun getFilteredRooms() {

        val hotel_id = intent.extras?.getString("hotel_id")
        println(hotel_id)
        Log.d("test", hotel_id.toString())
        val noGuests = intent.extras?.getString("noGuests")
        println(noGuests)
        Log.d("test", noGuests.toString())
        val noRooms = intent.extras?.getString("noRooms")
        println(noRooms)
        Log.d("test", noRooms.toString())
        val body = JSONObject()

        body.put("noGuests",noGuests)
        body.put("noRooms",noRooms)
        body.put("hotel_id",hotel_id)
        println(body)
        val helper = ApiHelper(applicationContext)
        val api = Constants.BASEURL+"search_room"

        helper.post(api,body,object : ApiHelper.CallBack{
            override fun onSuccess(result: JSONArray?) {
                val gson = GsonBuilder().create()
                itemList = gson.fromJson(result.toString(), Array<Room>::class.java).toList()

//                pass data to the lab adapter
                filteredAdapter.setListItems(itemList)
                recyclerView.adapter = filteredAdapter
                progressBar.visibility = View.GONE
                swipe_refresh.isRefreshing = false

            }

            override fun onSuccess(result: JSONObject?) {
                val txt = result?.get("message")
                Toast.makeText(applicationContext,"Error: "+txt.toString(), Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.GONE
                swipe_refresh.isRefreshing = false
            }

            override fun onFailure(result: String?) {
                Toast.makeText(applicationContext,"Error: "+result.toString(), Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.GONE
                swipe_refresh.isRefreshing = false
            }

        })

    }
}