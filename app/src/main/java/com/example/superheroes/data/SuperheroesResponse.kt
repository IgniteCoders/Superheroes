package com.example.superheroes.data

import com.google.gson.TypeAdapter
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter


class SuperheroesResponse (
    @SerializedName("response") val response:String,
    @SerializedName("results-for") val resultsFor:String,
    @SerializedName("results") val results:List<Superhero>
) {
}

class Superhero (
    @SerializedName("id") val id:String,
    @SerializedName("name") val name:String,
    @SerializedName("powerstats") val stats:Stats,
    @SerializedName("biography") val biography:Biography,
    @SerializedName("work") val work:Work,
    @SerializedName("image") val image:Image
) { }

class Stats (
    @JsonAdapter(IntegerAdapter::class) @SerializedName("intelligence") val intelligence: Int,
    @JsonAdapter(IntegerAdapter::class) @SerializedName("strength") val strength: Int,
    @JsonAdapter(IntegerAdapter::class) @SerializedName("speed") val speed: Int,
    @JsonAdapter(IntegerAdapter::class) @SerializedName("durability") val durability: Int,
    @JsonAdapter(IntegerAdapter::class) @SerializedName("power") val power: Int,
    @JsonAdapter(IntegerAdapter::class) @SerializedName("combat") val combat: Int,
) { }

class Biography (
    @SerializedName("full-name") val realName:String,
    @SerializedName("place-of-birth") val placeOfBirth:String,
    @SerializedName("alignment") val alignment:String,
    @SerializedName("publisher") val publisher:String
) { }

class Work (
    @SerializedName("occupation") val occupation:String,
    @SerializedName("base") val base:String
) { }

class Image (
    @SerializedName("url") val url:String
) { }


class IntegerAdapter : TypeAdapter<Int>() {
    override fun write(out: JsonWriter?, value: Int) {
        out?.value(value)
    }

    override fun read(`in`: JsonReader?): Int {
        if (`in` != null) {
            val value: String = `in`.nextString()
            if (value != "null") {
                return value.toInt()
            }
        }
        return 0
    }

}