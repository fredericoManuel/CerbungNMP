package com.example.cerbung

data class Cerbung(
    var id:Int,
    var title:String,
    var author_id:Int,
    var author:String,
    var genre:String,
    var summary:String,
    var img_url:String,
    var created_at:String,
    var updated_at:String,
    var restricted:Int,
    var paragraphs:Int,
    var likes:Int,
    //var detail:ArrayList<Paragraph>,
)