package com.ubaya.cerbungnmp

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.cerbungnmp.databinding.ActivityHal1CreateBinding
import com.ubaya.cerbungnmp.databinding.ActivityHomeBinding
import com.ubaya.cerbungnmp.databinding.ActivitySignInBinding


class hal1Create : AppCompatActivity() {
    private lateinit var binding: ActivityHal1CreateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHal1CreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val adapter = ArrayAdapter(this, R.layout.simple_list_item_1,Global.genre)
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //binding.spinnerGenre.adapter=adapter

        binding.btnNext1.setOnClickListener() {
            val intent = Intent(this, hal2Create::class.java)
            startActivity(intent)
        }

        binding.btnBack.setOnClickListener() {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }

    }
}