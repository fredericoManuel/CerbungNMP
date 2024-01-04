package com.ubaya.cerbungnmp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ubaya.cerbungnmp.databinding.FragmentReadBinding

class ReadFragment : Fragment() {
    private lateinit var binding: FragmentReadBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReadBinding.inflate(inflater, container, false)

        var sharedFile = "com.ubaya.cerbungnmp"
        var shared: SharedPreferences = requireContext().getSharedPreferences(sharedFile, Context.MODE_PRIVATE)
        val idUser = shared.getInt("ID", 0)

        val idCerita = arguments?.getInt("idCerita") ?: 0
        val access = arguments?.getString("access") ?: ""
        return TODO("Provide the return value")
    }

}