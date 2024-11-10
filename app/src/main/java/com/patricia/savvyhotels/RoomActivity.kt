package com.patricia.savvyhotels

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.textview.MaterialTextView
import com.google.gson.GsonBuilder
import com.patricia.savvyhotels.adapters.RoomAdapter
import com.patricia.savvyhotels.constants.Constants
import com.patricia.savvyhotels.helpers.ApiHelper
import com.patricia.savvyhotels.helpers.PrefsHelper
import com.patricia.savvyhotels.models.Room
import org.json.JSONArray
import org.json.JSONObject

class RoomActivity : AppCompatActivity() {

    lateinit var itemList: List<Room>
    lateinit var roomAdapter: RoomAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var swipe_refresh: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_room)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 40 , systemBars.right, systemBars.bottom)
            insets
        }

        progressBar = findViewById(R.id.progressbar)
        recyclerView = findViewById(R.id.recycler)
        roomAdapter = RoomAdapter(applicationContext)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.setHasFixedSize(true)
        post_fetch()

        swipe_refresh = findViewById(R.id.swipe_refresh)
        swipe_refresh.setOnRefreshListener {
            post_fetch()

        }

        val hotel_name = intent.extras?.getString("hotel_name")

        val search_text = findViewById<EditText>(R.id.search_room)
        search_text.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(texttyped: CharSequence?, start: Int, before: Int, count: Int) {
                filter(texttyped.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }
        } )
    }

    fun post_fetch(){
        val helper = ApiHelper(applicationContext)
        val api = Constants.BASEURL+"rooms"

//        retrieve from intent extras
        val id = intent.extras?.getString("key")
        val body = JSONObject()
        body.put("hotel_id",id)


        helper.post(api, body,object: ApiHelper.CallBack{
            override fun onSuccess(result: JSONArray?) {
//                convert json array into a list using json - added in gradle
                val gson = GsonBuilder().create()
                itemList = gson.fromJson(result.toString(), Array<Room>::class.java).toList()

//                pass data to the lab adapter
                roomAdapter.setListItems(itemList)
                recyclerView.adapter = roomAdapter
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

    private fun filter (text:String) {
        val filteredList: ArrayList<Room> = ArrayList()
        for (item in itemList) {
            if (item.room_category.lowercase().contains(text.lowercase())) {
                filteredList.add(item)
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No data matched your search query", Toast.LENGTH_SHORT).show()
        } else{
            roomAdapter.filterList(filteredList)
        }
    }
}