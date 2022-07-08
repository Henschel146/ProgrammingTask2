/*
 *  Copyright 2019, The Android Open Source Project
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.android.example.testapplication.detail

import android.app.Application
import androidx.lifecycle.*
import com.android.example.testapplication.databases.DatabaseUser
import com.android.example.testapplication.databases.UserDao
import com.android.example.testapplication.domain.UserDomain
import com.android.example.testapplication.network.User
import kotlinx.coroutines.launch


/**
 * The [ViewModel] that is associated with the [DetailFragment].
 */
class DetailViewModel(@Suppress("UNUSED_PARAMETER")user: UserDomain, val database: UserDao, app: Application) : AndroidViewModel(app) {

    private val _selectedUser = MutableLiveData<UserDomain>()


    val selectedUser: LiveData<UserDomain>
        get() = _selectedUser

    init {
        _selectedUser.value = user
    }


    fun deleteUser(user: DatabaseUser){
        viewModelScope.launch {
                delete(user)
        }
    }

    fun updateUser(user: DatabaseUser){
        viewModelScope.launch {
            update(user)
        }
    }

   private suspend fun delete(user:DatabaseUser){
       database.delete(user)
    }

   private suspend fun update(user: DatabaseUser){
        database.update(user)
    }

}
