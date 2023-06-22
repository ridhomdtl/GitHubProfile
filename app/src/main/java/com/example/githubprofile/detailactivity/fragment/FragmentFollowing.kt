package com.example.githubprofile.detailactivity.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubprofile.R
import com.example.githubprofile.databinding.FragmentUserFollowBinding
import com.example.githubprofile.detailactivity.DetailActivity
import com.example.githubprofile.mainactivity.UserProfileAdapter

class FragmentFollowing: Fragment(R.layout.fragment_user_follow){


    private var fragmentbinding : FragmentUserFollowBinding? = null
    private val binding get() = fragmentbinding!!
    private lateinit var viewModel: FollowingViewModel
    private lateinit var adapter: UserProfileAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val argument = arguments
        username = argument?.getString(DetailActivity.EXTRA_USERLOGIN).toString()
        fragmentbinding = FragmentUserFollowBinding.bind(view)

        adapter = UserProfileAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvProfile.setHasFixedSize(true)
            rvProfile.layoutManager = LinearLayoutManager(activity)
            rvProfile.adapter = adapter
        }

        loadingBar(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowingViewModel::class.java)
        viewModel.setFollowingList(username)
        viewModel.getFollowingList().observe(viewLifecycleOwner) {
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

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentbinding = null
    }

    private fun loadingBar(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun emptyList(state: Boolean) {
        binding.tvNotFound.text = resources.getString(R.string.no_following)
        binding.tvNotFound.visibility = if (state) View.VISIBLE else View.GONE
    }

}