package com.ubaya.cerbungnmp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import com.ubaya.cerbungnmp.databinding.ActivitySignInBinding
import com.ubaya.cerbungnmp.databinding.ActivitySignUpBinding
import org.json.JSONException
import org.json.JSONObject
import java.util.Calendar

class SignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnShowProfile.setOnClickListener {
            val url = binding.txtPicture.text.toString()

            val builder = Picasso.Builder(this)
            builder.listener { picasso, uri, exception -> exception.printStackTrace() }
            builder.build().load(url).into(binding.imgProfile)
        }

        binding.txtSignIn.setOnClickListener() {
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
        }
        binding.btnSignUp.setOnClickListener() {

            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH) + 1
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val currentDate = "$year-$month-$day"

            if (binding.txtPasswordUp1.text.toString() == "" && binding.txtPasswordUp2.text.toString() == "" && binding.txtUsernameUp.text.toString() == "" && binding.txtPicture.text.toString() == "" && binding.txtFullname.text.toString() == ""){
                Toast.makeText(this, "Every field must be filled", Toast.LENGTH_SHORT).show()
            }
            else {
                if (binding.txtPasswordUp1.text.toString() == binding.txtPasswordUp2.text.toString()) {

                    val url = "https://ubaya.me/native/160721056/signup.php"

                    val stringRequest = object : StringRequest(
                        Request.Method.POST, url,
                        Response.Listener { response ->
                            Log.e("apiresult", response.toString())
                            try {
                                val jsonResponse = JSONObject(response)
                                val result = jsonResponse.getString("result")

                                if (result == "success") {
                                    Toast.makeText(this, "Your account has been successfully created", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this, SignIn::class.java)
                                    startActivity(intent)
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
                            Toast.makeText(this, "Error in volley respon error", Toast.LENGTH_SHORT).show()
                            Log.e("apiresult", error.toString())
                        }
                    ) {
                        override fun getParams(): MutableMap<String, String> {
                            val params = HashMap<String, String>()
                            params["username"] = binding.txtUsernameUp.text.toString()
                            params["password"] = binding.txtPasswordUp1.text.toString()
                            params["fullname"] = binding.txtFullname.text.toString()
                            params["gambar"] = binding.txtPicture.text.toString()
                            params["registerDate"] = currentDate.toString()
                            return params
                        }
                    }
                    // Tambahkan permintaan ke antrian Volley
                    Volley.newRequestQueue(this).add(stringRequest)

                }
                else {
                    Toast.makeText(this, "Make sure password match", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
}