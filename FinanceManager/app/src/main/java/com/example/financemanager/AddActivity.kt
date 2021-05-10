package com.example.financemanager

import android.os.Bundle
import android.view.View
import android.widget.CalendarView.OnDateChangeListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.financemanager.databinding.ActivityAddBinding
import com.example.financemanager.model.Transaction
import java.util.*


class AddActivity : AppCompatActivity() {
    private var transaction: Transaction?=null
    private val binding by lazy {ActivityAddBinding.inflate((layoutInflater))}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if(intent.extras !=null)
            transaction = intent.extras?.get("transaction") as Transaction
        if(transaction != null){
            val transaction = transaction
            binding.place.setText(transaction?.place)
            binding.category.setText(transaction?.category)
            binding.amount.setText(transaction?.amount.toString())
            val calendar = Calendar.getInstance()
            calendar.time = transaction?.date
            binding.calendarView.date = calendar.timeInMillis
        }
        binding.calendarView.setOnDateChangeListener(OnDateChangeListener { view, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year,month,dayOfMonth)
            binding.calendarView.date = calendar.timeInMillis
        })
    }
    fun confirmButtonClicked(view: View){
        val place = binding.place.text.toString()
        val category = binding.category.text.toString()
        val amount = binding.amount.text.toString().toFloatOrNull()
        val date = Date(binding.calendarView.date)
        if(place.isEmpty() || category.isEmpty() || amount == null){
            Toast.makeText(this, "Nie wypełniono wszyskich pól", Toast.LENGTH_LONG).show()
            return
        }
        if(transaction != null)
            Shared.transactionList.remove(transaction!!)
        val transaction = Transaction(place, category, amount, date)
        Shared.transactionList.add(transaction)
        finish()

    }
}