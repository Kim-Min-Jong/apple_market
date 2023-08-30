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


class ProductAdapter(private val context: Context) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    private val _items = arrayListOf<Product>()
    val items: List<Product>
        get() = _items
    private lateinit var itemClickListener: ItemClickListener

    fun setOnItemClickListener(listener: ItemClickListener) {
        itemClickListener = listener
    }

    fun addItems(list: List<Product>) {
        _items.addAll(list)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        _items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun updateItem(id: Int, isChecked: Boolean) {
        _items.find { it.id == id }?.run {
            isLiked = isChecked
            if(isChecked)
                liked++
            else
                liked--
        }
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemSaleProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = _items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(_items[position])

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
                if(model.isLiked) {
                    iconFavoriteActivated.setImageResource(R.drawable.icon_favorite_activated)
                } else {
                    iconFavoriteActivated.setImageResource(R.drawable.icon_favorite)
                }
                itemLiked.text = "${model.liked}"
            }
        }
    }
}