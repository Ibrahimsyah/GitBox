package com.zairussalamdev.gitbox.database

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.zairussalamdev.gitbox.data.entities.User

@Dao
interface UserDao {
    @Query("select * from users")
    fun getAll(): LiveData<List<User>>

    @Query("select * from users")
    fun getAllAsCursor(): Cursor

    @Query("select count(*) != 0 from users u where u.username = :username")
    fun getByUsername(username: String): LiveData<Boolean>

    @Insert
    fun insert(user: User)

    @Delete
    fun delete(user: User)
}