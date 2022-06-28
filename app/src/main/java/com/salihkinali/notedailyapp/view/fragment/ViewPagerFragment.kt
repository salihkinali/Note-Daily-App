package com.salihkinali.notedailyapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.salihkinali.notedailyapp.adapter.ViewPagerAdapter
import com.salihkinali.notedailyapp.databinding.FragmentViewPagerBinding
import com.salihkinali.notedailyapp.view.fragment.screens.FirstFragment
import com.salihkinali.notedailyapp.view.fragment.screens.SecondFragment
import com.salihkinali.notedailyapp.view.fragment.screens.ThirdFragment


class ViewPagerFragment : Fragment() {
    private var _binding: FragmentViewPagerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity).supportActionBar?.hide()
        _binding = FragmentViewPagerBinding.inflate(inflater, container, false)

        val adapter = ViewPagerAdapter(arrayListOf(
            FirstFragment(),
            SecondFragment(),
            ThirdFragment()
        ),requireActivity().supportFragmentManager,lifecycle)
        binding.pager.adapter = adapter

        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        (activity as AppCompatActivity).supportActionBar?.show()
        _binding = null
    }


}