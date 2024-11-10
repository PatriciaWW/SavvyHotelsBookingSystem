package com.patricia.savvyhotels.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.patricia.savvyhotels.R
import com.patricia.savvyhotels.models.Food
import com.patricia.savvyhotels.models.Hotel

class FoodAdapter(var context: Context):
    RecyclerView.Adapter<FoodAdapter.ViewHolder>() {
    //Create a List and connect it with our model
    var itemList : List<Food> = listOf() //Its empty

    //Create a Class here, will hold our views in single_lab xml
    inner class  ViewHolder(itemView: View):  RecyclerView.ViewHolder(itemView)

    //Access single_lab XML
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodAdapter.ViewHolder {
        //access/inflate the single lab xml
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.single_menu,
            parent, false)

        return ViewHolder(view) //pass the single lab to ViewHolder
    }

    override fun onBindViewHolder(holder: FoodAdapter.ViewHolder, position: Int) {
        val food_name = holder.itemView.findViewById<MaterialTextView>(R.id.menu_name)
        val food_cost = holder.itemView.findViewById<MaterialTextView>(R.id.menu_cost)

        val food = itemList[position]
        food_name.text = food.food_name
        food_cost.text = food.food_cost

        //When one Lab is clicked, Move to Lab tests Activity
        holder.itemView.setOnClickListener {
            //To Navigate to LabTests on each Lab Click
//            var id = hotel._id
//            var i = Intent(context, LabTestsActivity:: class.java)
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
    fun filterList(filterList: List<Food>){
        itemList = filterList
        notifyDataSetChanged()
    }


    //Earlier we mentioned item List is empty!
    //We will get data from our APi, then bring it to below function
    //The data you bring here must follow the Lab model
    fun setListItems(data: List<Food>){
        itemList = data //map/link the data to itemlist
        notifyDataSetChanged()
        //Tell this adapter class that now itemList is loaded with data
    }
    //justpaste.it/cgaym
}