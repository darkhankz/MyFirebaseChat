package com.example.myfirebasechat.view.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myfirebasechat.R
import com.example.myfirebasechat.data.User
import com.example.myfirebasechat.view.fragments.ChatFragment
import com.example.myfirebasechat.view.fragments.UsersFragmentDirections
import de.hdodenhof.circleimageview.CircleImageView


class UserAdapter(private val context: Context, private val userList: ArrayList<User>) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private var userClickListener: UserClickListener? = null

    fun setUserClickListener(listener: UserClickListener): UserAdapter {
        userClickListener = listener
        return this
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]
        holder.txtUserName.text = user.userName
        Glide.with(context).load(user.profileImage).placeholder(R.drawable.profile_image)
            .into(holder.imgUser)

        holder.layoutUser.setOnClickListener {

            userClickListener?.onUserClicked(user.userId, user.userName)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val txtUserName: TextView = view.findViewById(R.id.userName)
        val txtTemp: TextView = view.findViewById(R.id.temp)
        val imgUser: CircleImageView = view.findViewById(R.id.userImage)
        val layoutUser: LinearLayout = view.findViewById(R.id.layoutUser)
    }

    interface UserClickListener {
        fun onUserClicked(userId: String, userName: String)
    }

}

