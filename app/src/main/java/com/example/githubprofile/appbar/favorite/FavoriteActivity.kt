package com.example.githubprofile.appbar.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubprofile.R
import com.example.githubprofile.detailactivity.DetailActivity
import com.example.githubprofile.mainactivity.UserProfileAdapter
import com.example.githubprofile.databinding.ActivityFavoriteBinding
import com.example.githubprofile.userfavoritedatabase.UserFavorite
import com.example.githubprofile.usermodeldatabase.UserInfo

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: UserProfileAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setIcon(R.drawable.ic_action_name)
        supportActionBar?.title=("  GitHub Profile")

        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        adapter = UserProfileAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallBack(object : UserProfileAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserInfo) {
                Intent(this@FavoriteActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USERLOGIN, data.login)
                    it.putExtra(DetailActivity.EXTRA_USERAVATAR, data.avatar_url)
                    it.putExtra(DetailActivity.EXTRA_USERID, data.id)
                    startActivity(it)
                }
            }

        })

        binding.apply {
            rvFavoriteProfile.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvFavoriteProfile.setHasFixedSize(true)
            rvFavoriteProfile.adapter = adapter
        }

        viewModel.getUserToFavorite()?.observe(this, {
            if (it != null){
                val listUser = userList(it)
                loadingBar(false)
                adapter.setList(listUser)
            }else{
                loadingBar(true)
            }
            if (it.isEmpty()){
                emptyList(true)
            }else{
                emptyList(false)
            }
        })
    }

    private fun userList(userList: List<UserFavorite>): ArrayList<UserInfo>{
        val favoriteUser = ArrayList<UserInfo>()
        for (i in userList){
            val userMap = UserInfo(i.avatar_url, i.id, i.login)
            favoriteUser.add(userMap)
        }
        return favoriteUser
    }

    private fun loadingBar(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun emptyList(state: Boolean) {
        binding.tvNotFound.visibility = if (state) View.VISIBLE else View.GONE
    }

}