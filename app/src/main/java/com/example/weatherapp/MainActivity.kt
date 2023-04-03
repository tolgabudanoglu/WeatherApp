package com.example.weatherapp


import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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

    var tvSehir: TextView? = null
    var location:SimpleLocation?=null
    var latitude:String? = null
    var longitude:String? = null


    private lateinit var binding: ActivityMainBinding
    private fun currentCity(lat: String?, longitude: String?) :String? {

        val url = "https://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+longitude+"&appid=dbebd51d2e67e2d589db303fb923efad&lang=tr&units=metric"



        var cityName: String? = "Şuanki Yer"
        val weatherObjectRequest2 = JsonObjectRequest(Request.Method.GET, url, null,object : Response.Listener<JSONObject> {




            override fun onResponse(response: JSONObject?) {



                var main = response?.getJSONObject("main")
                var tempereture = main?.getInt("temp")
                var feelsLike = main?.getInt("feels_like")
                var tempMin = main?.getInt("temp_min")
                var tempMax = main?.getInt("temp_max")
                var pressure = main?.getInt("pressure")
                var humidity = main?.getInt("humidity")
                binding.tvTemp.text = tempereture.toString()
                binding.tvhumidity.text = humidity.toString().plus(" %")
                binding.tvFeelsTemp.text = feelsLike.toString().plus("°C")
                binding.tvLowTemp.text = tempMin.toString().plus("°C")
                binding.tvMaxTemp.text = tempMax.toString().plus("°C")
                binding.tvPressure.text = pressure.toString()


                var wind = response?.getJSONObject("wind")
                var windSpeed = wind?.getInt("speed")
                binding.tvWindSpeed.text = windSpeed.toString().plus(" km/s")




                cityName= response?.getString("name")
                tvSehir?.setText(cityName)

                var weather=response?.getJSONArray("weather")
                var descrition = weather?.getJSONObject(0)?.getString("description")

                binding.tvDescription.text = descrition
                var icon = weather?.getJSONObject(0)?.getString("icon")

                if (icon?.last() == 'd'){
                    binding.rootLayout.background = getDrawable(R.drawable.sunday2)
                    binding.tvHistory.setTextColor(resources.getColor(R.color.indigo))
                    binding.tvDescription.setTextColor(resources.getColor(R.color.indigo))
                    binding.tvCelcius.setTextColor(resources.getColor(R.color.indigo))
                    binding.tvTemp.setTextColor(resources.getColor(R.color.indigo))
                    binding.tvhumidity.setTextColor(resources.getColor(R.color.indigo))
                    binding.tvFeelsTemp.setTextColor(resources.getColor(R.color.indigo))
                    binding.tvWindSpeed.setTextColor(resources.getColor(R.color.indigo))
                    binding.tvLowTemp.setTextColor(resources.getColor(R.color.indigo))
                    binding.tvMaxTemp.setTextColor(resources.getColor(R.color.indigo))
                    binding.tvPressure.setTextColor(resources.getColor(R.color.indigo))
                    binding.textView11.setTextColor(resources.getColor(R.color.indigo))
                    binding.textView13.setTextColor(resources.getColor(R.color.indigo))
                    binding.textView15.setTextColor(resources.getColor(R.color.indigo))
                    binding.textView6.setTextColor(resources.getColor(R.color.indigo))
                    binding.textView8.setTextColor(resources.getColor(R.color.indigo))
                    binding.textView.setTextColor(resources.getColor(R.color.indigo))
                    tvSehir?.setTextColor(resources.getColor(R.color.indigo))
                    binding.spnCity.background.setColorFilter(resources.getColor(R.color.indigo), PorterDuff.Mode.SRC_ATOP)
                }else{
                    binding.rootLayout.background = getDrawable(R.drawable.night)
                    binding.tvHistory.setTextColor(resources.getColor(R.color.colorAccent))
                    binding.tvDescription.setTextColor(resources.getColor(R.color.colorAccent))
                    binding.tvCelcius.setTextColor(resources.getColor(R.color.colorAccent))
                    binding.tvTemp.setTextColor(resources.getColor(R.color.colorAccent))
                    binding.tvhumidity.setTextColor(resources.getColor(R.color.colorAccent))
                    binding.tvFeelsTemp.setTextColor(resources.getColor(R.color.colorAccent))
                    binding.tvWindSpeed.setTextColor(resources.getColor(R.color.colorAccent))
                    binding.tvLowTemp.setTextColor(resources.getColor(R.color.colorAccent))
                    binding.tvMaxTemp.setTextColor(resources.getColor(R.color.colorAccent))
                    binding.tvPressure.setTextColor(resources.getColor(R.color.colorAccent))
                    binding.textView11.setTextColor(resources.getColor(R.color.colorAccent))
                    binding.textView13.setTextColor(resources.getColor(R.color.colorAccent))
                    binding.textView15.setTextColor(resources.getColor(R.color.colorAccent))
                    binding.textView6.setTextColor(resources.getColor(R.color.colorAccent))
                    binding.textView8.setTextColor(resources.getColor(R.color.colorAccent))
                    binding.textView.setTextColor(resources.getColor(R.color.colorAccent))
                    tvSehir?.setTextColor(resources.getColor(R.color.colorAccent))
                    binding.spnCity.background.setColorFilter(resources.getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP)

                }



                var imageFile = resources.getIdentifier("icon_"+icon?.lastDigitDelete(),"drawable",packageName)

                binding.imgWeather.setImageResource(imageFile)

                binding.tvHistory.text = history()





            }


        }, object : Response.ErrorListener {

            override fun onErrorResponse(error: VolleyError?) {

            }

        })


        MySingleton.getInstance(this).addToRequestQueue(weatherObjectRequest2)

        if(cityName != null){
            return cityName
        }else{
            return "hata bulunamadı"

        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var spinnerAdapter = ArrayAdapter.createFromResource(this,R.array.turkey_city, R.layout.spinner_tek_satir)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


        binding.spnCity.adapter = spinnerAdapter

        binding.spnCity.onItemSelectedListener = this

        location = SimpleLocation(this)

        if (!location!!.hasLocationEnabled()) {

            binding.spnCity.setSelection(1)

            Toast.makeText(this,"GPS AÇ YERİNİ BULALIM ",Toast.LENGTH_LONG).show()
            // ask the user to enable location access

            SimpleLocation.openSettings(this);
        }else{
            if (ContextCompat.checkSelfPermission(this,ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, arrayOf(ACCESS_FINE_LOCATION),11)
            }else{

                location = SimpleLocation(this)
                location?.setListener(object :SimpleLocation.Listener{
                    override fun onPositionChanged() {

                        latitude = String.format("%.2f",location?.latitude)
                        longitude = String.format("%.2f",location?.longitude)

                        Log.e("tolgaaaaaaaa ",""+latitude + " " + longitude)
                        currentCity(latitude,longitude)

                    }

                })

            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        if (requestCode==11){
            if (grantResults.size >0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                location = SimpleLocation(this)
                location?.setListener(object :SimpleLocation.Listener{
                    override fun onPositionChanged() {

                        latitude = String.format("%.2f",location?.latitude)
                        longitude = String.format("%.2f",location?.longitude)

                        Log.e("tolgaaaaaaaa ",""+latitude + " " + longitude)
                        currentCity(latitude,longitude)

                    }

                })

            }
        }else{
            Toast.makeText(this,"izir ver ",Toast.LENGTH_LONG).show()
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }





    fun data(sehir:String){

        val url = "https://api.openweathermap.org/data/2.5/weather?q="+sehir+"&appid=dbebd51d2e67e2d589db303fb923efad&lang=tr&units=metric"
        val weatherObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,object : Response.Listener<JSONObject> {


            override fun onResponse(response: JSONObject?) {

                var main = response?.getJSONObject("main")
                var tempereture = main?.getInt("temp")
                var feelsLike = main?.getInt("feels_like")
                var tempMin = main?.getInt("temp_min")
                var tempMax = main?.getInt("temp_max")
                var pressure = main?.getInt("pressure")
                var humidity = main?.getInt("humidity")
                binding.tvTemp.text = tempereture.toString()
                binding.tvhumidity.text = humidity.toString().plus(" %")
                binding.tvFeelsTemp.text = feelsLike.toString().plus("°C")
                binding.tvLowTemp.text = tempMin.toString().plus("°C")
                binding.tvMaxTemp.text = tempMax.toString().plus("°C")
                binding.tvPressure.text = pressure.toString()


                var wind = response?.getJSONObject("wind")
                var windSpeed = wind?.getInt("speed")
                binding.tvWindSpeed.text = windSpeed.toString().plus(" km/s")

                var cityName = response?.getString("name")

                var weather=response?.getJSONArray("weather")
                var descrition = weather?.getJSONObject(0)?.getString("description")

                binding.tvDescription.text = descrition
                var icon = weather?.getJSONObject(0)?.getString("icon")

                if (icon?.last() == 'd'){
                    binding.rootLayout.background = getDrawable(R.drawable.sunday2)
                    binding.tvHistory.setTextColor(resources.getColor(R.color.indigo))
                    binding.tvDescription.setTextColor(resources.getColor(R.color.indigo))
                    binding.tvCelcius.setTextColor(resources.getColor(R.color.indigo))
                    binding.tvTemp.setTextColor(resources.getColor(R.color.indigo))
                    binding.tvhumidity.setTextColor(resources.getColor(R.color.indigo))
                    binding.tvFeelsTemp.setTextColor(resources.getColor(R.color.indigo))
                    binding.tvWindSpeed.setTextColor(resources.getColor(R.color.indigo))
                    binding.tvLowTemp.setTextColor(resources.getColor(R.color.indigo))
                    binding.tvMaxTemp.setTextColor(resources.getColor(R.color.indigo))
                    binding.tvPressure.setTextColor(resources.getColor(R.color.indigo))
                    binding.textView11.setTextColor(resources.getColor(R.color.indigo))
                    binding.textView13.setTextColor(resources.getColor(R.color.indigo))
                    binding.textView15.setTextColor(resources.getColor(R.color.indigo))
                    binding.textView6.setTextColor(resources.getColor(R.color.indigo))
                    binding.textView8.setTextColor(resources.getColor(R.color.indigo))
                    binding.textView.setTextColor(resources.getColor(R.color.indigo))
                    tvSehir?.setTextColor(resources.getColor(R.color.indigo))
                    binding.spnCity.background.setColorFilter(resources.getColor(R.color.indigo), PorterDuff.Mode.SRC_ATOP)

                }else{
                    binding.rootLayout.background = getDrawable(R.drawable.night)
                    binding.tvHistory.setTextColor(resources.getColor(R.color.colorAccent))
                    binding.tvDescription.setTextColor(resources.getColor(R.color.colorAccent))
                    binding.tvCelcius.setTextColor(resources.getColor(R.color.colorAccent))
                    binding.tvTemp.setTextColor(resources.getColor(R.color.colorAccent))
                    binding.tvhumidity.setTextColor(resources.getColor(R.color.colorAccent))
                    binding.tvFeelsTemp.setTextColor(resources.getColor(R.color.colorAccent))
                    binding.tvWindSpeed.setTextColor(resources.getColor(R.color.colorAccent))
                    binding.tvLowTemp.setTextColor(resources.getColor(R.color.colorAccent))
                    binding.tvMaxTemp.setTextColor(resources.getColor(R.color.colorAccent))
                    binding.tvPressure.setTextColor(resources.getColor(R.color.colorAccent))
                    binding.textView11.setTextColor(resources.getColor(R.color.colorAccent))
                    binding.textView13.setTextColor(resources.getColor(R.color.colorAccent))
                    binding.textView15.setTextColor(resources.getColor(R.color.colorAccent))
                    binding.textView6.setTextColor(resources.getColor(R.color.colorAccent))
                    binding.textView8.setTextColor(resources.getColor(R.color.colorAccent))
                    binding.textView.setTextColor(resources.getColor(R.color.colorAccent))
                    tvSehir?.setTextColor(resources.getColor(R.color.colorAccent))
                    binding.spnCity.background.setColorFilter(resources.getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP)
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

        tvSehir = p1 as TextView

        if (p2 == 0){

            var currentCityName = currentCity(latitude,longitude)
            tvSehir?.setText(currentCityName)





        }else{
            var selectedCity=p0?.getItemAtPosition(p2).toString()

            data(selectedCity)

        }




    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }


}


