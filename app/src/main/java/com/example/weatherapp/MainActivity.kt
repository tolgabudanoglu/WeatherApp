package com.example.weatherapp


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject
import com.android.volley.toolbox.Volley
import com.android.volley.Request;
import com.android.volley.VolleyError


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val url = "https://api.openweathermap.org/data/2.5/weather?q=Mugla&appid=dbebd51d2e67e2d589db303fb923efad&lang=tr"
        val havaDurumuObjeRequest = JsonObjectRequest(Request.Method.GET, url, null,object : Response.Listener<JSONObject> {


            override fun onResponse(response: JSONObject?) {

                Toast.makeText(this@MainActivity,response.toString(),Toast.LENGTH_LONG).show()



            }


        }, object : Response.ErrorListener {

            override fun onErrorResponse(error: VolleyError?) {

            }

        })


        MySingleton.getInstance(this).addToRequestQueue(havaDurumuObjeRequest)
    }





}
