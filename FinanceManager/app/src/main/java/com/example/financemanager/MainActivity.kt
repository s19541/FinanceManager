package com.example.financemanager

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.financemanager.adapter.TransactionAdapter
import com.example.financemanager.databinding.ActivityMainBinding
import com.example.financemanager.databinding.ItemTransactionBinding
import com.example.financemanager.model.Transaction
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    companion object {
        var selectedMonth = Calendar.getInstance().time
    }
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}
    val transactionAdapter by lazy { TransactionAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupTransactionList()
        if(Shared.transactionList.isEmpty())
        addExampleTransactions()
        setContentView(binding.root)
    }
    private fun setupTransactionList(){
        binding.transactionlist.apply {
            adapter = transactionAdapter
            layoutManager = LinearLayoutManager(context)
        }
        transactionAdapter.transactions = Shared.transactionList.sortedBy { it.date }
    }
    private fun addExampleTransactions(){
        Shared.transactionList.add(Transaction("Biedronka", "art. spożywcze", -220.53f, Date(GregorianCalendar(2021, Calendar.MAY, 4).timeInMillis)))
        Shared.transactionList.add(Transaction("Praca", "usługi", 5000f, Date(GregorianCalendar(2021, Calendar.MAY, 1).timeInMillis)))
        Shared.transactionList.add(Transaction("Fryzjer", "usługi", -55f, Date(GregorianCalendar(2021, Calendar.MAY, 20).timeInMillis)))
        Shared.transactionList.add(Transaction("Kino", "rozrywka", -120f, Date(GregorianCalendar(2021, Calendar.MAY, 15).timeInMillis)))
        Shared.transactionList.add(Transaction("Media Markt", "elektronika", -1550f, Date(GregorianCalendar(2021, Calendar.MAY, 24).timeInMillis)))
        Shared.transactionList.add(Transaction("Biedronka", "art. spożywcze", -225.99f, Date(GregorianCalendar(2021, Calendar.APRIL, 25).timeInMillis)))
        Shared.transactionList.add(Transaction("Apteka", "zdrowie", -500f, Date(GregorianCalendar(2021, Calendar.APRIL, 21).timeInMillis)))
        Shared.transactionList.add(Transaction("Restauracja", "jedzenie", -200f, Date(GregorianCalendar(2021, Calendar.APRIL, 21).timeInMillis)))
        Shared.transactionList.add(Transaction("Praca", "usługi", 1000f, Date(GregorianCalendar(2021, Calendar.APRIL, 20).timeInMillis)))
        Shared.transactionList.add(Transaction("Teatr", "rozrywka", -200f, Date(GregorianCalendar(2021, Calendar.APRIL, 7).timeInMillis)))
        Shared.transactionList.add(Transaction("McDonald", "jedzenie", -15.50f, Date(GregorianCalendar(2021, Calendar.MARCH, 21).timeInMillis)))
        Shared.transactionList.add(Transaction("Pizza hut", "jedzenie", -45.50f, Date(GregorianCalendar(2021, Calendar.MARCH, 20).timeInMillis)))
        Shared.transactionList.add(Transaction("Dentysta", "zdrowie", -300f, Date(GregorianCalendar(2021, Calendar.MARCH, 11).timeInMillis)))
        Shared.transactionList.add(Transaction("McDonald", "jedzenie", -18.50f, Date(GregorianCalendar(2021, Calendar.FEBRUARY, 21).timeInMillis)))
        Shared.transactionList.add(Transaction("Praca", "usługi", 4000f, Date(GregorianCalendar(2021, Calendar.FEBRUARY, 20).timeInMillis)))
    }

    override fun onResume() {
        super.onResume()
        onChangeMonth()
    }
    fun onChangeMonth(){
        val sdf = SimpleDateFormat("MMM yyyy")
        transactionAdapter.transactions = Shared.transactionList.sortedBy{ it.date }.filter{sdf.format(it.date).equals(sdf.format(selectedMonth))}
        binding.bilans.text = "Bilans: " + (Math.round(transactionAdapter.transactions.map { it.amount }.sum()*100.0)/100.0).toString()
        binding.month.text = sdf.format(selectedMonth).toString()
    }
    fun nextMonthClicked(view: View){
        val cal = Calendar.getInstance()
        cal.time = selectedMonth
        cal.add(Calendar.MONTH, 1)
        selectedMonth = cal.time

        onChangeMonth()
    }
    fun previousMonthClicked(view: View){
        val cal = Calendar.getInstance()
        cal.time = selectedMonth
        cal.add(Calendar.MONTH, -1)
        selectedMonth = cal.time

        onChangeMonth()
    }
    fun addButtonClicked(view: View){
        startActivity(Intent(this, AddActivity::class.java))
    }
    fun graphButtonClicked(view: View){
        startActivity(Intent(this, GraphActivity::class.java))
    }
}