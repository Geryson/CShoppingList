package com.gery.cshoppinglist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gery.cshoppinglist.R
import com.gery.cshoppinglist.data.ShoppingItem
import com.gery.cshoppinglist.data.ShoppingItemCategory
import com.gery.cshoppinglist.databinding.ShoppingListItemLayoutBinding

class ShoppingListAdapter : RecyclerView.Adapter<ShoppingListAdapter.ViewHolder> {

    private val items = mutableListOf(
        ShoppingItem("Milk", "This is milk", 2, ShoppingItemCategory.FOOD, false),
        ShoppingItem("Smartphone", "This is smartphone", 220, ShoppingItemCategory.ELECTRONICS, false),
        ShoppingItem("Shirt", "This is shirt", 20, ShoppingItemCategory.CLOTHES, false)
    )
    private val context: Context

    constructor(context: Context) : super() {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val shopItemBinding = ShoppingListItemLayoutBinding.inflate(
            LayoutInflater.from(context), parent, false)
        return ViewHolder(shopItemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[holder.adapterPosition])

        holder.shopItemBinding.btnItemDelete.setOnClickListener {
            deleteItem(holder.adapterPosition)
        }
    }

    private fun deleteItem(adapterPosition: Int) {
        items.removeAt(adapterPosition)
        notifyItemRemoved(adapterPosition)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItem(item: ShoppingItem) {
        items.add(item)
        notifyItemInserted(items.lastIndex)
        //notifyDataSetChanged()
    }

    fun deleteAll() {
        items.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(val shopItemBinding: ShoppingListItemLayoutBinding): RecyclerView.ViewHolder(shopItemBinding.root) {
        fun bind(shopItem: ShoppingItem) {
            shopItemBinding.tvItemName.text = shopItem.name
            shopItemBinding.tvItemPrice.text = shopItem.price.toString()
            shopItemBinding.tvItemDescription.text = shopItem.description
            shopItemBinding.cbItemStatus.isChecked = shopItem.status

            when (shopItem.category) {
                ShoppingItemCategory.CLOTHES -> {
                    shopItemBinding.ivItemCategoryIcon.setImageResource(
                        R.drawable.apparel_24dp)
                }
                ShoppingItemCategory.FOOD -> {
                    shopItemBinding.ivItemCategoryIcon.setImageResource(
                        R.drawable.food_24dp)
                }
                ShoppingItemCategory.ELECTRONICS -> {
                    shopItemBinding.ivItemCategoryIcon.setImageResource(
                        R.drawable.electronics_24dp)
                }
            }
        }
    }

}