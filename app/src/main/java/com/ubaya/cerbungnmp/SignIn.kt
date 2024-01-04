package com.ubaya.cerbungnmp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ubaya.cerbungnmp.databinding.ActivitySignInBinding
import org.json.JSONException
import org.json.JSONObject

class SignIn : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivitySignInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnSignIn.setOnClickListener(){
            val username = binding.txtUsernameIn.text.toString()
            val password = binding.txtPasswordIn.text.toString()

            val url = "https://ubaya.me/native/160721056/login.php"

            val stringRequest = object : StringRequest(
                Request.Method.POST, url,
                Response.Listener { response ->
                    Log.d("apiresultlogin", response.toString())
                    try {
                        val jsonResponse = JSONObject(response)
                        val result = jsonResponse.getString("result")
                        val fullname = jsonResponse.getString("fullname")
                        val username = jsonResponse.getString("username")
                        val id = jsonResponse.getString("id")
                        val image = jsonResponse.getString("image")

                        if (result == "success") {
                            val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putString("app_id", id)
                            editor.putString("app_fullname", fullname)
                            editor.putString("app_username", username)
                            editor.putString("app_image", image)
                            editor.apply()
                            Toast.makeText(this,"Login succesfully, Welcome " + fullname, Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, MainActivityCerbung::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            // Proses ketika login gagal atau hasil lainnya
                            Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: JSONException) {
                        // Tangani kesalahan parsing JSON
                        Toast.makeText(this, "Error parsing JSON", Toast.LENGTH_SHORT).show()
                    }

                },
                Response.ErrorListener { error ->
                    Log.d("apiresultLogin", error.toString())
                    Toast.makeText(this, "Error in volley respon error", Toast.LENGTH_SHORT).show()
                }
            ) {
                override fun getParams(): MutableMap<String, String> {
                    val params = HashMap<String, String>()
                    params["username"] = username
                    params["password"] = password
                    return params
                }
            }

            Volley.newRequestQueue(this).add(stringRequest)
        }

        binding.txtSignUp.setOnClickListener() {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
    }
}