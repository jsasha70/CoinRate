package com.jsasha.coinrate.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jsasha.coinrate.databinding.ItemCoinInfoBinding
import com.jsasha.coinrate.pojo.CoinPriceInfo
import com.squareup.picasso.Picasso
import com.jsasha.coinrate.R

class CoinInfoAdapter(private val context: Context) : RecyclerView.Adapter<CoinInfoAdapter.CoinInfoViewHolder>() {

    var coinInfoList: MutableList<CoinPriceInfo> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var n = 0

    var onCoinClickListener: OnCoinClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val binding = ItemCoinInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        log("onCreateViewHolder ${++n}")
        return CoinInfoViewHolder(binding, n)
    }

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = coinInfoList[position]
        holder.tvSymbols.text = String.format(context.resources.getString(R.string.symbols_template), coin.fromSymbol, coin.toSymbol, position + 1)
        holder.tvPrice.text = coin.price.toString()
        holder.tvLastUpdate.text = coin.getFormattedTime()
        Picasso.get().load(coin.getFullImageUrl()).into(holder.ivLogoCoin)
        holder.itemView.setOnClickListener {
            onCoinClickListener?.onCoinClick(coin)
        }
//        log("onBindViewHolder ${holder.n}: поз ${position + 1} (всего ${n})")
    }

    override fun getItemCount() = coinInfoList.size

//    fun log(s: String) {
//        Log.d("TEST_OF_LOADING_DATA", s)
//    }

    inner class CoinInfoViewHolder(binding: ItemCoinInfoBinding, val n: Int) : RecyclerView.ViewHolder(binding.root) {
        val ivLogoCoin = binding.ivLogoCoin
        val tvSymbols = binding.tvSymbols
        val tvPrice = binding.tvPrice
        val tvLastUpdate = binding.tvLastUpdate
    }

    interface OnCoinClickListener {
        fun onCoinClick(coinPriceInfo: CoinPriceInfo)
    }
}