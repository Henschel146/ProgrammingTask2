/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.android.example.testapplication.overview

import android.app.Application
import androidx.lifecycle.*
import com.android.example.testapplication.databases.getDatabase
import com.android.example.testapplication.domain.UserDomain
import com.android.example.testapplication.network.User
import com.android.example.testapplication.network.UsersApi
import com.android.example.testapplication.repository.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.lang.Exception

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel (application: Application) : AndroidViewModel(application) {

    /**
     * The data source this ViewModel will fetch results from.
     */
    private val usersRepository = UsersRepository(getDatabase(application))


    val users = usersRepository.users

    // The internal MutableLiveData String that stores the most recent response
    private val _response = MutableLiveData<String>()

    // The external immutable LiveData for the response String
    val response: LiveData<String>
        get() = _response

    private val _navigateToSelectedUser = MutableLiveData<UserDomain?>()
    val navigateToSelectedUser: LiveData<UserDomain?>
        get() = _navigateToSelectedUser

    /**
     * Call getUsers() on init so we can display status immediately.
     */
    init {
            val count: Int? = users.value?.size

        count.let {
            if(count == 0){
                refreshDataFromRepository()
            }
        }


    }



    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                usersRepository.refreshUsers()


            } catch (networkError: IOException) {
                // Show a Toast error message and hide the progress bar.

            }
        }
    }
    fun displayUserDetails(user: UserDomain) {
        _navigateToSelectedUser.value = user
    }

    fun displayUserDetailsComplete() {
        _navigateToSelectedUser.value = null
    }

    fun getLastDatabaseId() : Int {
       val lastIndex = users.value?.lastIndex
        val user = users.value?.get(lastIndex!!)
        var id: Int = 0
        if (user != null) {
            id = user.id
        }
        return id
    }

    fun insertUser(user: UserDomain){
        viewModelScope.launch {
            usersRepository.insertUser(user)
        }

    }

}
