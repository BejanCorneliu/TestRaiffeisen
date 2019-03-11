package com.raiffeisen.bejancorneliu.screens.first

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.raiffeisen.bejancorneliu.BR
import com.raiffeisen.bejancorneliu.R
import com.raiffeisen.bejancorneliu.database.tables.Users

class FirstScreenAdapter(private var mActivity : FirstScreen,private var mData : MutableList<Users> = ArrayList()) : RecyclerView.Adapter<FirstScreenAdapter.FirstScreenViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FirstScreenViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding

        binding = DataBindingUtil.inflate(layoutInflater, R.layout.row_users, parent, false)

        return FirstScreenViewHolder(binding, mActivity, this)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: FirstScreenViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    class FirstScreenViewHolder(val binding: ViewDataBinding, private val mFragment : FirstScreen, private val mAdapter: FirstScreenAdapter) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Users) {
            binding.setVariable(BR.adapter, mAdapter)
            binding.setVariable(BR.data, data)
            binding.executePendingBindings()
        }
    }

    fun addData(mNewData : List<Users>){
        mData.addAll(mNewData)
        notifyDataSetChanged()
    }
}