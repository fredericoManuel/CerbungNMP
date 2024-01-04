package com.ubaya.cerbungnmp

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import com.ubaya.cerbungnmp.databinding.CerbungItemBinding
import org.json.JSONObject

class CerbungAdapter (val cerbungs: ArrayList<Cerbung>): RecyclerView.Adapter<CerbungAdapter.CerbungViewHolder>(){
    class CerbungViewHolder(val binding: CerbungItemBinding): RecyclerView.ViewHolder(binding.root)
//
//    interface ItemClickListener {
//        fun onReadButtonClicked(contentId: Int, contentAccess: String, contentPhoto: String, contentTitle: String, contentDateReleased: String)
//    }
//
//    private var adapterCallback: ItemClickListener? = null

//    fun setItemClickListener(listener: ItemClickListener){
//        adapterCallback = listener
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CerbungViewHolder {
        val binding = CerbungItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CerbungViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return cerbungs.size
    }

    override fun onBindViewHolder(holder: CerbungViewHolder, position: Int) {
        val url = cerbungs[position].img_url
        val builder = Picasso.Builder(holder.itemView.context)
        builder.listener {picasso, uri, exception -> exception.printStackTrace()}
        Picasso.get().load(url).into(holder.binding.imgPoster)
        with(holder.binding){
            val title = cerbungs[position].title
            val idCerita = cerbungs[position].id
            val access = cerbungs[position].akses
            val tglRilis = cerbungs[position].created_at
            txtJudul.text = title
            txtContent.text = cerbungs[position].summary
            getData(cerbungs[position].id, holder.itemView.context){
                authorName, paragraphCount, totalLikes ->
                txtAuthor.text = "by " + authorName
                txtJmlParagraf.text = paragraphCount.toString()
                txtJmlLike.text = totalLikes.toString()
            }
//            btnRead.setOnClickListener{
//                adapterCallback?.onReadButtonClicked(idCerita, access, url, title , tglRilis)
//            }
        }
    }



    private fun getData(idCerita: Int, context: Context, callback: (String, Int, Int) -> Unit) {
        var authorName: String = ""; var paragraphCount: Int = 0; var totalLikes: Int = 0
        val q = Volley.newRequestQueue(context)
        val url = "https://ubaya.me/native/160721056/get_data.php"
        var stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> {
                Log.d("cekparams", it)
                val obj = JSONObject(it)
                if(obj.getString("result") == "OK") {
                    val data = obj.getJSONArray("data")
                    for(i in 0 until data.length()) {
                        val dataObj = data.getJSONObject(i)
                        authorName = dataObj.getString("fullname")
                        paragraphCount = dataObj.getInt("paragraph_count")
                        totalLikes = dataObj.getInt("total_likes")
                    }
                    callback(authorName, paragraphCount, totalLikes)
                    Log.d("cekisi", authorName + paragraphCount.toString() + totalLikes.toString())
                }
            },
            Response.ErrorListener {
                Log.e("cekparams", it.message.toString())
            }) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["idCerita"] = idCerita.toString()
                return params
            }
        }
        q.add(stringRequest)
    }


}