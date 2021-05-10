package com.example.financemanager.adapter

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import com.example.financemanager.AddActivity
import com.example.financemanager.MainActivity
import com.example.financemanager.Shared
import com.example.financemanager.databinding.ItemTransactionBinding
import com.example.financemanager.model.Transaction
import java.text.SimpleDateFormat

class TransactionItem(val binding: ItemTransactionBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(transaction: Transaction){
        binding.apply{
            place.text = transaction.place
            category.text = transaction.category
            amount.text = transaction.amount.toString() + " pln"
            val sdf = SimpleDateFormat("dd-MM-yyyy")
            date.text = sdf.format(transaction.date).toString()
            if(transaction.amount>0f)
                card.setBackgroundColor(Color.GREEN)
            else if(transaction.amount==0f)
                card.setBackgroundColor(Color.WHITE)
            else
                card.setBackgroundColor(Color.RED)
            card.setOnLongClickListener{
                var alertDialogBuilder = AlertDialog.Builder(binding.root.context)
                alertDialogBuilder.setTitle("Jesteś pewny, że chcesz usunąć ten element?")
                alertDialogBuilder.setPositiveButton("Tak"){ dialog, which ->
                    Shared.transactionList.remove(transaction)
                    binding.root.context.startActivity((Intent(binding.root.context, MainActivity::class.java)))
                }
                alertDialogBuilder.setNegativeButton("Nie"){ dialog, which ->

                }
                alertDialogBuilder.show()

                true
            }
            card.setOnClickListener{
                var intent = Intent(binding.root.context, AddActivity::class.java)
                intent.putExtra("transaction",transaction)
                binding.root.context.startActivity(intent)
            }
        }
    }
}

class TransactionAdapter() : RecyclerView.Adapter<TransactionItem>() {
    var transactions: List<Transaction> = emptyList()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionItem {
        val binding = ItemTransactionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )
        return TransactionItem(binding)
    }

    override fun getItemCount(): Int = transactions.size

    override fun onBindViewHolder(holder: TransactionItem, position: Int) {
        holder.bind(transactions[position])
    }


}