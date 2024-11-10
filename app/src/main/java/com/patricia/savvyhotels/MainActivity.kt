package com.patricia.savvyhotels

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.GsonBuilder
import com.patricia.savvyhotels.adapters.HotelAdapter
import com.patricia.savvyhotels.constants.Constants
import com.patricia.savvyhotels.helpers.ApiHelper
import com.patricia.savvyhotels.helpers.PrefsHelper
import com.patricia.savvyhotels.models.Hotel
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    lateinit var itemList: List<Hotel>
    lateinit var hotelAdapter: HotelAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var swipe_refresh: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 40, systemBars.right, systemBars.bottom)
            insets
        }

        progressBar = findViewById(R.id.progressbar)
        recyclerView = findViewById(R.id.recycler)
        hotelAdapter = HotelAdapter(applicationContext)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.setHasFixedSize(true)
        fetchData()

        swipe_refresh = findViewById(R.id.swipe_refresh)
        swipe_refresh.setOnRefreshListener {
            fetchData()
        }

        val search_text = findViewById<TextInputEditText>(R.id.search_hotel_location)
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

    fun fetchData(){
        val helper = ApiHelper(this@MainActivity)
        val api = Constants.BASEURL+"hotels"

        helper.get(api, object: ApiHelper.CallBack{
            override fun onSuccess(result: JSONArray?) {
//                convert json array into a list using json - added in gradle
                val gson = GsonBuilder().create()
                itemList = gson.fromJson(result.toString(), Array<Hotel>::class.java).toList()

//                pass data to the lab adapter
                hotelAdapter.setListItems(itemList)
                recyclerView.adapter = hotelAdapter
                progressBar.visibility = View.GONE
                swipe_refresh.isRefreshing = false

            }

            override fun onSuccess(result: JSONObject?) {
                val txt = result?.getString("message")
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
        val filteredList: ArrayList<Hotel> = ArrayList()
        for (item in itemList) {
            if (item.location.lowercase().contains(text.lowercase())) {
                filteredList.add(item)
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No data matched your search query", Toast.LENGTH_SHORT).show()
        } else{
            hotelAdapter.filterList(filteredList)
        }
    }


//    when creating a  drop down menu

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main,menu)
        var item : MenuItem = menu!!.findItem(R.id.mycart)
        item.setActionView(R.layout.design)
        val actionView: View? = item.actionView
//        `val number = actionView?.findViewById<TextView>(R.id.badge)`
        val image = actionView?.findViewById<ImageView>(R.id.image)

        val login: MenuItem = menu!!.findItem(R.id.login)
        val profile: MenuItem = menu!!.findItem(R.id.profile)
        val logout: MenuItem = menu!!.findItem(R.id.logout)
        val register: MenuItem = menu!!.findItem(R.id.register)



        val username = PrefsHelper.getPrefs(applicationContext,"username")



        image?.setOnClickListener{
            startActivity(Intent(applicationContext,Bookings::class.java))

        }
//        val helper = SQLiteCartHelper(applicationContext)
//        number?.text = "" + helper.getNumItems()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.login){
            startActivity(Intent(applicationContext,SigninActivity::class.java))
        }
        else if(item.itemId == R.id.register){
            startActivity(Intent(applicationContext,SignupActivity::class.java))
        }else if(item.itemId == R.id.profile){
//            startActivity(Intent())
        }else if(item.itemId == R.id.logout){
            PrefsHelper.clearPrefs(applicationContext)
            finishAffinity()
//
            startActivity(Intent(applicationContext,SigninActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}