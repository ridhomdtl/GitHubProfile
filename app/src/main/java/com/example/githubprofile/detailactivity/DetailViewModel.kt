package com.example.githubprofile.detailactivity

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubprofile.apiservice.ApiClient
import com.example.githubprofile.userfavoritedatabase.UserFavorite
import com.example.githubprofile.userfavoritedatabase.UserFavoriteDao
import com.example.githubprofile.userfavoritedatabase.UserFavoriteDatabase
import com.example.githubprofile.usermodeldatabase.UserDetail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(appdata: Application): AndroidViewModel(appdata) {

    private var userFavoriteDao: UserFavoriteDao?
    private var userFavoriteDatabase: UserFavoriteDatabase?

    val userList = MutableLiveData<UserDetail>()

    init{
        userFavoriteDatabase = UserFavoriteDatabase.getUserDatabase(appdata)
        userFavoriteDao = userFavoriteDatabase?.userFavoriteDao()
    }

    fun setDetail(userlogin: String){
        ApiClient.apiInstance
            .getDetail(userlogin)
            .enqueue(object : Callback<UserDetail>{
                override fun onResponse(call: Call<UserDetail>, response: Response<UserDetail>) {
                    if (response.isSuccessful){
                        userList.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<UserDetail>, t: Throwable) {
                    t.message?.let { Log.d( "Failure", it ) }
                }

            })
    }

    fun getUserToDetail(): LiveData<UserDetail> {
        return userList
    }

    fun addUserToFavorite(username: String, useravatar: String, userid: Int){
        CoroutineScope(Dispatchers.IO).launch {
            val addUserFavorite = UserFavorite(username, useravatar, userid)
            userFavoriteDao?.addUserToFavorite(addUserFavorite)
        }

    }

    fun removeUserFavorite(userid: Int){
        CoroutineScope(Dispatchers.IO).launch {
            val removeUserFavorite = userid
            userFavoriteDao?.removeUserFavorite(removeUserFavorite)
        }
    }

    suspend fun userCheck(userid: Int) = userFavoriteDao?.userCheck(userid)
}