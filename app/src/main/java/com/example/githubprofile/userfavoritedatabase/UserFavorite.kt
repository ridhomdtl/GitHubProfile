package com.example.githubprofile.userfavoritedatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorite_github_user")
data class UserFavorite(
    @ColumnInfo(name = "login")
    val login: String,
    @ColumnInfo(name = "avatar_url")
    val avatar_url: String,
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
): Serializable
