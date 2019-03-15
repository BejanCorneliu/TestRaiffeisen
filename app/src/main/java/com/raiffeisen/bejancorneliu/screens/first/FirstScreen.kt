package com.raiffeisen.bejancorneliu.screens.first

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.raiffeisen.bejancorneliu.R
import com.raiffeisen.bejancorneliu.database.tables.Users
import com.raiffeisen.bejancorneliu.databinding.ActivityMainBinding
import com.raiffeisen.bejancorneliu.screens.second.SecondScreen
import com.raiffeisen.bejancorneliu.tools.ViewModelFactory
import com.raiffeisen.bejancorneliu.tools.addNewData
import com.raiffeisen.bejancorneliu.tools.setUsersAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

class FirstScreen : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    fun onUserClick(nUser: Users) {
       startActivity(Intent(this,SecondScreen::class.java))
    }

    private lateinit var mViewModel : FirstViewModel
    private lateinit var mBinding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mViewModel = ViewModelProviders.of(this, ViewModelFactory(this)).get(FirstViewModel::class.java)
        mBinding.activity = this
        mBinding.viewmodel =  mViewModel
        mBinding.executePendingBindings()
        mBinding.lifecycleOwner = this

        initUI()

        with(mViewModel) {
            mUsersList.observe(this@FirstScreen, Observer {
                it?.let {
                    mUsers.addNewData(it)
                }
            })

            mAddNewDataFlag.observe(this@FirstScreen, Observer {
                it?.let {
                    if (it) {
                        mUsers.addNewData(mNewData)
                        resetLoadingNextPage()
                    }
                }
            })
        }
    }

    private fun initUI() {

        mUsers.setUsersAdapter(this@FirstScreen, ArrayList(),mViewModel)

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}