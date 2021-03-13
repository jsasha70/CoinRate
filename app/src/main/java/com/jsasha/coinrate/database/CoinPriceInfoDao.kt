package com.jsasha.coinrate.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jsasha.coinrate.pojo.CoinPriceInfo

@Dao
interface CoinPriceInfoDao {
    @Query("select * from full_price_list order by lastUpdate desc")
    fun getPriceList(): LiveData<MutableList<CoinPriceInfo>>

    @Query("select * from full_price_list where fromSymbol=:fSym and toSymbol=:tSym limit 1")
    fun getPriceInfoAboutCoin(fSym: String, tSym: String): LiveData<CoinPriceInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPriceList(priceList: List<CoinPriceInfo>)
}