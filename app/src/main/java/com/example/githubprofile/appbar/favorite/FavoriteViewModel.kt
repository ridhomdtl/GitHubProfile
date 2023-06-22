package com.example.githubprofile.appbar.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.githubprofile.userfavoritedatabase.UserFavorite
import com.example.githubprofile.userfavoritedatabase.UserFavoriteDao
import com.example.githubprofile.userfavoritedatabase.UserFavoriteDatabase

class FavoriteViewModel (appdata: Application): AndroidViewModel(appdata) {

    private var userFavoriteDao: UserFavoriteDao?
    private var userFavoriteDatabase: UserFavoriteDatabase?

    init{
        userFavoriteDatabase = UserFavoriteDatabase.getUserDatabase(appdata)
        userFavoriteDao = userFavoriteDatabase?.userFavoriteDao()
    }

    fun getUserToFavorite(): LiveData<List<UserFavorite>>? {
        return userFavoriteDao?.getUserToFavorite()
    }

}

