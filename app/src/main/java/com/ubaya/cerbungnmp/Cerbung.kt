package com.ubaya.cerbungnmp

data class Cerbung(
    var id:Int,
    var title:String,
    var author_id:Int,
    var genre_id:Int,
    var summary:String,
    var img_url:String,
    var created_at:String,
    var updated_at:String,
    var akses:String,
    //var detail:ArrayList<Paragraph>,
)