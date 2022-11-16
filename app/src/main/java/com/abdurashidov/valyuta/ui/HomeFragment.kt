package com.abdurashidov.valyuta.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import com.abdurashidov.valyuta.R
import com.abdurashidov.valyuta.adapters.StateAdapters
import com.abdurashidov.valyuta.databinding.FragmentHomeBinding
import com.abdurashidov.valyuta.databinding.TabItemViewBinding
import com.abdurashidov.valyuta.models.PagerItem
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var list:ArrayList<PagerItem>
    private lateinit var stateAdapters: StateAdapters

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

        return binding.root
    }

}