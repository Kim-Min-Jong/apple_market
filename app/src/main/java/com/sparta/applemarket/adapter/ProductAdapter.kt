package com.sparta.applemarket.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sparta.applemarket.R
import com.sparta.applemarket.adapter.listener.ItemClickListener
import com.sparta.applemarket.databinding.ItemSaleProductBinding
import com.sparta.applemarket.model.Product
import com.sparta.applemarket.util.Format
import java.text.DecimalFormat

class ProductAdapter(private val context: Context) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    private val datas = arrayListOf<Product>()
    private lateinit var itemClickListener: ItemClickListener

    fun setOnItemClickListener(listener: ItemClickListener) {
        itemClickListener = listener
    }

    fun addItems(items: List<Product>) {
        datas.addAll(items)
        notifyDataSetChanged()
    }
    fun getItems() = datas

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemSaleProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(datas[position])

    inner class ViewHolder(
        private val binding: ItemSaleProductBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                itemClickListener.onItemClick(adapterPosition)
            }
            binding.root.setOnLongClickListener{
                itemClickListener.onLongClick(adapterPosition)
                return@setOnLongClickListener true
            }
        }
        fun bind(model: Product) {
            with(binding) {
                mainImageView.run {
                    setImageResource(model.image)
                    clipToOutline = true
                }
                itemTitle.text = model.productName
                itemAddress.text = model.address
                "${Format.thousandsByComma(model.price)}${context.getString(R.string.won)}".also {
                    itemPrice.text = it
                }
                itemChat.text = "${model.chatting}"
                itemLiked.text = "${model.liked}"
            }
        }


    }
}