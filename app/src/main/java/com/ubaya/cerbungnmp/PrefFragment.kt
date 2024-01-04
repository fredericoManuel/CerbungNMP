package com.ubaya.cerbungnmp

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ubaya.cerbungnmp.Users
//import com.ubaya.cerbungnmp.databinding.FragmentCreateCerbung1Binding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import com.ubaya.cerbungnmp.databinding.FragmentPrefBinding
import org.json.JSONObject

class PrefFragment : Fragment() {
    private lateinit var binding:FragmentPrefBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var sharedFile = "com.ubaya.cerbungnmp"
        var shared: SharedPreferences = requireContext().getSharedPreferences(sharedFile,
            Context.MODE_PRIVATE )
        var username = shared.getString("username", null)
        val url = shared.getString("profilepic", null)

        val builder = Picasso.Builder(requireContext())
        builder.listener { picasso, uri, exception ->
            exception.printStackTrace()
        }
        Picasso.get().load(url).into(binding.imageUser)


        binding.txtNama.setText(username)

        binding.fabLogOut.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Logout Confirmation")
            builder.setMessage("You sure?")
            builder.setPositiveButton("Yes") { _, _ ->
                performLogout()
            }
            builder.setNegativeButton("No",null)

            builder.create().show()
        }

        binding.btnChangePass.setOnClickListener {
            val dialog = AlertDialog.Builder(it.context)
            dialog.setMessage("Are you sure, you want change password?")
            dialog.setPositiveButton("YES",
                DialogInterface.OnClickListener { dialogInterface, i ->
                    if(binding.txtPassword.text.toString() == binding.txtRepeatPassword.text.toString()){
                        val q = Volley.newRequestQueue(requireContext())
                        val url = "https://ubaya.me/native/160721056/changepassword.php"
                        val stringRequest = object : StringRequest(Method.POST,
                            url,
                            { Log.d("apisuccess",it)
                                val jsonObj = JSONObject(it)
                                val result = jsonObj.optString("result")
                                if(result=="success"){
                                    Toast.makeText(requireContext(), "Password Changed", Toast.LENGTH_SHORT).show()
                                    binding.txtPassword.setText("")
                                    binding.txtOldPassword.setText("")
                                    binding.txtRepeatPassword.setText("")
                                }
                                else{
                                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                                }
                            },
                            { Log.e("apierror",it.printStackTrace().toString()) }
                        ){
                            override fun getParams(): MutableMap<String, String>? {
                                val params = HashMap<String,String>()
                                params["username"] = username.toString()
                                params["current"] = binding.txtOldPassword.text.toString()
                                params["new"]= binding.txtPassword.text.toString()
                                return params
                            }
                        }
                        q.add(stringRequest)
                    }
                    else{
                        Toast.makeText(requireContext(), "Password you inputted do not match", Toast.LENGTH_SHORT).show()
                    }
                })
            dialog.setNegativeButton("CANCEL",null)
            dialog.create().show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = PrefFragmentBinding.inflate(inflater,container,false )
        return binding.root


    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            PrefsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    private fun performLogout() {
        //Hapus data dari SharedPreferences
        val sharedFile = "com.euonia.projectnmp"
        val shared: SharedPreferences = requireContext().getSharedPreferences(sharedFile, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = shared.edit()
        editor.remove("username")
        editor.remove("profilepic")
        editor.apply()

        //ke layar login
        val intent = Intent(activity, SignIn::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}