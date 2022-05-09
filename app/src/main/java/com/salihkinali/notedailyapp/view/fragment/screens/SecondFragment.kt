package com.salihkinali.notedailyapp.view.fragment.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.salihkinali.notedailyapp.R
import com.salihkinali.notedailyapp.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {
    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater,container,false)
        val viewpager = activity?.findViewById<ViewPager2>(R.id.pager)
        binding.anotherNext.setOnClickListener {
            viewpager!!.currentItem = 2
        }
        binding.previous.setOnClickListener {
            viewpager!!.currentItem = 0
        }
        return binding.root
    }


}