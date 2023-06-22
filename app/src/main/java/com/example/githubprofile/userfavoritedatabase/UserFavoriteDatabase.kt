package com.example.githubprofile.userfavoritedatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [UserFavorite::class],
    version = 2
)

abstract class UserFavoriteDatabase: RoomDatabase() {

    abstract fun userFavoriteDao(): UserFavoriteDao

    companion object{
        @Volatile
        private var INSTANCE: UserFavoriteDatabase? = null

        @JvmStatic
        fun getUserDatabase(context: Context): UserFavoriteDatabase{
            if(INSTANCE == null){
                synchronized(UserFavoriteDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, UserFavoriteDatabase::class.java, "github user database").build()
                }
            }
            return INSTANCE as UserFavoriteDatabase
        }
    }

}