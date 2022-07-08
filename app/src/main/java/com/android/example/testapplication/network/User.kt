package com.android.example.testapplication.network
import android.os.Parcelable
import androidx.lifecycle.Transformations.map
import com.android.example.testapplication.databases.DatabaseUser
import com.android.example.testapplication.domain.UserDomain
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class User (
	@Json(name = "id") val id : Int,
	@Json(name ="name") val name : String,
	@Json(name ="username") val username : String,
	@Json(name ="email") val email : String,
	@Json(name ="address") val address : Address,
	@Json(name ="phone") val phone : String,
	@Json(name ="website") val website : String,
	@Json(name= "company") val company : Company
) : Parcelable

fun User.asDatabaseModel() : DatabaseUser{

	val theUser = DatabaseUser(
		id = id,
		name = name,
		username = username,
		email = email,
		street = address.street,
		suite = address.suite,
		city = address.city,
		zipcode = address.zipcode,
		phone = phone,
		website = website,
		companyName = company.name,
		bs = company.bs,
		catchPhrase = company.catchPhrase,
		geo = address.geo.toString()
	)
	return theUser


}