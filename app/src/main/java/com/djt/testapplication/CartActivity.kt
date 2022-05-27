package com.djt.testapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import org.json.JSONArray

class CartActivity : AppCompatActivity() {

    lateinit var recycler: RecyclerView
    lateinit var adapter: FruitAdapter
    var listing = ""
    private var cartsaved = ArrayList<Fruits>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        recycler = findViewById(R.id.recycler)
        val sharedid = Constants.getPrefs(this)
        listing = sharedid.getString(Constants.cartlist, "").toString()
        getlength()


    }

    private fun getlength() {

        if (!listing.equals("")) {
            var jArray: JSONArray = JSONArray(listing)
            for (i in 0 until jArray.length()) {

                var jobj = jArray.getJSONObject(i)
                var name = jobj.getString("fruits")
                var color = jobj.getString("color")
                var price = jobj.getString("price")
                var image = jobj.getInt("image")
                cartsaved.add(Fruits(name, price, image, color))

                //  }
            }
        }
        addrecycler()
    }


    private fun addrecycler() {

        recycler.apply {
            val linearLayoutManager = LinearLayoutManager(this@CartActivity)
            recycler.setLayoutManager(linearLayoutManager)
            adapter = FruitAdapter(this@CartActivity, cartsaved, "cart")
            recycler.setAdapter(adapter)

        }
    }
}