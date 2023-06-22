package com.example.githubprofile.usermodeldatabase

data class UserDataResponse(
    val incomplete_results: Boolean,
    val items: ArrayList<UserInfo>,
    val total_count: Int
)