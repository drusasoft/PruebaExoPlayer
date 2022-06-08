package com.exmaple.pruebaexoplayer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.exmaple.pruebaexoplayer.R
import com.exmaple.pruebaexoplayer.databinding.LayoutFragmentMainMenuBinding





class Fragment_Main_Menu:Fragment()
{

    private lateinit var binding: LayoutFragmentMainMenuBinding
    private lateinit var navController: NavController


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {

        binding =  LayoutFragmentMainMenuBinding.inflate(inflater, container, false)

        //Se registran los eventos ClickListener
        binding.txtOpMainMenu.setOnClickListener { navController.navigate(R.id.irfragmentReproductor) }

        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        //Se instancia el objeto NavController
        navController = Navigation.findNavController(view)
    }

}