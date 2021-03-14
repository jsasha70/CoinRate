package com.jsasha.coinrate

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.jsasha.coinrate.api.ApiFactory
import com.jsasha.coinrate.database.AppDatabase
import com.jsasha.coinrate.pojo.CoinPriceInfo
import com.jsasha.coinrate.pojo.CoinPriceInfoRawData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val compositeDisposable = CompositeDisposable()

    val priceList = db.coinPriceInfoDao().getPriceList()

    fun getDetailInfo(fSym: String, tSym: String): LiveData<CoinPriceInfo> {
        return db.coinPriceInfoDao().getPriceInfoAboutCoin(fSym, tSym)
    }

    init {
        loadData()
    }

    private fun loadData() {
        val disposable = ApiFactory.apiService.getTopCoinsInfo()
            .map {
                it.data?.map { it.coinInfo?.name }?.joinToString(",") ?: ""
            }
            .flatMap {
                ApiFactory.apiService.getFullPriceList(it)
            }
            .map {
                getPriceListFromRowData(it)
            }
            .delaySubscription(10, TimeUnit.SECONDS) // !! задержка подписки с первого шага !! ???
            .repeat()
            .retry()
            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread()) - операции с БД не д.б. в главном потоке
            .subscribe({
                db.coinPriceInfoDao().insertPriceList(it)
            }, {
//                Log.d("TEST_OF_LOADING_DATA_3", it.message ?: "error with no error text")
            })
        compositeDisposable.add(disposable)
    }

    private fun getPriceListFromRowData(
        coinPriceInfoRawData: CoinPriceInfoRawData
    ): MutableList<CoinPriceInfo> {
        val ret = mutableListOf<CoinPriceInfo>()
        val jsonObject = coinPriceInfoRawData.raw ?: return ret

        for (coinKey in jsonObject.keySet()) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            for (curKey in currencyJson.keySet()) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(curKey),
                    CoinPriceInfo::class.java
                )
                ret.add(priceInfo)
            }
        }

        return ret
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}