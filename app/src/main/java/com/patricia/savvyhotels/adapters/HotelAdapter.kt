package com.patricia.savvyhotels.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.patricia.savvyhotels.PaymentActivity
import com.patricia.savvyhotels.R
import com.patricia.savvyhotels.RoomActivity
import com.patricia.savvyhotels.SearchroomActivity
import com.patricia.savvyhotels.helpers.PrefsHelper
import com.patricia.savvyhotels.models.Hotel

class HotelAdapter(var context: Context):
    RecyclerView.Adapter<HotelAdapter.ViewHolder>() {
    //Create a List and connect it with our model
    var itemList : List<Hotel> = listOf() //Its empty

    //Create a Class here, will hold our views in single_lab xml
    inner class  ViewHolder(itemView: View):  RecyclerView.ViewHolder(itemView){
        val hotel_name = itemView.findViewById<MaterialTextView>(R.id.hotel_name)
        val hotel_desc = itemView.findViewById<MaterialTextView>(R.id.hotel_desc)
        val category_hotel = itemView.findViewById<MaterialTextView>(R.id.hotel_category)
        val hotel_location = itemView.findViewById<MaterialTextView>(R.id.hotel_location)
        val all_rooms: MaterialButton = itemView.findViewById(R.id.all_rooms)
        val search_room: MaterialButton = itemView.findViewById(R.id.search_room)

    }

    //Access single_lab XML
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelAdapter.ViewHolder {
        //access/inflate the single lab xml
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.single_hotel,
            parent, false)

        return ViewHolder(view) //pass the single lab to ViewHolder
    }

    // Bind data to Views from single_lab XML
    override fun onBindViewHolder(holder: HotelAdapter.ViewHolder, position: Int) {
        //Find your 3 text views
        val hotel_name = holder.itemView.findViewById<MaterialTextView>(R.id.hotel_name)
        val hotel_desc = holder.itemView.findViewById<MaterialTextView>(R.id.hotel_desc)
        val category_hotel = holder.itemView.findViewById<MaterialTextView>(R.id.hotel_category)
        val hotel_location = holder.itemView.findViewById<MaterialTextView>(R.id.hotel_location)
        //Assume one Lab, and bind data, it will loop other Labs
        val hotel = itemList[position]
        hotel_name.text = hotel.hotel_name
        hotel_desc.text = hotel.hotel_desc
        hotel_location.text = "Located at "+hotel.location
        category_hotel.text = hotel.category_hotel
        //When one Lab is clicked, Move to Lab tests Activity
        holder.itemView.setOnClickListener {
//            To Navigate to LabTests on each Lab Click
            var id = hotel._id
            var i = Intent(context, RoomActivity:: class.java)
            i.putExtra("key",id)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(i)

        }

        holder.all_rooms.setOnClickListener {
            Toast.makeText(
                context,
                "Loading page",
                Toast.LENGTH_SHORT
            ).show()
            var id = hotel._id
            var hotel_name = hotel.hotel_name
            val x = Intent(context, RoomActivity::class.java)
            x.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            x.putExtra("key",id)
            x.putExtra("hotel_name",hotel_name)
            context.startActivity(x)

        }

        holder.search_room.setOnClickListener {
            Toast.makeText(
                context,
                "Loading page",
                Toast.LENGTH_SHORT
            ).show()
            var id = hotel._id
            val x = Intent(context, SearchroomActivity::class.java)
            x.putExtra("hotel_id",id)
            x.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(x)

        }

    }
    //Count Number of items
    override fun getItemCount(): Int {
        return itemList.size  //Count how may Items in the List
    }



    //This is for filtering data
    fun filterList(filterList: List<Hotel>){
        itemList = filterList
        notifyDataSetChanged()
    }


    //Earlier we mentioned item List is empty!
    //We will get data from our APi, then bring it to below function
    //The data you bring here must follow the Lab model
    fun setListItems(data: List<Hotel>){
        itemList = data //map/link the data to itemlist
        notifyDataSetChanged()
        //Tell this adapter class that now itemList is loaded with data
    }
    //justpaste.it/cgaym
}