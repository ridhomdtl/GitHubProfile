package com.example.githubprofile.userfavoritedatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserFavoriteDao {
    @Insert
    suspend fun addUserToFavorite(userFavorite: UserFavorite)

    @Query("DELETE FROM favorite_github_user WHERE favorite_github_user.id = :id")
    suspend fun removeUserFavorite(id: Int): Int

    @Query("SELECT * from favorite_github_user")
    fun getUserToFavorite(): LiveData<List<UserFavorite>>

    @Query("SELECT count(*) FROM favorite_github_user WHERE favorite_github_user.id = :id LIMIT 1")
    suspend fun userCheck(id: Int): Int
}