package com.example.granne

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ChatRecyclerAdapter(
    private var nickname: List<String>,
    private var userUid: MutableList<String>,
) : RecyclerView.Adapter<ChatRecyclerAdapter.ViewHolder>() {

    var auth = Firebase.auth
    val db = Firebase.firestore

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val itemNickname: TextView = itemView.findViewById(R.id.nicknameText)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.user_item_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemNickname.text = nickname[position]

        holder.itemView.setOnClickListener {
            //When pressing on a user in the list, go to ChatRoomActivity
            val intent = Intent(holder.itemView.context, ChatRoomActivity::class.java)
                .putExtra("secondUserNickname", nickname[position])
                .putExtra("secondUserUid", userUid[position])
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return nickname.size
    }

}