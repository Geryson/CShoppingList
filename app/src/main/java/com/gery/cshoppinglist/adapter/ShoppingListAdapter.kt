package com.gery.cshoppinglist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.gery.cshoppinglist.MainActivity
import com.gery.cshoppinglist.R
import com.gery.cshoppinglist.ShoppingItemDialog
import com.gery.cshoppinglist.data.ShoppingItem
import com.gery.cshoppinglist.databinding.ShoppingListItemLayoutBinding

class ShoppingListAdapter(private val context: Context) :
    RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>() {

    private val items = mutableListOf(
        ShoppingItem("Milk", "This is milk", 2, 1, true),
        ShoppingItem("Smartphone", "This is smartphone", 220, 2, false),
        ShoppingItem("Shirt", "This is shirt", 20, 0, false)
    )

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

        holder.shopItemBinding.btnItemEdit.setOnClickListener {
            editItem(holder.adapterPosition)
        }

        holder.shopItemBinding.cbItemStatus.setOnClickListener {
            items[holder.adapterPosition].status = holder.shopItemBinding.cbItemStatus.isChecked
            notifyItemChanged(holder.adapterPosition)
        }

        if (position % 2 == 0) {
            holder.shopItemBinding.clItemContainer.setBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, R.color.list_item_background_even))
        } else {
            holder.shopItemBinding.clItemContainer.setBackgroundColor(
                ContextCompat.getColor(context, R.color.list_item_background_odd))
        }
    }

    private fun editItem(adapterPosition: Int) {
        val item = items[adapterPosition]

        ShoppingItemDialog(item, adapterPosition).show((context as MainActivity).supportFragmentManager, "ShoppingItemDialog_EDIT")
    }

    private fun deleteItem(adapterPosition: Int) {
        items.removeAt(adapterPosition)
        notifyItemRemoved(adapterPosition)
        notifyItemRangeChanged(adapterPosition, items.size)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItem(item: ShoppingItem) {
        items.add(item)
        notifyItemInserted(items.lastIndex)
    }

    fun editItem(item: ShoppingItem, position: Int) {
        items[position] = item
        notifyItemChanged(position)
    }

    fun deleteAll() {
        val size = items.size
        items.clear()
        notifyItemRangeRemoved(0, size)
    }

    inner class ViewHolder(val shopItemBinding: ShoppingListItemLayoutBinding): RecyclerView.ViewHolder(shopItemBinding.root) {
        fun bind(shopItem: ShoppingItem) {
            val formattedName = shopItem.name + ","
            shopItemBinding.tvItemName.text = formattedName
            shopItemBinding.tvItemPrice.text = shopItem.price.toString()
            shopItemBinding.tvItemDescription.text = shopItem.description
            shopItemBinding.cbItemStatus.isChecked = shopItem.status

            when (shopItem.category) {
                0 -> {
                    shopItemBinding.ivItemCategoryIcon.setImageResource(
                        R.drawable.apparel_24dp)
                }
                1 -> {
                    shopItemBinding.ivItemCategoryIcon.setImageResource(
                        R.drawable.food_24dp)
                }
                2 -> {
                    shopItemBinding.ivItemCategoryIcon.setImageResource(
                        R.drawable.electronics_24dp)
                }
            }
        }
    }

}