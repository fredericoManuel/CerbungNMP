package com.ubaya.cerbungnmp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import com.ubaya.cerbungnmp.databinding.ActivityHal1CreateBinding
import com.ubaya.cerbungnmp.databinding.ActivityHal2CreateBinding

class hal2Create : AppCompatActivity() {
    private lateinit var binding: ActivityHal2CreateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHal2CreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.GroupCerita.checkedRadioButtonId
        binding.GroupCerita.setOnCheckedChangeListener { radioGroup, id ->
            binding.rbtnRestricted.setTextColor(Color.BLACK)
            binding.rbtnPublic.setTextColor(Color.BLACK)

            val radio=findViewById<RadioButton>(binding.GroupCerita.checkedRadioButtonId)
            val tag = radio.tag.toString()
            Log.d("radioCerita",tag)

            val selectedRadio= findViewById<RadioButton>(id)
            selectedRadio.setTextColor(Color.RED)
        }

        binding.btnNext2.setOnClickListener() {
            val intent = Intent(this, hal3Create::class.java)
            startActivity(intent)
        }
        binding.btnPrev.setOnClickListener() {
            val intent = Intent(this, hal1Create::class.java)
            startActivity(intent)
        }
    }
}