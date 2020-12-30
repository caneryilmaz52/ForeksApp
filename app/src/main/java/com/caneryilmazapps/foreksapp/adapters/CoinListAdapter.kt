package com.caneryilmazapps.foreksapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.caneryilmazapps.foreksapp.R
import com.caneryilmazapps.foreksapp.data.models.response.Coin
import io.paperdb.Paper
import kotlinx.android.synthetic.main.coin_list_item.view.*

class CoinListAdapter : RecyclerView.Adapter<CoinListAdapter.CoinListViewHolder>() {

    private var lastPosition: Int = -1

    class CoinListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val code = itemView.coin_item_code
        val name = itemView.coin_item_name
        val update = itemView.coin_item_update
        val id = itemView.coin_item_id
        val last = itemView.coin_item_last
        val difference = itemView.coin_item_difference
        val differencePercent = itemView.coin_item_difference_percent
        val high = itemView.coin_item_high
        val low = itemView.coin_item_low
        val buy = itemView.coin_item_buy
        val sell = itemView.coin_item_sell
        val pdc = itemView.coin_item_pdc


        init {

            val idStatus = Paper.book().read("id", false)
            if (idStatus)
                id.visibility = View.VISIBLE
            else
                id.visibility = View.GONE

            val lastStatus = Paper.book().read("last", false)
            if (lastStatus)
                last.visibility = View.VISIBLE
            else
                last.visibility = View.GONE

            val differenceStatus = Paper.book().read("difference", false)
            if (differenceStatus)
                difference.visibility = View.VISIBLE
            else
                difference.visibility = View.GONE

            val differencePercentStatus = Paper.book().read("differencePercent", false)
            if (differencePercentStatus)
                differencePercent.visibility = View.VISIBLE
            else
                differencePercent.visibility = View.GONE

            val highStatus = Paper.book().read("high", false)
            if (highStatus)
                high.visibility = View.VISIBLE
            else
                high.visibility = View.GONE

            val lowStatus = Paper.book().read("low", false)
            if (lowStatus)
                low.visibility = View.VISIBLE
            else
                low.visibility = View.GONE

            val buyStatus = Paper.book().read("buy", false)
            if (buyStatus)
                buy.visibility = View.VISIBLE
            else
                buy.visibility = View.GONE

            val sellStatus = Paper.book().read("sell", false)
            if (sellStatus)
                sell.visibility = View.VISIBLE
            else
                sell.visibility = View.GONE

            val pdcStatus = Paper.book().read("pdc", false)
            if (pdcStatus)
                pdc.visibility = View.VISIBLE
            else
                pdc.visibility = View.GONE
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Coin>() {
        override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return oldItem.cod == newItem.cod
        }

        override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CoinListViewHolder {
        return CoinListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.coin_list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CoinListViewHolder, position: Int) {
        val coinItem = differ.currentList[position]

        holder.code.text = coinItem.cod
        holder.name.text = coinItem.def
        holder.update.text = coinItem.clo
        holder.buy.text = coinItem.buy
        holder.sell.text = coinItem.sel
        holder.high.text = coinItem.hig
        holder.low.text = coinItem.low
        holder.difference.text = coinItem.ddi
        holder.differencePercent.text = coinItem.pdd
        holder.last.text = coinItem.las
        holder.id.text = coinItem.tke
        holder.pdc.text = coinItem.pdc

        val animation: Animation = AnimationUtils.loadAnimation(
            holder.itemView.context,
            if (position > lastPosition) R.anim.up_from_bottom else R.anim.down_from_top
        )

        holder.itemView.startAnimation(animation)
        lastPosition = position

        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(coinItem)
        }
    }

    private var onItemClickListener: ((Coin) -> Unit)? = null

    fun setOnItemClickListener(listener: (Coin) -> Unit) {
        onItemClickListener = listener
    }
}