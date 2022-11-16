package com.abdurashidov.valyuta

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.abdurashidov.valyuta.adapters.StateAdapters
import com.abdurashidov.valyuta.adapters.UserAdapter
import com.abdurashidov.valyuta.databinding.ActivityMainBinding
import com.abdurashidov.valyuta.models.PagerItem
import com.abdurashidov.valyuta.models.Valyuta
import com.android.volley.Request.Method.GET
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var list:ArrayList<PagerItem>
    private lateinit var requestQueue: RequestQueue
    private lateinit var userAdapter: UserAdapter
    private lateinit var stateAdapters: StateAdapters
    val url="http://cbu.uz/uzc/arkhiv-kursov-valyut/json/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//
//        list=ArrayList()
//        list.add(PagerItem("list"))
//        list.add(PagerItem("converter"))
//        stateAdapters= StateAdapters(list, Fragment())
//
//        if (isOnline(this)){
//        }else{
//            Toast.makeText(this, "Internet bilan aloqani tekshiring", Toast.LENGTH_SHORT).show()
//        }
    }

//    inner class MyAsyncTask:AsyncTask<Void, Void, Void>(){
//        override fun doInBackground(vararg params: Void?): Void? {
//
//            val jsonArrayRequest = JsonArrayRequest(GET, url, null,
//                object : Response.Listener<JSONArray> {
//                    override fun onResponse(response: JSONArray?) {
//
//                        val type = object : TypeToken<List<Valyuta>>(){}.type
//                        list = Gson().fromJson<List<Valyuta>>(response.toString(), type)
//                        userAdapter = UserAdapter(list)
//                        binding.rv.adapter = userAdapter
//                    }
//                }, object : Response.ErrorListener {
//                    override fun onErrorResponse(error: VolleyError?) {
//                        Toast.makeText(this@MainActivity, "$error", Toast.LENGTH_SHORT).show()
//                    }
//                })
//
//            requestQueue.add(jsonArrayRequest)
//            return null
//        }
//
//    }
//
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    return true
                }
            }
        }
        return false
    }

}