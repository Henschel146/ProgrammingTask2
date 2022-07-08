package com.android.example.testapplication.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.android.example.testapplication.databases.DatabaseUser
import com.android.example.testapplication.databases.UsersDatabase
import com.android.example.testapplication.databases.asDomainModel
import com.android.example.testapplication.domain.UserDomain
import com.android.example.testapplication.network.User
import com.android.example.testapplication.network.UsersApi
import com.android.example.testapplication.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UsersRepository(private val database: UsersDatabase) {

    val users: LiveData<List<UserDomain>> = Transformations.map(
        database.userDao.getUsers()
    ){it.asDomainModel()}

    /**
     * Refresh the users stored in the offline cache.
     *
     * This function uses the IO dispatcher to ensure the database insert database operation
     * happens on the IO dispatcher. By switching to the IO dispatcher using `withContext` this
     * function is now safe to call from any thread including the Main thread.
     *
     */
    suspend fun refreshUsers() {
        withContext(Dispatchers.IO) {


            val users = UsersApi.retrofitService.getUsers()
            val databaseUsers = ArrayList<DatabaseUser>()

            for( user : User in users){
                databaseUsers.add(user.asDatabaseModel())
            }

           val list: List<DatabaseUser> = databaseUsers

            database.userDao.insertAll(list)


        }
    }

   suspend fun insertUser(userDomain: UserDomain){

       withContext(Dispatchers.IO){
           val dbUser : DatabaseUser = DatabaseUser(
               id=userDomain.id,
               name=userDomain.name,
               username = userDomain.userName,
               email = userDomain.email,
               street = userDomain.street,
               suite = userDomain.suite,
               city = userDomain.city,
               zipcode = userDomain.zipcode,
               geo = userDomain.geo,
               phone = userDomain.phone,
               website = userDomain.website,
               companyName = userDomain.companyName,
               catchPhrase = userDomain.catchPhrase,
               bs = userDomain.bs

           )
           database.userDao.insert(dbUser)
       }

   }


}