package com.example.kantek.simplekotlin

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.android.support.kotlin.core.base.BaseRecyclerAdapter
import kotlinx.android.synthetic.main.item_view_user.view.*

class UserAdapter(view: RecyclerView) : BaseRecyclerAdapter<User>(view) {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int) =
            ViewHolder(p0, R.layout.item_view_user)

    class ViewHolder(parent: ViewGroup, id: Int) : BaseRecyclerAdapter.ViewHolder<User>(parent, id) {

        @SuppressLint("SetTextI18n")
        override fun bind(item: User) {
            super.bind(item)
            itemView.txtName.text = "${item.firstName} ${item.lastName}"
            itemView.txtAge.text = "$item.id"
        }
    }

}