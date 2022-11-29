package com.abdurashidov.valyuta.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.abdurashidov.valyuta.R
import com.abdurashidov.valyuta.adapters.UserAdapter
import com.abdurashidov.valyuta.databinding.FragmentValyutaConverterBinding
import com.abdurashidov.valyuta.databinding.FragmentValyutaListBinding
import com.abdurashidov.valyuta.models.Valyuta
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray

class ValyutaConverterFragment : Fragment() {

    private lateinit var binding: FragmentValyutaConverterBinding
    private lateinit var list:List<Valyuta>
    private lateinit var listName:ArrayList<String>
    private lateinit var requestQueue: RequestQueue
    private lateinit var userAdapter: UserAdapter
    private var dot=true
    val url="http://cbu.uz/uzc/arkhiv-kursov-valyut/json/"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentValyutaConverterBinding.inflate(layoutInflater)
        requestQueue= Volley.newRequestQueue(binding.root.context)
        list= ArrayList()
        listName= ArrayList()
        val myAsyncTask=MyAsyncTask()
        myAsyncTask.execute()

        binding.btn1.setOnClickListener {
            if (binding.inputTv.text.first()=='0' && binding.inputTv.text.length==1){
                binding.inputTv.text="1"
            }else{
                binding.inputTv.append("1")
            }
        }
        binding.btn2.setOnClickListener {
            if (binding.inputTv.text.first()=='0' && binding.inputTv.text.length==1){
                binding.inputTv.text="2"
            }else{
                binding.inputTv.append("2")
            }
        }
        binding.btn3.setOnClickListener {
            if (binding.inputTv.text.first()=='0' && binding.inputTv.text.length==1){
                binding.inputTv.text="3"
            }else{
                binding.inputTv.append("3")
            }
        }
        binding.btn4.setOnClickListener {
            if (binding.inputTv.text.first()=='0' && binding.inputTv.text.length==1){
                binding.inputTv.text="4"
            }else{
                binding.inputTv.append("4")
            }
        }
        binding.btn5.setOnClickListener {
            if (binding.inputTv.text.first()=='0' && binding.inputTv.text.length==1){
                binding.inputTv.text="5"
            }else{
                binding.inputTv.append("5")
            }
        }
        binding.btn6.setOnClickListener {
            if (binding.inputTv.text.first()=='0' && binding.inputTv.text.length==1){
                binding.inputTv.text="6"
            }else{
                binding.inputTv.append("6")
            }
        }
        binding.btn7.setOnClickListener {
            if (binding.inputTv.text.first()=='0' && binding.inputTv.text.length==1){
                binding.inputTv.text="7"
            }else{
                binding.inputTv.append("7")
            }
        }
        binding.btn8.setOnClickListener {
            if (binding.inputTv.text.first()=='0' && binding.inputTv.text.length==1){
                binding.inputTv.text="8"
            }else{
                binding.inputTv.append("8")
            }
        }
        binding.btn9.setOnClickListener {
            if (binding.inputTv.text.first()=='0' && binding.inputTv.text.length==1){
                binding.inputTv.text="9"
            }else{
                binding.inputTv.append("9")
            }
        }
        binding.btn0.setOnClickListener {
            if (binding.inputTv.text.first()=='0' && !dot){
                binding.inputTv.append("0")
            }else if(binding.inputTv.text.first()!='0'){
                binding.inputTv.append("0")
            }
        }

        binding.btnDot.setOnClickListener {
            if (dot){
                binding.inputTv.append(".")
                dot=false
            }
        }

        binding.clear.setOnClickListener {
            binding.inputTv.text="0"
            binding.outputTv.text="0"
            dot=true
        }

        binding.btnConvert.setOnClickListener {
            val valyuta1=list[binding.fromSpinner.selectedItemPosition]
            val valyuta2=list[binding.toSpinner.selectedItemPosition]
            val valyuta=convert(valyuta2, valyuta1)
            val summa=binding.inputTv.text.toString().toFloat()
            val result=valyuta*summa
            binding.outputTv.text="${result}"
        }

        binding.change.setOnClickListener {
            val positon1=binding.fromSpinner.selectedItemPosition
            val positon2=binding.toSpinner.selectedItemPosition
            binding.fromSpinner.setSelection(positon2)
            binding.toSpinner.setSelection(positon1)
            binding.inputTv.text="0"
            binding.outputTv.text="0"
        }


//        try {
//            binding.convertBtn.setOnClickListener {
//                val valyuta1=list[binding.fromSpinner.selectedItemPosition]
//                val valyuta2=list[binding.toSpinner.selectedItemPosition]
//                val valyuta=convert(valyuta2, valyuta1)
//                val summa=binding.edt.text.toString().toFloat()
//                binding.result.visibility=View.VISIBLE
//                binding.result.text="${valyuta*summa}"
//            }
//        }catch (e:java.lang.Exception){}





        return binding.root
    }
    inner class MyAsyncTask: AsyncTask<Void, Void, Void>(){
        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg params: Void?): Void? {

            val jsonArrayRequest = JsonArrayRequest(
                Request.Method.GET, url, null,
                object : Response.Listener<JSONArray> {
                    override fun onResponse(response: JSONArray?) {

                        val type = object : TypeToken<List<Valyuta>>(){}.type
                        list = Gson().fromJson<List<Valyuta>>(response.toString(), type)
                        list.forEach {
                            listName.add(it.CcyNm_UZ)
                        }
                        binding.fromSpinner.adapter=ArrayAdapter<String>(binding.root.context, android.R.layout.simple_list_item_1, listName)
                        binding.toSpinner.adapter=ArrayAdapter<String>(binding.root.context, android.R.layout.simple_list_item_1, listName)
                    }
                }, object : Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError?) {
                        Toast.makeText(binding.root.context, "$error", Toast.LENGTH_SHORT).show()
                    }
                })

            requestQueue.add(jsonArrayRequest)
            return null
        }

    }

    fun convert(valyuta1: Valyuta, valyuta2: Valyuta):Float{
        return valyuta2.Rate.toFloat()/valyuta1.Rate.toFloat()
    }


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