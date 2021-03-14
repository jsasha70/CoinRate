package com.jsasha.coinrate

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.jsasha.coinrate.databinding.ActivityCoinDetailBinding
import com.squareup.picasso.Picasso

class CoinDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCoinDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL) ?: ""
        val toSymbol = intent.getStringExtra(EXTRA_TO_SYMBOL) ?: ""
        if (fromSymbol.isEmpty() || toSymbol.isEmpty()) {
            finish()
            return
        }

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(application)
        )[CoinViewModel::class.java]
        viewModel.getDetailInfo(fromSymbol, toSymbol).observe(this, {
            binding.tvPrice.text = it.price.toString()
            binding.tvMinPrice.text = it.lowDay.toString()
            binding.tvMaxPrice.text = it.highDay.toString()
            binding.tvLastMarket.text = it.lastMarket
            binding.tvLastUpdate.text = it.getFormattedTime()
            binding.tvFromSymbol.text = it.fromSymbol
            binding.tvToSymbol.text = it.toSymbol
            Picasso.get().load(it.getFullImageUrl()).into(binding.ivLogoCoin)
        })

    }

    companion object {
        private const val EXTRA_FROM_SYMBOL = "fSym"
        private const val EXTRA_TO_SYMBOL = "tSym"

        fun newIntent(context: Context, fromSymbol: String, toSymbol: String): Intent {
            val intent = Intent(context, CoinDetailActivity::class.java)
            intent.putExtra(EXTRA_FROM_SYMBOL, fromSymbol)
            intent.putExtra(EXTRA_TO_SYMBOL, toSymbol)
            return intent
        }
    }
}