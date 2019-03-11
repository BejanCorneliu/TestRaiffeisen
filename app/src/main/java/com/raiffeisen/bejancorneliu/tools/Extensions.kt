package com.raiffeisen.bejancorneliu.tools

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.raiffeisen.bejancorneliu.database.tables.Users
import com.raiffeisen.bejancorneliu.screens.first.FirstScreen
import com.raiffeisen.bejancorneliu.screens.first.FirstScreenAdapter
import android.widget.LinearLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.raiffeisen.bejancorneliu.screens.first.FirstViewModel

fun RecyclerView.setUsersAdapter(nActivity: FirstScreen,mData : MutableList<Users>,mViewModel : FirstViewModel): RecyclerView.LayoutManager {
    adapter = FirstScreenAdapter(nActivity,mData)
    layoutManager = GridLayoutManager(context, 1)
    addItemDecoration(DividerItemDecoration(nActivity, LinearLayout.VERTICAL))

    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            layoutManager?.let {
                val visibleItemCount = it.childCount
                val totalItemCount = it.itemCount
                val firstVisibleItemPosition  = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                with (mViewModel) {
                    if (!mIsLoading) {
                        if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                            mIsLoading = true
                            getNextPage()
                        }
                    }
                }
            }
        }
    })

    return layoutManager as GridLayoutManager
}

fun RecyclerView.addNewData(mNewdata : List<Users>) {
    (adapter as FirstScreenAdapter).addData(mNewdata)
}