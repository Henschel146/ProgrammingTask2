package com.android.example.testapplication.databases

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao{

    @Query("select * from databaseuser")
     fun getUsers(): LiveData<List<DatabaseUser>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( users: List<DatabaseUser>)

    @Delete
    suspend fun delete(user: DatabaseUser)

    @Insert
    suspend fun insert(user: DatabaseUser)

    @Update
    suspend fun update(user: DatabaseUser)

    @Query("SELECT * from databaseuser WHERE id = :key")
    suspend fun get(key: Int): DatabaseUser?


}

@Database(entities = [DatabaseUser::class], version = 1)
abstract class UsersDatabase: RoomDatabase() {
    abstract val userDao: UserDao
}

private lateinit var INSTANCE: UsersDatabase

fun getDatabase(context: Context): UsersDatabase {
    synchronized(UsersDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                UsersDatabase::class.java,
                "users").build()
        }
    }
    return INSTANCE
}

