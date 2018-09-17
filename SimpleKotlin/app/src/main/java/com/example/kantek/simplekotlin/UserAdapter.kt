package com.example.kantek.simplekotlin

import android.annotation.SuppressLint
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.android.support.kotlin.core.base.BaseViewHolder
import com.android.support.kotlin.core.base.PagedRecyclerAdapter
import kotlinx.android.synthetic.main.item_view_user.view.*

class UserAdapter(view: RecyclerView) : PagedRecyclerAdapter<User>(view, DIFF_CALLBACK) {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int) = ViewHolder(p0)

    class ViewHolder(parent: ViewGroup) : BaseViewHolder<User>(parent, R.layout.item_view_user) {

        @SuppressLint("SetTextI18n")
        override fun bind(item: User) {
            super.bind(item)
            itemView.txtName.text = "${item.firstName} ${item.lastName}"
            itemView.txtAge.text = "${item.id}"
        }
    }

}

val DIFF_CALLBACK = object : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(p0: User, p1: User) =
            p0.id == p1.id

    override fun areContentsTheSame(p0: User, p1: User) =
            p0.avatar == p1.avatar
                    && p0.firstName == p1.firstName
                    && p0.lastName == p1.lastName
}
