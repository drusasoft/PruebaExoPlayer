package com.exmaple.pruebaexoplayer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.exmaple.pruebaexoplayer.databinding.LayoutFragmentReproductorBinding





class FragmentReproductor:Fragment()
{

    private lateinit var binding: LayoutFragmentReproductorBinding



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        binding = LayoutFragmentReproductorBinding.inflate(inflater, container, false)


        return binding.root
    }

}