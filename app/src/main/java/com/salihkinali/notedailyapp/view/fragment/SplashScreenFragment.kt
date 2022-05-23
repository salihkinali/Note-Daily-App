package com.salihkinali.notedailyapp.view.fragment

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.salihkinali.notedailyapp.databinding.FragmentSplashScreenBinding


class SplashScreenFragment : Fragment() {
    private var _binding: FragmentSplashScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity).supportActionBar?.hide()
        _binding = FragmentSplashScreenBinding.inflate(inflater, container, false)

        val timer = object :CountDownTimer(3000,1000){
            override fun onTick(p0: Long) {
            }
            override fun onFinish() {
                if(onBoarding()){
                    val action = SplashScreenFragmentDirections.splashToViewPagerAction()
                    findNavController().navigate(action)
                }else{
                    val action = SplashScreenFragmentDirections.splashToHomeAction()
                    findNavController().navigate(action)
                    showIt()
                }
            }
        }
        timer.start()
        return binding.root
    }
    private fun onBoarding():Boolean{
        val sharedPref = requireActivity().getSharedPreferences("BoardingScreen", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("isFirstTime",true)
    }
    private fun showIt(){
        (activity as AppCompatActivity).supportActionBar?.show()
    }
}

