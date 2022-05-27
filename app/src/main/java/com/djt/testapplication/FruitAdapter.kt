package com.djt.testapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FruitAdapter(

    var context: Context,
    private var fruitlist: ArrayList<Fruits>,
    private var from: String

): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listener: FruitAdapter.CartListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FruitViewHolder {

    return FruitViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false))

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FruitViewHolder-> {
                var fruititems = fruitlist.get(position)
                holder.name.text=fruititems.fruits
                holder.price.text=fruititems.price
                holder.color.text=fruititems.color
                holder.image.setImageResource(fruititems.image)

                if(from.equals("home")) {
                    holder.add.visibility=View.VISIBLE
                }
                else{
                    holder.add.visibility=View.GONE
                }
                holder.add.setOnClickListener {
                    listener?.additem(fruititems.fruits,fruititems.price,fruititems.color,fruititems.image)

                }
            }
        }

    }

    override fun getItemCount(): Int {

        return fruitlist.size
    }

    inner class FruitViewHolder(view: View):RecyclerView.ViewHolder(view){

        var name: TextView = view.findViewById(R.id.textname)
        var add: TextView = view.findViewById(R.id.textadd)
        var price: TextView = view.findViewById(R.id.textprice)
        var color: TextView = view.findViewById(R.id.textcolor)
        var image: ImageView = view.findViewById(R.id.imagev)

    }
    fun setListener(listener: CartListener) {
        this.listener = listener
    }

    interface CartListener {
        fun additem(
            name: String,
            price: String,
            color: String,
            image:Int

        )

    }
}
