package com.abdurashidov.valyuta.ui

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.abdurashidov.valyuta.R
import com.abdurashidov.valyuta.adapters.UserAdapter
import com.abdurashidov.valyuta.databinding.ActivityMainBinding
import com.abdurashidov.valyuta.databinding.BottomsheetDialogBinding
import com.abdurashidov.valyuta.databinding.FragmentValyutaListBinding
import com.abdurashidov.valyuta.models.MyObject
import com.abdurashidov.valyuta.models.Valyuta
import com.android.volley.Request.Method.GET
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray

class ValyutaListFragment : Fragment(), UserAdapter.RvClick {

    private lateinit var binding: FragmentValyutaListBinding
    private lateinit var list: List<Valyuta>
    private lateinit var requestQueue: RequestQueue
    val url = "http://cbu.uz/uzc/arkhiv-kursov-valyut/json/"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentValyutaListBinding.inflate(layoutInflater)
        requestQueue = Volley.newRequestQueue(binding.root.context)
        list = ArrayList()
        if (isOnline(binding.root.context)) {
            val myAsyncTask = MyAsyncTask()
            myAsyncTask.execute()
        } else {
            Toast.makeText(
                binding.root.context,
                "Tarmoq bilan aloqani tekshiring",
                Toast.LENGTH_SHORT
            ).show()
        }
        return binding.root
    }

    inner class MyAsyncTask : AsyncTask<Void, Void, Void>() {

        @Deprecated("Deprecated in Java")
        override fun onPreExecute() {
            super.onPreExecute()
            binding.myProgress.visibility=View.VISIBLE
        }

        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg params: Void?): Void? {
            val jsonArrayRequest = JsonArrayRequest(GET, url, null,
                object : Response.Listener<JSONArray> {
                    override fun onResponse(response: JSONArray?) {

                        val type = object : TypeToken<List<Valyuta>>() {}.type
                        list = Gson().fromJson<List<Valyuta>>(response.toString(), type)
                        MyObject.userAdapter=UserAdapter(list, this@ValyutaListFragment)
                        binding.rv.adapter = MyObject.userAdapter
                        MyObject.list.addAll(list)
                    }
                }, object : Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError?) {
                        Toast.makeText(binding.root.context, "$error", Toast.LENGTH_SHORT).show()
                    }
                })

            requestQueue.add(jsonArrayRequest)
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            binding.myProgress.visibility=View.GONE
        }

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

    @SuppressLint("SetTextI18n")
    override fun onClick(valyuta: Valyuta) {
        val dialog = BottomSheetDialog(binding.root.context)
        val bottomsheetDialogBinding = BottomsheetDialogBinding.inflate(layoutInflater)
        bottomsheetDialogBinding.name.text = "Valyuta: ${valyuta.CcyNm_UZ}"
        bottomsheetDialogBinding.rate.text = "Qiymati: ${valyuta.Rate}"
        bottomsheetDialogBinding.diff.text = "Farqi: ${valyuta.Diff}"
        bottomsheetDialogBinding.date.text = "Sana: ${valyuta.Date}"
        if (valyuta.Diff.toFloat() > 0) {
            bottomsheetDialogBinding.diffImg.setImageResource(R.drawable.increase)
        } else if (valyuta.Diff.toFloat() < 0) {
            bottomsheetDialogBinding.diffImg.setImageResource(R.drawable.decrease)
        } else {
            bottomsheetDialogBinding.diffImg.setImageResource(R.drawable.minus)
        }

        dialog.setContentView(bottomsheetDialogBinding.root)
        dialog.show()
    }


}