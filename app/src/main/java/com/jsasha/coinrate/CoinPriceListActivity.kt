package com.jsasha.coinrate

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jsasha.coinrate.adapters.CoinInfoAdapter
import com.jsasha.coinrate.databinding.ActivityCoinPriceListBinding
import com.jsasha.coinrate.pojo.CoinPriceInfo

class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCoinPriceListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = CoinInfoAdapter(this)
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinPriceInfo: CoinPriceInfo) {
                val intent = CoinDetailActivity.newIntent(this@CoinPriceListActivity, coinPriceInfo.fromSymbol, coinPriceInfo.toSymbol)
                startActivity(intent)
            }
        }
        binding.rvCoinPriceList.adapter = adapter

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(application)
        )[CoinViewModel::class.java]

        viewModel.priceList.observe(this, {
            if (adapter.coinInfoList.size == it.size) {
                for ((i, c) in it.withIndex())
                    adapter.coinInfoList[i] = c
                adapter.notifyDataSetChanged()
            } else {
                adapter.coinInfoList = it
            }
        })
    }
}