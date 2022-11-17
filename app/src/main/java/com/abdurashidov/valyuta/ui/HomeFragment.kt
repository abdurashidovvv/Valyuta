package com.abdurashidov.valyuta.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import com.abdurashidov.valyuta.R
import com.abdurashidov.valyuta.adapters.StateAdapters
import com.abdurashidov.valyuta.adapters.UserAdapter
import com.abdurashidov.valyuta.databinding.FragmentHomeBinding
import com.abdurashidov.valyuta.databinding.FragmentValyutaListBinding
import com.abdurashidov.valyuta.databinding.TabItemViewBinding
import com.abdurashidov.valyuta.models.MyObject
import com.abdurashidov.valyuta.models.PagerItem
import com.abdurashidov.valyuta.models.Valyuta
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var list:ArrayList<PagerItem>
    private lateinit var stateAdapters: StateAdapters
    private lateinit var filteredList:ArrayList<Valyuta>
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        list=ArrayList()
        list.add(PagerItem("Royhat"))
        list.add(PagerItem("Konverter"))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding=FragmentHomeBinding.inflate(layoutInflater)

        stateAdapters=StateAdapters(list, this)
        binding.myViewpager.adapter=stateAdapters


        TabLayoutMediator(binding.myTablayout, binding.myViewpager){tab,position->
            val tabItemView= TabItemViewBinding.inflate(layoutInflater)

            tabItemView.tabItemTv.text=list[position].type

            tab.customView = tabItemView.root
        }.attach()




        binding.searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })

        return binding.root
    }

    private fun filterList(newText: String?) {
        filteredList=ArrayList()
        for (i in MyObject.list) {
            if (i.CcyNm_UZ!!.toLowerCase().contains(newText!!.toLowerCase())){
                filteredList.add(i)
            }
        }
        if (filteredList.isEmpty()){
            MyObject.userAdapter!!.list=filteredList
            MyObject.userAdapter!!.notifyDataSetChanged()
            Toast.makeText(binding.root.context, "No data found", Toast.LENGTH_SHORT).show()
        }else{
            MyObject.userAdapter!!.list=filteredList
            MyObject.userAdapter!!.notifyDataSetChanged()
        }
    }

}