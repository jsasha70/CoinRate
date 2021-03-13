package com.jsasha.coinrate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jsasha.coinrate.adapters.CoinInfoAdapter
import com.jsasha.coinrate.databinding.ActivityCoinPriceListBinding

class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCoinPriceListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = CoinInfoAdapter()
        binding.rvCoinPriceList.adapter = adapter

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(application)
        )[CoinViewModel::class.java]

        viewModel.priceList.observe(this, Observer {
            if (adapter.coinInfoList.size == it.size) {
                for ((i, c) in it.withIndex())
                    adapter.coinInfoList[i] = c
            } else {
                adapter.coinInfoList = it
            }
        })
    }
}