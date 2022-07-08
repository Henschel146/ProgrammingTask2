package com.android.example.testapplication.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Domain objects are plain Kotlin data classes that represent the things in our app. These are the
 * objects that should be displayed on screen, or manipulated by the app.
 *
 * @see database for objects that are mapped to the database
 * @see network for objects that parse or prepare network calls
 */
@Parcelize
data class UserDomain( val id: Int,
                 val name: String,
                 val userName: String,
                 val email: String,
                 val street: String,
                 val suite: String,
                 val city: String,
                 val zipcode: String,
                 val geo: String,
                 val phone: String,
                 val website: String,
                 val companyName: String,
                 val catchPhrase: String,
                 val bs: String
):Parcelable

