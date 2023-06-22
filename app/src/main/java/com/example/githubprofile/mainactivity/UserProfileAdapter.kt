package com.example.githubprofile.mainactivity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.githubprofile.databinding.ItemProfileBinding
import com.example.githubprofile.usermodeldatabase.UserInfo

class UserProfileAdapter : RecyclerView.Adapter<UserProfileAdapter.UserViewHolder>() {

    private val listFile = ArrayList<UserInfo>()
    private var onItemClickCallBack: OnItemClickCallback? = null

    fun setOnItemClickCallBack(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallBack = onItemClickCallback
    }

    fun setList(users: ArrayList<UserInfo>){
        listFile.clear()
        listFile.addAll(users)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(val binding: ItemProfileBinding) : RecyclerView.ViewHolder(binding.root){
        fun binder(user: UserInfo){

            binding.root.setOnClickListener{
                onItemClickCallBack?.onItemClicked(user)
            }

            binding.apply {
                ivPicture.load(user.avatar_url)
                tvUsername.text = user.login
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.binder(listFile[position])
    }

    override fun getItemCount(): Int = listFile.size

    interface OnItemClickCallback{
        fun onItemClicked(data: UserInfo)
    }
}