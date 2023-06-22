package com.example.githubprofile.detailactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.githubprofile.R
import com.example.githubprofile.databinding.ActivityDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setIcon(R.drawable.ic_action_name)
        supportActionBar?.title=("  GitHub Profile")

        setContentView(binding.root)

        val userlogin = intent.getStringExtra(EXTRA_USERLOGIN)
        val useravatar = intent.getStringExtra(EXTRA_USERAVATAR)
        val userid = intent.getIntExtra(EXTRA_USERID, 0)

        val bundle = Bundle()
        bundle.putString(EXTRA_USERLOGIN, userlogin)

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel.setDetail(userlogin!!)
        viewModel.getUserToDetail().observe(this, {
            if (it != null) {
                loadingBar(false)
                binding.apply {

                    val usernamecheck: String?

                    when (it.name) {
                        null -> usernamecheck = "GitHub User"
                        else -> usernamecheck = it.name.toString()
                    }

                    val followersNumInt = it.followers
                    val followingNumInt = it.following

                    val followersNumStr = followersNumInt.toString()
                    val followingNumStr = followingNumInt.toString()

                    ivPictureDetail.load(it.avatar_url)
                    tvNameDetail.text = usernamecheck
                    tvUsernameDetail.text = it.login
                    tvFollowersDetail.text =
                        resources.getString(R.string.followers_num, followersNumStr)
                    tvFollowingDetail.text =
                        resources.getString(R.string.following_num, followingNumStr)
                }
            }
        })

        val detailPagerAdapter = DetailPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = detailPagerAdapter
            tabLayout.setupWithViewPager(viewPager)
            loadingBar(true)
        }

        var check = false
        CoroutineScope(Dispatchers.IO).launch {
            val usercount = viewModel.userCheck(userid)
            withContext(Dispatchers.Main){
                if (usercount != null){
                    if(usercount==1){
                        binding.tbFavorite.isChecked = true
                        check = true
                    }else {
                        binding.tbFavorite.isChecked = false
                        check = false
                    }
                }
            }
        }

        binding.tbFavorite.setOnClickListener {
            check = !check

            if (check){
                if (useravatar != null) {
                    viewModel.addUserToFavorite(userlogin, useravatar, userid)
                }
            }else{
                viewModel.removeUserFavorite(userid)
            }
            binding.tbFavorite.isChecked = check
        }
    }

    private fun loadingBar(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    companion object{
        const val EXTRA_USERLOGIN = "extra_login"
        const val EXTRA_USERID = "extra_id"
        const val EXTRA_USERAVATAR = "extra_avatar"
    }

}