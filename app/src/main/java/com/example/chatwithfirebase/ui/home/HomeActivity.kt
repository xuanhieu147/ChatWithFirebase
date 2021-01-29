package com.example.chatwithfirebase.ui.home

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatwithfirebase.BR
import com.example.chatwithfirebase.base.BaseActivityGradient
import com.example.chatwithfirebase.R
import com.example.chatwithfirebase.databinding.ActivityHomeBinding
import com.example.chatwithfirebase.di.ViewModelFactory
import com.example.chatwithfirebase.ui.home.adapter.HomeAdapter
import com.example.chatwithfirebase.utils.ToastUtils
import javax.inject.Inject
import javax.inject.Named

class HomeActivity : BaseActivityGradient<ActivityHomeBinding, HomeViewModel>() {

    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var homeViewModel: HomeViewModel

    @Inject
    @field:Named("vertical")
    lateinit var layoutManager: LinearLayoutManager

    @Inject
    lateinit var homeAdapter: HomeAdapter

    override fun getViewModel(): HomeViewModel {
        homeViewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)
        return homeViewModel
    }

    override fun getLayoutId(): Int = R.layout.activity_home

    override fun getBindingVariable(): Int = BR.homeViewModel

    override fun updateUI(savedInstanceState: Bundle?) {
        // set adapter
        binding.rvListUser.setHasFixedSize(true)
        binding.rvListUser.layoutManager = layoutManager
        binding.rvListUser.adapter = homeAdapter

        // get data user
        homeViewModel.getData()
        homeViewModel.getUserList().observe(this,{
            if(it!=null){
                homeAdapter.clearItems()
                homeAdapter.addItems(it)
            }
            else{
                ToastUtils.toastError(this.applicationContext,R.string.error,R.string.error_get_data)
            }
        })

    }


}