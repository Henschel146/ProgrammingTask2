package com.android.example.testapplication.network
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize



@JsonClass(generateAdapter = true)
@Parcelize
data class Address (

	@Json(name ="street") val street : String,
	@Json(name = "suite") val suite : String,
	@Json(name ="city") val city : String,
	@Json(name = "zipcode") val zipcode : String,
	@Json(name ="geo") val geo : Geo
):Parcelable