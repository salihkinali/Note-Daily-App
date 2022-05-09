package com.salihkinali.notedailyapp.view.fragment.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.salihkinali.notedailyapp.R
import com.salihkinali.notedailyapp.databinding.FragmentFirstBinding


class FirstFragment : Fragment() {
     private var _binding:FragmentFirstBinding? =null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater,container,false)
        val viewpager = activity?.findViewById<ViewPager2>(R.id.pager)
        binding.nextButton.setOnClickListener {
            viewpager!!.currentItem = 1
        }
       return binding.root
    }


}