package com.example.chatwithfirebase.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.example.chatwithfirebase.BR
import com.example.chatwithfirebase.base.BaseActivityGradient
import com.example.chatwithfirebase.R
import com.example.chatwithfirebase.base.OnItemClickListener
import com.example.chatwithfirebase.base.manager.SharedPreferencesManager
import com.example.chatwithfirebase.databinding.ActivityHomeBinding
import com.example.chatwithfirebase.di.ViewModelFactory
import com.example.chatwithfirebase.ui.home.adapter.UserAdapter
import com.example.chatwithfirebase.ui.login.LoginActivity
import com.example.chatwithfirebase.ui.message.MessageActivity
import com.example.chatwithfirebase.ui.setting.SettingActivity
import com.example.chatwithfirebase.ui.setting.SettingViewModel
import javax.inject.Inject

class HomeActivity : BaseActivityGradient<ActivityHomeBinding, HomeViewModel>(),
    OnItemClickListener {

    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var homeViewModel: HomeViewModel

    @Inject
    lateinit var userAdapter: UserAdapter

    @Inject
    lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun getViewModel(): HomeViewModel {
        homeViewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)
        return homeViewModel
    }

    override fun getLayoutId(): Int = R.layout.activity_home

    override fun getBindingVariable(): Int = BR.homeViewModel

    override fun updateUI(savedInstanceState: Bundle?) {

        // get info user
        homeViewModel.getInfoUser()
        homeViewModel.getUser().observe(this, {
            it?.let {
                binding.user = it
                sharedPreferencesManager.saveUrlAvatar(it.avatarUser)
            }
        })

        // set adapter
        binding.rvListUser.apply {
            setHasFixedSize(true)
            adapter = userAdapter
        }

        // get all user
        homeViewModel.getAllUser()
        homeViewModel.getUserList().observe(this, {
            if (it != null) {
                userAdapter.clearItems()
                userAdapter.addItems(it)
            } else {
                toast(resources.getString(R.string.error_get_data))
            }
        })

        // on Item Click
        userAdapter.setOnItemClickListener(this)
        homeViewModel.getInfoReceiver().observe(this, {
            goScreenAndPutString(
                MessageActivity::class.java,
                false,
                it.userId,
                sharedPreferencesManager.getUrlAvatar(),
                R.anim.slide_in_right,
                R.anim.slide_out_left,
            )
        })

        // search for user
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                homeViewModel.searchForUser(newText.toString().toLowerCase())
                return false
            }

        })

        binding.imgSetting.setOnClickListener {
            goScreen(
                SettingActivity::class.java,
                false, R.anim.slide_in_right, R.anim.slide_out_left
            )
        }

    }

    override fun onItemClick(position: Int) {
        homeViewModel.onItemClickGetPositionUser(position)
    }


}