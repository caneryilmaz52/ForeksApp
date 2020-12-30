package com.caneryilmazapps.foreksapp.ui.main.fragments.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.caneryilmazapps.foreksapp.R
import com.caneryilmazapps.foreksapp.data.models.response.CoinDetailResponse
import com.caneryilmazapps.foreksapp.ui.main.MainActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import io.paperdb.Paper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CoinDetailFragment : Fragment(R.layout.fragment_coin_detail),
    CoinDetailContract.CoinDetailsView {

    private lateinit var description: TextView
    private lateinit var code: TextView
    private lateinit var hour: TextView
    private lateinit var buy: TextView
    private lateinit var sell: TextView
    private lateinit var low: TextView
    private lateinit var high: TextView
    private lateinit var last: TextView
    private lateinit var difference: TextView
    private lateinit var differencePercent: TextView
    private lateinit var addFavorite: FloatingActionButton

    @Inject
    lateinit var coinDetailPresenter: CoinDetailPresenter

    private val args: CoinDetailFragmentArgs by navArgs()

    private var canSetRefreshData = true
    private var isForeground = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_coin_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        coinDetailPresenter.attachView(this)

        description = view.findViewById(R.id.fr_coin_detail_description)
        code = view.findViewById(R.id.fr_coin_detail_code)
        hour = view.findViewById(R.id.fr_coin_detail_hour)
        buy = view.findViewById(R.id.fr_coin_detail_buy)
        sell = view.findViewById(R.id.fr_coin_detail_sell)
        low = view.findViewById(R.id.fr_coin_detail_low)
        high = view.findViewById(R.id.fr_coin_detail_high)
        last = view.findViewById(R.id.fr_coin_detail_last)
        difference = view.findViewById(R.id.fr_coin_detail_difference)
        differencePercent = view.findViewById(R.id.fr_coin_detail_difference_percent)
        addFavorite = view.findViewById(R.id.fr_coin_detail_add_favorite)

        setupAddFavorite()
        getCoinDetail()
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

    private fun getCoinDetail() {
        (requireActivity() as MainActivity).showLoadingView()
        viewLifecycleOwner.lifecycleScope.launch {
            coinDetailPresenter.getCoinDetail(args.coinCode)
        }
    }

    private fun setRefreshInterval() {
        val interval = Paper.book().read("updateInterval", 2)

        viewLifecycleOwner.lifecycleScope.launch {
            while (true) {
                Log.i("RefreshJob", "Request Send - Coin Detail Fragment")
                coinDetailPresenter.getCoinDetail(args.coinCode)
                // 1 second = 1000 millisecond
                val delay = (interval * 1000).toLong()
                delay(delay)
            }
        }
    }

    private fun setupAddFavorite() {
        addFavorite.setOnClickListener {
            if (args.isFromFavoriteCoinsFragment)
                deleteFromFavoriteCoins()
            else
                addToFavoriteCoins()
        }
    }

    private fun addToFavoriteCoins() {
        (requireActivity() as MainActivity).showLoadingView()

        viewLifecycleOwner.lifecycleScope.launch {
            coinDetailPresenter.saveFavoriteCoin(args.coinCode)
        }
    }

    private fun deleteFromFavoriteCoins() {
        (requireActivity() as MainActivity).showLoadingView()

        viewLifecycleOwner.lifecycleScope.launch {
            coinDetailPresenter.deleteFavoriteCoin(args.coinCode)
        }
    }


    private fun setCoinDetail(coinDetailResponse: CoinDetailResponse) {
        description.text = coinDetailResponse.def
        code.text = coinDetailResponse.cod

        hour.text = coinDetailResponse.coinDetail[0].clo

        buy.text = coinDetailResponse.coinDetail[0].fields.buy
        sell.text = coinDetailResponse.coinDetail[0].fields.sel
        low.text = coinDetailResponse.coinDetail[0].fields.low
        high.text = coinDetailResponse.coinDetail[0].fields.hig
        last.text = coinDetailResponse.coinDetail[0].fields.las
        difference.text = coinDetailResponse.coinDetail[0].fields.ddi
        differencePercent.text = coinDetailResponse.coinDetail[0].fields.pdd

        if (canSetRefreshData) {
            setRefreshInterval()
            canSetRefreshData = false
        }
    }

    override fun onSuccess(coinDetailResponse: CoinDetailResponse) {
        (requireActivity() as MainActivity).hideLoadingView()
        setCoinDetail(coinDetailResponse)
    }

    override fun onSuccessAddFavorite() {
        (requireActivity() as MainActivity).hideLoadingView()
        Toast.makeText(requireContext(), "Favorilere eklendi", Toast.LENGTH_SHORT).show()
    }

    override fun onSuccessDeleteFavorite() {
        (requireActivity() as MainActivity).hideLoadingView()
        Toast.makeText(requireContext(), "Favorilerden çıkarıldı", Toast.LENGTH_SHORT).show()
    }

    override fun onFail(message: String) {
        (requireActivity() as MainActivity).hideLoadingView()
        AlertDialog.Builder(requireContext())
            .setMessage(message)
            .setPositiveButton("Tekrar Dene") { dialog, _ ->
                dialog.dismiss()
                getCoinDetail()
            }.show()
    }

    override fun onFailAddFavorite(message: String) {
        (requireActivity() as MainActivity).hideLoadingView()
        AlertDialog.Builder(requireContext())
            .setMessage(message)
            .setPositiveButton("Tekrar Dene") { dialog, _ ->
                dialog.dismiss()
                addToFavoriteCoins()
            }.show()
    }

    override fun onFailDeleteFavorite(message: String) {
        (requireActivity() as MainActivity).hideLoadingView()
        AlertDialog.Builder(requireContext())
            .setMessage(message)
            .setPositiveButton("Tekrar Dene") { dialog, _ ->
                dialog.dismiss()
                deleteFromFavoriteCoins()
            }.show()
    }
}