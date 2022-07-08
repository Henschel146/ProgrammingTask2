/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.android.example.testapplication.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.example.testapplication.databinding.ItemViewBinding
import com.android.example.testapplication.domain.UserDomain
import com.android.example.testapplication.network.User

class UserAdapter( private val onClickListener: OnClickListener ) : ListAdapter<UserDomain,
        UserAdapter.UserViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.UserViewHolder {

        return UserViewHolder(ItemViewBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: UserAdapter.UserViewHolder, position: Int) {
        val user = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(user)
        }
        holder.bind(user)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<UserDomain>() {

        override fun areItemsTheSame(oldItem: UserDomain, newItem: UserDomain): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: UserDomain, newItem: UserDomain): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class UserViewHolder(
        private var binding:
        ItemViewBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UserDomain) {
            binding.user = user
            binding.executePendingBindings()
        }
    }
    class OnClickListener(val clickListener: (user:UserDomain) -> Unit) {
        fun onClick(user:UserDomain) = clickListener(user)
    }
}




