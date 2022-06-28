package com.salihkinali.notedailyapp.view.fragment.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.salihkinali.notedailyapp.R
import com.salihkinali.notedailyapp.databinding.FragmentFirstBinding


class FirstFragment : Fragment() {
     private var _binding:FragmentFirstBinding? =null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        _binding = FragmentFirstBinding.inflate(inflater,container,false)
        val viewpager = activity?.findViewById<ViewPager2>(R.id.pager)
        binding.nextButton.setOnClickListener {
            viewpager!!.currentItem = 1
        }
       return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }


}