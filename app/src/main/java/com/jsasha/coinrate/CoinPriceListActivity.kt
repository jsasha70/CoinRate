package com.jsasha.coinrate

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jsasha.coinrate.databinding.ActivityCoinPriceListBinding

class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCoinPriceListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(application)
        )[CoinViewModel::class.java]

//        viewModel.priceList.observe(this, Observer {
//            Log.d("TEST_OF_LOADING_DATA_5", it[0].toString())
//        })

        viewModel.getDetailInfo("BTC", "USD").observe(this, Observer {
            Log.d("TEST_OF_LOADING_DATA_6", it.toString())
        })
    }
}