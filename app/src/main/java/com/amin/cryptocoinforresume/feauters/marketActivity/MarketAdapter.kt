package com.amin.cryptocoin.feauters.marketActivity

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.amin.cryptocoin.apiManager.BASE_URL_IMAGE
import com.amin.cryptocoin.apiManager.model.CoinsData
import com.amin.cryptocoinforresume.R
import com.amin.cryptocoinforresume.databinding.ItemRecyclerWatchlistBinding
import com.bumptech.glide.Glide
import java.util.ArrayList


// 1. add dependency
// 2. add call adapter factory
// 3. return Single/Observable/Completable rx except call retrofit in ApiService

class MarketAdapter(private var list: ArrayList<CoinsData.Data> , private var marketEventINF: MarketEventINF) : RecyclerView.Adapter<MarketAdapter.MarketViewHolder>() {
    private lateinit var binding: ItemRecyclerWatchlistBinding
    inner class MarketViewHolder(private val binding: ItemRecyclerWatchlistBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(position: Int) {
//            binding.itemTvTitleRV.text = list[position].title
//            itemView.setOnLongClickListener

            try {
                val listPosition = list[position]
                binding.txtCoinShortName.text = listPosition.coinInfo.name
                binding.txtCounterNum.text = (adapterPosition + 1).toString()
                binding.txtPrice.text =  listPosition.dISPLAY.uSD.pRICE

                val marketCap = listPosition.rAW.uSD.mKTCAP / 1000000000
                var dotIndex = marketCap.toString().indexOf('.')
                binding.txtMarketCap.text = marketCap.toString().substring(0 , dotIndex + 2) + " B"

                var change = listPosition.rAW.uSD.cHANGEPCT24HOUR.toString().toDouble()
                if (change > 0.00){
                    binding.txtIncreaseOrDecrease.text = "+" + change.toString().substring(0 , 4) +"%"
                    binding.txtIncreaseOrDecrease.setBackgroundResource(R.drawable.shape_increase)
                } else if(change in -0.001 .. -0.009){
                    binding.txtIncreaseOrDecrease.text =  "0.00%"
                    binding.txtIncreaseOrDecrease.setBackgroundResource(R.drawable.shape_0)
                } else if(change < 0.00){
                    binding.txtIncreaseOrDecrease.text =   change.toString().substring(0 , 5) +"%"
                    binding.txtIncreaseOrDecrease.setBackgroundResource(R.drawable.shape_decrease)
                }

                Glide.with(binding.root)
                    .load(BASE_URL_IMAGE + listPosition.coinInfo.imageUrl)
                    .into(binding.imgCoin)

                itemView.setOnClickListener {
                    marketEventINF.onCoinItemClicked(listPosition)
                }
            }catch (e:Exception){
                Toast.makeText(itemView.context, "data failed to download", Toast.LENGTH_SHORT).show()
            }

        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketViewHolder {
        val binding = ItemRecyclerWatchlistBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        return MarketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MarketViewHolder, position: Int) {
        holder.bindData(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface  MarketEventINF{
       fun onCoinItemClicked(coinData: CoinsData.Data)
    }


}