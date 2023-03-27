package com.example.weatherapp


import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject
import com.android.volley.toolbox.Volley
import com.android.volley.Request;
import com.android.volley.VolleyError
import com.example.weatherapp.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val url = "https://api.openweathermap.org/data/2.5/weather?q=Mugla&appid=dbebd51d2e67e2d589db303fb923efad&lang=tr&units=metric"
        val weatherObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,object : Response.Listener<JSONObject> {


            override fun onResponse(response: JSONObject?) {

                var main = response?.getJSONObject("main")
                var tempereture = main?.getInt("temp")
                binding.tvTemp.text = tempereture.toString()

                var cityName = response?.getString("name")

                var weather=response?.getJSONArray("weather")
                var descrition = weather?.getJSONObject(0)?.getString("description")

                binding.tvDescription.text = descrition
                var icon = weather?.getJSONObject(0)?.getString("icon")

                if (icon?.last() == 'd'){
                    binding.rootLayout.background = getDrawable(R.drawable.bg)
                }else{
                    binding.rootLayout.background = getDrawable(R.drawable.gece)
                }



                var imageFile = resources.getIdentifier("icon_"+icon?.lastDigitDelete(),"drawable",packageName)

                binding.imgWeather.setImageResource(imageFile)





            }


        }, object : Response.ErrorListener {

            override fun onErrorResponse(error: VolleyError?) {

            }

        })


        MySingleton.getInstance(this).addToRequestQueue(weatherObjectRequest)
    }

    fun history(){
        var calendar = Calendar.getInstance()
        var format=SimpleDateFormat("EEEE, MMMM yyyy", Locale("tr"))
    }

    private fun String.lastDigitDelete(): String{

        return this.substring(0,this.length-1)
    }





}
