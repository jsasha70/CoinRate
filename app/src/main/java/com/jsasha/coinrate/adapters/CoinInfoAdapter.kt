package com.jsasha.coinrate.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jsasha.coinrate.databinding.ItemCoinInfoBinding
import com.jsasha.coinrate.pojo.CoinPriceInfo
import com.squareup.picasso.Picasso
import android.util.Log

class CoinInfoAdapter : RecyclerView.Adapter<CoinInfoAdapter.CoinInfoViewHolder>() {

    var coinInfoList: MutableList<CoinPriceInfo> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var n = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val binding = ItemCoinInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        log("onCreateViewHolder ${++n}")
        return CoinInfoViewHolder(binding, n)
    }

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = coinInfoList[position]
        holder.tvSymbols.text = coin.fromSymbol + " / " + coin.toSymbol + "(" + (position + 1) + ")"
        holder.tvPrice.text = coin.price.toString()
        holder.tvLastUpdate.text = coin.getFormattedTime()
        Picasso.get().load(coin.getFullImageUrl()).into(holder.ivLogoCoin)
        log("onBindViewHolder ${holder.n}: поз ${position + 1} (всего ${n})")
    }

    override fun getItemCount() = coinInfoList.size

    fun log(s: String) {
        Log.d("TEST_OF_LOADING_DATA", s)
    }

    inner class CoinInfoViewHolder(private val binding: ItemCoinInfoBinding, val n: Int) : RecyclerView.ViewHolder(binding.root) {
        val ivLogoCoin = binding.ivLogoCoin
        val tvSymbols = binding.tvSymbols
        val tvPrice = binding.tvPrice
        val tvLastUpdate = binding.tvLastUpdate
    }
}