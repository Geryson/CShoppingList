package com.gery.cshoppinglist

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.gery.cshoppinglist.data.ShoppingItem
import com.gery.cshoppinglist.databinding.ShoppingItemDialogBinding

class ShoppingItemDialog : DialogFragment() {

    interface ShoppingItemDialogHandler {
        fun shoppingItemCreated(item: ShoppingItem)
        fun shoppingItemModified(item: ShoppingItem)
    }

    private lateinit var shoppingItemHandler: ShoppingItemDialogHandler

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is ShoppingItemDialogHandler) {
            shoppingItemHandler = context
        } else {
            throw RuntimeException("The Activity does not implement the ShoppingItemDialogHandler interface")
        }
    }

    private lateinit var shoppingDialogBinding: ShoppingItemDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("New Item")

        shoppingDialogBinding = ShoppingItemDialogBinding.inflate(
            requireActivity().layoutInflater
        )

        var categoryAdapter = ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.category,
            android.R.layout.simple_spinner_item
        )
        categoryAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        shoppingDialogBinding.spItemDialogCategoryPicker.adapter = categoryAdapter

        builder.setView(shoppingDialogBinding.root)

        builder.setPositiveButton("OK") { dialog, which ->
            //... keep empty
        }
        return builder.create()
    }

    override fun onResume() {
        super.onResume()

        val dialog = dialog as AlertDialog
        val positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE)

        positiveButton.setOnClickListener {
            if (shoppingDialogBinding.etItemDialogName.text!!.isNotEmpty()) {
                if (shoppingDialogBinding.etItemDialogPrice.text!!.isNotEmpty()) {
                    handleItemCreate()

                    dialog.dismiss()
                } else {
                    shoppingDialogBinding.etItemDialogPrice.error = "This field can not be empty"
                }
            } else {
                shoppingDialogBinding.etItemDialogName.error = "This field can not be empty"
            }
        }
    }

    fun handleItemCreate() {
        shoppingItemHandler.shoppingItemCreated(
            ShoppingItem(
                shoppingDialogBinding.etItemDialogName.text.toString(),
                shoppingDialogBinding.etItemDialogDescription.text.toString(),
                shoppingDialogBinding.etItemDialogPrice.text.toString().toInt(),
                shoppingDialogBinding.spItemDialogCategoryPicker.selectedItemPosition,
                false
            )
        )

    }
}