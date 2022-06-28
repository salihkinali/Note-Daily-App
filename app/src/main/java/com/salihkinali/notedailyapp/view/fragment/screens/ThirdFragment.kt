package com.salihkinali.notedailyapp.view.fragment.screens

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.salihkinali.notedailyapp.R
import com.salihkinali.notedailyapp.databinding.FragmentThirdBinding
import com.salihkinali.notedailyapp.view.fragment.ViewPagerFragmentDirections

class ThirdFragment : Fragment() {
    private var _binding: FragmentThirdBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity).supportActionBar?.hide()
        _binding = FragmentThirdBinding.inflate(inflater,container,false)
        val viewpager = activity?.findViewById<ViewPager2>(R.id.pager)
        binding.startButton.setOnClickListener {
            val action = ViewPagerFragmentDirections.viewpagerToSplashAction()
            findNavController().navigate(action)
            onBoardingFinished()
        }
        binding.backButton.setOnClickListener {
            viewpager!!.currentItem = 1
        }
        return binding.root
    }

    private fun onBoardingFinished() {
        val sharedPref = requireActivity().getSharedPreferences("BoardingScreen",Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("isFirstTime",false)
        editor.commit()
    }


}