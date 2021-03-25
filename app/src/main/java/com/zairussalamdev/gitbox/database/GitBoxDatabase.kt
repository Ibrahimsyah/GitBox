package com.zairussalamdev.gitbox.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zairussalamdev.gitbox.data.entities.User

@Database(entities = [User::class], version = 1)
abstract class GitBoxDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        private const val DB_NAME = "GitBox_DB"

        @Volatile
        private var instance: GitBoxDatabase? = null

        fun getInstance(context: Context): GitBoxDatabase {
            if (instance == null) {
                synchronized(this) {
                    instance = Room.databaseBuilder(
                        context,
                        GitBoxDatabase::class.java, DB_NAME
                    ).build()
                }
            }
            return instance as GitBoxDatabase
        }
    }
}