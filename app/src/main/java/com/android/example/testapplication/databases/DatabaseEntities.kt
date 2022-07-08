package com.android.example.testapplication.databases

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.example.testapplication.domain.UserDomain

@Entity
data class DatabaseUser constructor(
    @PrimaryKey
    val id: Int,
    val name: String,
    val username: String,
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

)

/**
 * Map DatabaseUser to DomainUser
 */
fun List<DatabaseUser>.asDomainModel() : List<UserDomain> {
    return map{
        UserDomain(
            id = it.id,
            name = it.name,
            userName = it.username,
            email = it.email,
            street = it.street,
            suite = it.suite,
            city = it.city,
            zipcode = it.zipcode,
            geo = it.geo,
            phone = it.phone,
            website = it.website,
            companyName = it.companyName,
            catchPhrase = it.catchPhrase,
            bs = it.bs
        )
    }
}