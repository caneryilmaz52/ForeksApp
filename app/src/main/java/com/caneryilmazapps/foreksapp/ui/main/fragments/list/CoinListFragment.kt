package com.caneryilmazapps.foreksapp.ui.main.fragments.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caneryilmazapps.foreksapp.R
import com.caneryilmazapps.foreksapp.adapters.CoinListAdapter
import com.caneryilmazapps.foreksapp.data.models.response.CoinResponse
import com.caneryilmazapps.foreksapp.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import io.paperdb.Paper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CoinListFragment : Fragment(R.layout.fragment_coin_list), CoinListContract.CoinListView {

    private lateinit var recyclerView: RecyclerView

    @Inject
    lateinit var coinListPresenter: CoinListPresenter

    @Inject
    lateinit var coinListAdapter: CoinListAdapter

    private var canSetRefreshData = true
    private var isForeground = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_coin_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        coinListPresenter.attachView(this)

        recyclerView = view.findViewById(R.id.fr_coin_list_recycler_view)

        setupRecyclerView()
        getCoinList()

        coinListAdapter.setOnItemClickListener { coinItem ->
            val bundle = Bundle().apply {
                putSerializable("coinCode", coinItem.cod)
                putSerializable("isFromFavoriteCoinsFragment", false)
            }

            findNavController().navigate(R.id.action_coinListFragment_to_coinDetailFragment, bundle)
        }
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

    private fun getCoinList() {
        (requireActivity() as MainActivity).showLoadingView()

        viewLifecycleOwner.lifecycleScope.launch {
            coinListPresenter.getCoinList()
        }
    }

    private fun setRefreshInterval() {
        val interval = Paper.book().read("updateInterval", 2)

        viewLifecycleOwner.lifecycleScope.launch {
            while (isForeground) {
                Log.i("RefreshJob","Request Send - Coin List Fragment")
                coinListPresenter.getCoinList()
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

    override fun onSuccess(coinResponse: CoinResponse) {
        (requireActivity() as MainActivity).hideLoadingView()
        coinListAdapter.differ.submitList(coinResponse.coins)

        if (canSetRefreshData) {
            setRefreshInterval()
            canSetRefreshData = false
        }
    }

    override fun onFail(message: String) {
        (requireActivity() as MainActivity).hideLoadingView()
        AlertDialog.Builder(requireContext())
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton("Tekrar Dene") { dialog, _ ->
                dialog.dismiss()
                getCoinList()
            }.show()
    }
}