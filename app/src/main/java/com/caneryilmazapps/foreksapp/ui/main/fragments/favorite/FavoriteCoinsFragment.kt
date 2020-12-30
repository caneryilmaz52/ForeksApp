package com.caneryilmazapps.foreksapp.ui.main.fragments.favorite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caneryilmazapps.foreksapp.R
import com.caneryilmazapps.foreksapp.adapters.CoinListAdapter
import com.caneryilmazapps.foreksapp.data.models.local.FavoriteCoinEntity
import com.caneryilmazapps.foreksapp.data.models.response.Coin
import com.caneryilmazapps.foreksapp.data.models.response.CoinResponse
import com.caneryilmazapps.foreksapp.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import io.paperdb.Paper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteCoinsFragment : Fragment(R.layout.fragment_favorite_coins),
    FavoriteCoinsContract.FavoriteCoinsView {

    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyText: TextView
    private lateinit var favoriteCoinEntity: List<FavoriteCoinEntity>

    @Inject
    lateinit var favoriteCoinsPresenter: FavoriteCoinsPresenter

    @Inject
    lateinit var coinListAdapter: CoinListAdapter

    private var canSetRefreshData = true
    private var isForeground = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite_coins, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteCoinsPresenter.attachView(this)

        recyclerView = view.findViewById(R.id.fr_favorite_coins_recycler_view)
        emptyText = view.findViewById(R.id.fr_favorite_coins_tv_empty)

        setupRecyclerView()

        coinListAdapter.setOnItemClickListener { coinItem ->
            val bundle = Bundle().apply {
                putSerializable("coinCode", coinItem.cod)
                putSerializable("isFromFavoriteCoinsFragment", true)
            }

            findNavController().navigate(
                R.id.action_favoriteCoinsFragment_to_coinDetailFragment,
                bundle
            )
        }

        getFavoriteCoins()
    }

    override fun onResume() {
        super.onResume()
        isForeground = true
        setRefreshInterval()
    }

    override fun onPause() {
        super.onPause()
        isForeground = false
        setRefreshInterval()
    }

    private fun getFavoriteCoins() {
        (requireActivity() as MainActivity).showLoadingView()
        viewLifecycleOwner.lifecycleScope.launch {
            favoriteCoinsPresenter.getFavoriteCoins()
        }
    }

    private fun getCoinList() {
        (requireActivity() as MainActivity).showLoadingView()
        viewLifecycleOwner.lifecycleScope.launch {
            favoriteCoinsPresenter.getCoinList()
        }
    }

    private fun setRefreshInterval() {
        val interval = Paper.book().read("updateInterval", 2)

        viewLifecycleOwner.lifecycleScope.launch {
            while (isForeground) {
                Log.i("RefreshJob","Request Send - Favorite Coins Fragment")
                favoriteCoinsPresenter.getCoinList()
                // 1 second = 1000 millisecond
                val delay = (interval * 1000).toLong()
                delay(delay)
            }
        }
    }

    private fun setupRecyclerView() {
        recyclerView.apply {
            adapter = coinListAdapter
            layoutManager =
                GridLayoutManager(requireContext(), 2)
        }
    }

    override fun onSuccessGetFavoriteCoins(favoriteCoinEntity: List<FavoriteCoinEntity>) {
        this@FavoriteCoinsFragment.favoriteCoinEntity = favoriteCoinEntity
        getCoinList()
    }

    override fun onSuccessGetCoinList(coinResponse: CoinResponse) {
        (requireActivity() as MainActivity).hideLoadingView()

        val coins = ArrayList<Coin>()
        for (favoriteCoin in favoriteCoinEntity) {
            for (coin in coinResponse.coins) {
                if (favoriteCoin.coinCode == coin.cod) {
                    coins.add(coin)
                }
            }
        }

        emptyText.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        coinListAdapter.differ.submitList(coins)

        if (canSetRefreshData) {
            setRefreshInterval()
            canSetRefreshData = false
        }
    }

    override fun onFailGetFavoriteCoins(message: String) {
        (requireActivity() as MainActivity).hideLoadingView()
        emptyText.text = message
        emptyText.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

    override fun onFailGetCoinList(message: String) {
        (requireActivity() as MainActivity).hideLoadingView()
        AlertDialog.Builder(requireContext())
            .setMessage(message)
            .setPositiveButton("Tekrar Dene") { dialog, _ ->
                dialog.dismiss()
                getCoinList()
            }.show()
    }
}