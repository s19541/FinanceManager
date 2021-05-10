package com.example.financemanager

import android.R
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.financemanager.databinding.ActivityGraphBinding
import java.text.SimpleDateFormat


class GraphActivity : AppCompatActivity() {
    val binding by lazy { ActivityGraphBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val sdf = SimpleDateFormat("MMM yyyy")
        binding.month.text = sdf.format(MainActivity.selectedMonth).toString()
    }
}