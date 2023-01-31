package com.amin.cryptocoin.feauters

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.amin.cryptocoin.apiManager.BASE_URL_IMAGE
import com.amin.cryptocoin.apiManager.model.CoinsData
import com.amin.cryptocoinforresume.R
import com.amin.cryptocoinforresume.databinding.ActivityCoinBinding
import com.amin.cryptocoinforresume.feauters.ProfileActivity
import com.bumptech.glide.Glide

class CoinActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCoinBinding
    private lateinit var dataThisCoin : CoinsData.Data
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataThisCoin = intent.getParcelableExtra<CoinsData.Data>("aaa")!!
        binding.includeToolbarInCoinAc.txtCoinName.text = dataThisCoin.coinInfo.fullName

        binding.includeToolbarInCoinAc.imgViewProfile.setOnClickListener {
            val intent = Intent(this , ProfileActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        initUI()
    }

    private fun initUI() {
        initChartUI()
        initStatisticUI()
        initAboutUI()
    }

    private fun initAboutUI() {

    }

    private fun initStatisticUI() {
        if (dataThisCoin != null){
            binding.includeStatisticInCoinAc.txtOpenAmount.text =  dataThisCoin.dISPLAY.uSD.oPEN24HOUR
            binding.includeStatisticInCoinAc.txtTodayHigh.text =   dataThisCoin.dISPLAY.uSD.hIGH24HOUR
            binding.includeStatisticInCoinAc.txtTodayLow.text =  dataThisCoin.dISPLAY.uSD.lOW24HOUR
            binding.includeStatisticInCoinAc.txtTodayChange.text =  dataThisCoin.dISPLAY.uSD.cHANGE24HOUR
            binding.includeStatisticInCoinAc.txtVolumeHour.text =  dataThisCoin.dISPLAY.uSD.vOLUMEHOURTO
            binding.includeStatisticInCoinAc.txtDayVolume.text =  dataThisCoin.dISPLAY.uSD.vOLUMEDAYTO
            binding.includeStatisticInCoinAc.txtMarketCapStatistic.text =  dataThisCoin.dISPLAY.uSD.mKTCAP
            binding.includeStatisticInCoinAc.txtSupply.text =  dataThisCoin.dISPLAY.uSD.sUPPLY
        }
    }

    private fun initChartUI() {
        var change = dataThisCoin.rAW.uSD.cHANGEPCT24HOUR.toString().toDouble()
        if (change > 0.00){
            binding.includeChartInCoinAc.txtChartChangePercent.text = "+" + change.toString().substring(0 , 4) +"%"
            binding.includeChartInCoinAc.txtChartChangePercent.setTextColor(ContextCompat.getColor(this , R.color.colorGain))
            binding.includeChartInCoinAc.imgViewChartUpArrow.visibility = View.VISIBLE

        } else if(change in -0.001 .. -0.009){
            binding.includeChartInCoinAc.txtChartChangePercent.text =  "0.00%"
            binding.includeChartInCoinAc.txtChartChangePercent.setTextColor(ContextCompat.getColor(this , R.color.white))
        } else if(change < 0.00){
            binding.includeChartInCoinAc.txtChartChangePercent.text =   change.toString().substring(0 , 5) +"%"
            binding.includeChartInCoinAc.txtChartChangePercent.setTextColor(ContextCompat.getColor(this , R.color.colorLoss))
            binding.includeChartInCoinAc.imgViewChartDownArrow.visibility = View.VISIBLE
        }
            binding.includeChartInCoinAc.txtChartPrice.text = dataThisCoin.dISPLAY.uSD.pRICE
            Glide.with(this).load( BASE_URL_IMAGE +  dataThisCoin.coinInfo.imageUrl).into(binding.includeChartInCoinAc.imgViewCoinAvatar)
    }
}