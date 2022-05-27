package com.djt.testapplication

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.function.Predicate

class MainActivity : AppCompatActivity() {

   lateinit var recycler:RecyclerView
   lateinit var adapter: FruitAdapter
   lateinit var textview:TextView
   var listing=""
   var listing2=""
    private var cartArrayList = ArrayList<Fruits>()
    private var cartArrayListcart = ArrayList<Fruits>()
    private var cartsaved = ArrayList<Fruits>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler=findViewById(R.id.recycler)
        textview=findViewById(R.id.gotocart)
        val editor = Constants.getPrefs(application).edit()
        editor.remove(Constants.cartlist)
        editor.apply()


        textview.setOnClickListener {
            val sharedid = Constants.getPrefs(this)
            listing= sharedid.getString(Constants.cartlist,"").toString()

            getlength()


        }

        createjson()

    }

    private fun getlength() {

        if(!listing.equals("")) {
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
        if(cartsaved.size>0){
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }else {
               Toast.makeText(this,"No Items in Cart", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createjson() {

        val jsonarry=JsonArray()
        var childjsonobject = JsonObject()
        childjsonobject.addProperty("itemname","Mango")
        childjsonobject.addProperty("itemprice","120")
        childjsonobject.addProperty("image",R.drawable.mango)
        childjsonobject.addProperty("itemcolor","Yellow")
        jsonarry.add(childjsonobject)
        var childjsonobject2 = JsonObject()
        childjsonobject2.addProperty("itemname","Apple")
        childjsonobject2.addProperty("itemprice","120")
        childjsonobject2.addProperty("image",R.drawable.mango)
        childjsonobject2.addProperty("itemcolor","Yellow")
        jsonarry.add(childjsonobject2)
        var childjsonobject3 = JsonObject()
        childjsonobject3.addProperty("itemname","Banana")
        childjsonobject3.addProperty("itemprice","120")
        childjsonobject3.addProperty("image",R.drawable.mango)
        childjsonobject3.addProperty("itemcolor","Yellow")
        jsonarry.add(childjsonobject3)
        makearrylist(jsonarry.toString())

    }

    private fun makearrylist(body: String) {

        try {
            var arry: JSONArray = JSONArray(body)
            for (i in 0 until arry.length()) {
                var obj: JSONObject = arry.getJSONObject(i)
                var name = obj.getString("itemname")
                var price = obj.getString("itemprice")
                var image = obj.getInt("image")
                var color = obj.getString("itemcolor")
                cartArrayList.add(Fruits(name, price,image, color))
            }
        }catch (e:JSONException){
            e.printStackTrace()

        }
        addrecycler()

    }

    private fun addrecycler() {

        recycler.apply {
            val linearLayoutManager=LinearLayoutManager(this@MainActivity)
            recycler.setLayoutManager(linearLayoutManager)
            adapter=FruitAdapter(this@MainActivity,cartArrayList,"home")
            recycler.setAdapter(adapter)
            (adapter as FruitAdapter).setListener(object :
                FruitAdapter.CartListener {
                @RequiresApi(Build.VERSION_CODES.N)
                override fun additem(
                    name: String,
                    price: String,
                    color: String,
                    image: Int

                ) {

                    if(!cartArrayListcart.isEmpty()) {
                        if(cartArrayListcart.size<cartArrayList.size) {
//                            for (i in 0 until cartArrayListcart.size) {
                                if (!containsName(cartArrayListcart,name)) {
                                    Toast.makeText(this@MainActivity, name+" added", Toast.LENGTH_SHORT).show()
                                    //  cartArrayListcart.removeAt(i)
                                    cartArrayListcart.add(
                                        Fruits(
                                            name,
                                            price,
                                              image,
                                            color
                                        )
                                    )
                                } else {

                                    Toast.makeText(
                                        this@MainActivity,
                                        "Already present in cart",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                           // }
                        }else{

                            Toast.makeText(
                                this@MainActivity,
                                "Already present in cart",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }else{
                            Toast.makeText(this@MainActivity, "added", Toast.LENGTH_SHORT).show()

                        cartArrayListcart.add(
                            Fruits(
                                name,
                                price,
                                image,
                                color
                            )
                        )
                    }
                //    Toast.makeText(this@MainActivity, "added", Toast.LENGTH_SHORT).show()
                    val memberlist = Constants.getPrefs(this@MainActivity).edit()
                    val gson = Gson()
                    val json = gson.toJson(cartArrayListcart)
                    memberlist.putString(Constants.cartlist, json).commit()
                }
            })
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun containsName(list: List<Fruits>, name: String?): Boolean {
        return list.stream().filter(Predicate<Fruits> { o: Fruits ->
            o.fruits.equals(name)
        }).findFirst().isPresent()
    }
}