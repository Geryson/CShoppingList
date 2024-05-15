package com.gery.cshoppinglist.data

data class ShoppingItem(
    var name: String,
    var description: String,
    var price: Int,
    var category: ShoppingItemCategory,
    var status: Boolean
)