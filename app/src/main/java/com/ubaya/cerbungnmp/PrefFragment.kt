package com.ubaya.cerbungnmp

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import com.ubaya.cerbungnmp.databinding.FragmentPrefBinding
import org.json.JSONObject

class PrefFragment : Fragment() {
    private lateinit var binding: FragmentPrefBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPrefBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val q = Volley.newRequestQueue(activity)
        val url = "https://ubaya.me/native/160721056/get_user.php"

        // Mengambil ID pengguna dari SharedPreferences
        //Ambil userID yang login
        var sharedFile = "com.ubaya.cerbungnmp"
        var shared: SharedPreferences = requireContext().getSharedPreferences(sharedFile, Context.MODE_PRIVATE)
        val idUser = shared.getInt("ID",0)

        Log.d("PrefFragment", "ID Pengguna: $idUser")

        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            {
                Log.d("apiresult", it)
                val obj = JSONObject(it)
                val arrayData = obj.getJSONArray("data")
                if (arrayData.length() > 0) {
                    val userData = arrayData.getJSONObject(0)
                    val image = userData.getString("image")

                    Picasso.get().load(image).into(binding.profileImageView)

                    binding.txtUsername.text=userData.getString("username")
                }
            },
            {
                Log.e("apiresult", it.message.toString())
            }
        ) {
            // Override metode getParams() untuk menambahkan parameter ID pengguna
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["idUsers"] = idUser.toString()
                return params
            }
        }
        q.add(stringRequest)

        binding.btnLogout.setOnClickListener {
            val intent = Intent(activity, SignIn::class.java)
            startActivity(intent)
            // ini untuk tutup aktivitas di main
            activity?.finish()
        }



        binding.darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            Log.d("FragmentPref", "Before Dark Mode Change - isChecked: $isChecked")

            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.darkModeSwitch.isChecked = isChecked
            } else {
                binding.darkModeSwitch.isChecked = false
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

            Log.d("FragmentPref", "After Dark Mode Change - isChecked: $isChecked")
        }

        binding.notificationSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // In-app notifications are enabled
            } else {
                // In-app notifications are disabled
            }
        }
    }
}