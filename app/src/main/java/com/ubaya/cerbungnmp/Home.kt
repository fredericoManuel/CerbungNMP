package com.ubaya.cerbungnmp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.cerbungnmp.databinding.ActivityHomeBinding

class Home : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val lm:LinearLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = lm
        binding.recyclerView.setHasFixedSize(true)
        //binding.recyclerView.adapter = CerbungAdapter()

        binding.btnCreate.setOnClickListener(){
            val intent = Intent(this,hal1Create::class.java)
            startActivity(intent)
        }
    }
}