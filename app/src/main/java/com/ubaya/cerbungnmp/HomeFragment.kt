package com.ubaya.cerbungnmp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ubaya.cerbungnmp.databinding.FragmentHomeBinding
import org.json.JSONObject

class HomeFragment : Fragment() {
    var cerbungs: ArrayList<Cerbung> = ArrayList()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fetchData()
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false )
//        if (cerbung.isEmpty()) { // Fetch data only if needed
//            fetchData()
//        } else {
//            updateList()
//        }
//        return binding.root
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }




//    override fun onReadButtonClicked(
//        contentId: Int,
//        contentAccess: String,
//        contentPhoto: String,
//        contentTitle: String,
//        contentDateReleased: String
//    ) {
//        val readFragment = ReadFragment()
//        val bundle = Bundle()
//        bundle.putInt("idCerita", contentId) // Pass necessary data
//        bundle.putString("access", contentAccess)
//        bundle.putString("urlFoto", contentPhoto)
//        bundle.putString("titleCerita", contentTitle)
//        bundle.putString("tglRilis", contentDateReleased)
//        readFragment.arguments = bundle
//
//        val fragmentManager = activity?.supportFragmentManager
//        val fragmentTransaction = fragmentManager?.beginTransaction()
//        fragmentTransaction?.replace(R.id.fragmentContainer, readFragment)
//        fragmentTransaction?.addToBackStack(null) // Add to back stack for navigation history
//        fragmentTransaction?.commit()
//    }

    private fun fetchData(){
        val q = Volley.newRequestQueue(activity)
        val url = "https://ubaya.me/native/160721056/get_cerbung.php"
        var stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String>{
                Log.d("apiresult", it)
                val obj = JSONObject(it)
                if(obj.getString("result") == "OK") {
                    val data = obj.getJSONArray("data")

                    for (i in 0 until data.length()){
                        val playObj = data.getJSONObject(i)
                        val cerbung = Cerbung(
                            playObj.getInt("id"),
                            playObj.getString("title"),
                            playObj.getInt("author_id"),
                            playObj.getInt("genre_id"),
                            playObj.getString("summary"),
                            playObj.getString("img_url"),
                            playObj.getString("created_at"),
                            playObj.getString("updated_at"),
                            playObj.getString("akses")
                        )
                        cerbungs.add(cerbung)
                    }
                    updateList()

                    Log.d("cekisiarray", cerbungs.toString() )
                }
            },
            {
                Log.e("apiresult", it.message.toString())
            })
        q.add(stringRequest)
    }

    fun updateList(){
        val lm = LinearLayoutManager(activity)
        with(binding.cerbungRecView){
            layoutManager = lm
            setHasFixedSize(true)
            adapter = CerbungAdapter(cerbungs)
        }
    }
}

