package com.ubaya.cerbungnmp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ubaya.cerbungnmp.databinding.ActivityHal2CreateBinding
import com.ubaya.cerbungnmp.databinding.ActivityHal3CreateBinding

class hal3Create : AppCompatActivity() {
    private lateinit var binding: ActivityHal3CreateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHal3CreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPublish.setOnClickListener() {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }
        binding.btnPrev3.setOnClickListener() {
            val intent = Intent(this, hal2Create::class.java)
            startActivity(intent)
        }
    }
}