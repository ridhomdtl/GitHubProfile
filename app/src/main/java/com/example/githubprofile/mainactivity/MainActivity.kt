package com.example.githubprofile.mainactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubprofile.detailactivity.DetailActivity
import com.example.githubprofile.R
import com.example.githubprofile.databinding.ActivityMainBinding
import com.example.githubprofile.appbar.setting.dataStore
import com.example.githubprofile.appbar.favorite.FavoriteActivity
import com.example.githubprofile.appbar.setting.SettingActivity
import com.example.githubprofile.appbar.setting.SettingPreferences
import com.example.githubprofile.usermodeldatabase.UserInfo

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var adapter: UserProfileAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pref = SettingPreferences.getInstance(dataStore)
        binding = ActivityMainBinding.inflate(layoutInflater)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setIcon(R.drawable.ic_action_name)
        supportActionBar?.title=("  GitHub Profile")

        setContentView(binding.root)

        viewModel = ViewModelProvider(this, UserViewModelFactory(pref)).get(
            UserViewModel::class.java)
        
        viewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        adapter = UserProfileAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallBack(object : UserProfileAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserInfo) {
                Intent(this@MainActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USERLOGIN, data.login)
                    it.putExtra(DetailActivity.EXTRA_USERAVATAR, data.avatar_url)
                    it.putExtra(DetailActivity.EXTRA_USERID, data.id)
                    startActivity(it)
                }
            }

        })

        binding.apply {
            rvProfile.layoutManager = LinearLayoutManager(this@MainActivity)
            rvProfile.setHasFixedSize(true)
            rvProfile.adapter = adapter

            btnSearchbar.setOnClickListener{
                userSearch()
            }
            etSearch.setOnKeyListener { _, keyCode, event ->
                if(event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                    userSearch()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }

        }

        viewModel.getSearchUser().observe(this) {
            if (it != null) {
                adapter.setList(it)
                loadingBar(false)
            }
            if (it.isEmpty()){
                emptyList(true)
            }else{
                emptyList(false)
            }
        }

    }

    private fun userSearch(){
        binding.apply {
            val query = etSearch.text.toString()
            if (query.isEmpty()) return
            loadingBar(true)
            viewModel.setSearchUsers(query)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favorite_button -> {
                Intent(this, FavoriteActivity::class.java).also {
                    startActivity(it)
                }
            }
            R.id.setting_button -> {
                Intent(this, SettingActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadingBar(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun emptyList(state: Boolean) {
        binding.tvNotFound.visibility = if (state) View.VISIBLE else View.GONE
    }

}