package com.example.weatherapp


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.example.weatherapp.databinding.ActivityMainBinding
import im.delight.android.location.SimpleLocation
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() , AdapterView.OnItemSelectedListener{


    var location:SimpleLocation?=null
    var latitude:String? = null
    var longitude:String? = null


    private lateinit var binding: ActivityMainBinding
    private fun currentCity(lat: String?, longitude: String?) {

        val url = "https://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+longitude+"&appid=dbebd51d2e67e2d589db303fb923efad&lang=tr&units=metric"
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
                    binding.tvHistory.setTextColor(resources.getColor(R.color.colorAccent))
                    binding.tvDescription.setTextColor(resources.getColor(R.color.colorAccent))
                    binding.tvCelcius.setTextColor(resources.getColor(R.color.colorAccent))
                    binding.tvTemp.setTextColor(resources.getColor(R.color.colorAccent))
                }



                var imageFile = resources.getIdentifier("icon_"+icon?.lastDigitDelete(),"drawable",packageName)

                binding.imgWeather.setImageResource(imageFile)

                binding.tvHistory.text = history()





            }


        }, object : Response.ErrorListener {

            override fun onErrorResponse(error: VolleyError?) {

            }

        })


        MySingleton.getInstance(this).addToRequestQueue(weatherObjectRequest)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var spinnerAdapter = ArrayAdapter.createFromResource(this,R.array.turkey_city, android.R.layout.simple_spinner_item)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spnCity.adapter = spinnerAdapter

        binding.spnCity.onItemSelectedListener = this

        location = SimpleLocation(this)

        if (!location!!.hasLocationEnabled()) {
            // ask the user to enable location access
            SimpleLocation.openSettings(this);
        }
        location?.setListener(object :SimpleLocation.Listener{
            override fun onPositionChanged() {

                 latitude = String.format("%.2f",location?.latitude)
                 longitude = String.format("%.2f",location?.longitude)

                Log.e("tolgaaaaaaaa ",""+latitude + " " + longitude)
            }

        })



        data("muğla")
        currentCity(latitude,longitude)



    }

    override fun onResume() {
        super.onResume()
        location?.beginUpdates()
    }

    override fun onPause() {
        super.onPause()

        location?.endUpdates()
    }

    fun data(sehir:String){

        val url = "https://api.openweathermap.org/data/2.5/weather?q="+sehir+"&appid=dbebd51d2e67e2d589db303fb923efad&lang=tr&units=metric"
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
                    binding.tvHistory.setTextColor(resources.getColor(R.color.colorAccent))
                    binding.tvDescription.setTextColor(resources.getColor(R.color.colorAccent))
                    binding.tvCelcius.setTextColor(resources.getColor(R.color.colorAccent))
                    binding.tvTemp.setTextColor(resources.getColor(R.color.colorAccent))
                }



                var imageFile = resources.getIdentifier("icon_"+icon?.lastDigitDelete(),"drawable",packageName)

                binding.imgWeather.setImageResource(imageFile)

                binding.tvHistory.text = history()





            }


        }, object : Response.ErrorListener {

            override fun onErrorResponse(error: VolleyError?) {

            }

        })


        MySingleton.getInstance(this).addToRequestQueue(weatherObjectRequest)
    }

    fun history():String{
        var calendar = Calendar.getInstance().time
        var format=SimpleDateFormat("EEEE, MMMM yyyy", Locale("tr"))
        var history=format.format(calendar)

        return history
    }

    private fun String.lastDigitDelete(): String{

        return this.substring(0,this.length-1)
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        var selectedCity=p0?.getItemAtPosition(p2).toString()
        data(selectedCity)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }


}


