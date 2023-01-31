package com.amin.cryptocoin.feauters.marketActivity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.coordinatorlayout.R
import androidx.recyclerview.widget.LinearLayoutManager
import com.amin.cryptocoin.apiManager.ApiManager
import com.amin.cryptocoin.apiManager.model.CoinsData
import com.amin.cryptocoin.feauters.CoinActivity
import com.amin.cryptocoinforresume.databinding.ActivityMarketBinding
import com.amin.cryptocoinforresume.feauters.ProfileActivity
import java.util.ArrayList
import kotlin.math.log

class MarketActivity : AppCompatActivity() , MarketAdapter.MarketEventINF {
    private lateinit var binding: ActivityMarketBinding
     var apiManager = ApiManager()
    lateinit var dataNews : ArrayList<Pair<String , String>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMarketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.swipeRefreshLayout.setOnRefreshListener {
            initUI()

            Handler(Looper.getMainLooper()).postDelayed({
                binding.swipeRefreshLayout.isRefreshing = false
            } , 1500)
        }
        binding.includeWatchlist.btnShowMore.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW , Uri.parse("https://www.coinex.com/markets?sort=circulation_usd&page=1"))
            startActivity(intent)
        }

        binding.includeToolbar.imgViewProfile.setOnClickListener {
            val intent = Intent(this , ProfileActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        initUI()
    }

    private fun initUI() {
        getNewsFromAPI()
        getTopCoinFromAPI()
    }

     /*1. request in apiService
     2. get data in apiManager
     3. send data in apiManager to targetActivity with interface
     4. create function in initUI()
     5. receive data and set their values in UI*/

    private fun getTopCoinFromAPI() {
        apiManager.getTopCoinsList(object : ApiManager.ApiCallback<List<CoinsData.Data>>{
            override fun onSuccess(data: List<CoinsData.Data>) {
                showDatInRecyclerView(data)
            }
            override fun onError(errorMessage: String) {
                Toast.makeText(this@MarketActivity, "Network Error", Toast.LENGTH_SHORT).show()
                Log.v("aaa" , errorMessage.toString())
            }
        })
    }
    private fun showDatInRecyclerView(data: List<CoinsData.Data>) {
        binding.includeWatchlist.recyclerWatchlist.adapter = MarketAdapter(ArrayList(data) , this)
        binding.includeWatchlist.recyclerWatchlist.layoutManager = LinearLayoutManager(this)
    }
    private fun getNewsFromAPI() {
        apiManager.getNews(object : ApiManager.ApiCallback<ArrayList<Pair<String, String>>>{
            override fun onSuccess(data: ArrayList<Pair<String, String>>) {
                dataNews = data
                refreshNews()
            }

            override fun onError(errorMessage: String) {

            }

        })
    }
    private fun refreshNews(){
        val randomNumber = (0..48).random()
        binding.includeNews.txtNews.text = dataNews[randomNumber].first
        binding.includeNews.txtNews.setOnClickListener {
            refreshNews()
        }
        binding.includeNews.imageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW , Uri.parse(dataNews[randomNumber].second))
            startActivity(intent)
        }
    }

    override fun onCoinItemClicked(coinData: CoinsData.Data) {
            val intent = Intent(this , CoinActivity::class.java)
            intent.putExtra("aaa" , coinData)
            startActivity(intent)
    }
}