package com.patricia.savvyhotels.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.patricia.savvyhotels.FilteredroomsActivity
import com.patricia.savvyhotels.R
import com.patricia.savvyhotels.Singlerooms
import com.patricia.savvyhotels.models.Room

class FilteredAdapter(var context: Context):
    RecyclerView.Adapter<FilteredAdapter.ViewHolder>() {
    //Create a List and connect it with our model
    var itemList : List<Room> = listOf() //Its empty

    //Create a Class here, will hold our views in single_lab xml
    inner class  ViewHolder(itemView: View):  RecyclerView.ViewHolder(itemView)

    //Access single_lab XML
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilteredAdapter.ViewHolder {
        //access/inflate the single lab xml
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.single_filtered,
            parent, false)

        return ViewHolder(view) //pass the single lab to ViewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val room_name = holder.itemView.findViewById<MaterialTextView>(R.id.room_name)
        val room_category = holder.itemView.findViewById<MaterialTextView>(R.id.room_category)
        val room_desc = holder.itemView.findViewById<MaterialTextView>(R.id.room_desc)
        val no_guests = holder.itemView.findViewById<MaterialTextView>(R.id.no_guests)
        val status = holder.itemView.findViewById<MaterialTextView>(R.id.status)
        val room_cost = holder.itemView.findViewById<MaterialTextView>(R.id.room_cost)

        //Assume one Lab, and bind data, it will loop other Labs
        val room = itemList[position]
        room_name.text = room.room_name
        room_category.text = room.room_category
        room_desc.text = room.room_desc
        no_guests.text = room.no_guests
        status.text = room.status
        room_cost.text = room.room_cost

        //When one Lab is clicked, Move to Lab tests Activity
        holder.itemView.setOnClickListener {
            //To Navigate to LabTests on each Lab Click
//            var id = room._id
//            var i = Intent(context, FilteredroomsActivity:: class.java)
//            i.putExtra("key",id)
//            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            context.startActivity(i)

        }
    }

    //Count Number of items
    override fun getItemCount(): Int {
        return itemList.size  //Count how may Items in the List
    }



    //This is for filtering data
    fun filterList(filterList: List<Room>){
        itemList = filterList
        notifyDataSetChanged()
    }


    //Earlier we mentioned item List is empty!
    //We will get data from our APi, then bring it to below function
    //The data you bring here must follow the Lab model
    fun setListItems(data: List<Room>){
        itemList = data //map/link the data to itemlist
        notifyDataSetChanged()
        //Tell this adapter class that now itemList is loaded with data
    }
    //justpaste.it/cgaym
}