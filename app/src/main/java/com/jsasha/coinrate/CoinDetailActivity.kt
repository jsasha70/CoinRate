package com.jsasha.coinrate

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider

class CoinDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_detail)

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
            Log.d("TEST_OF_LOADING_DATA_!", it.toString())
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