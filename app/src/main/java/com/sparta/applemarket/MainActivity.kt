package com.sparta.applemarket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sparta.applemarket.adapter.ProductAdapter
import com.sparta.applemarket.data.ProductsData
import com.sparta.applemarket.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
    }

    private fun initRecyclerView() = with(binding) {
        val list = ProductsData(this@MainActivity).getList()
        mainRecyclerView.adapter = ProductAdapter(this@MainActivity).apply {
            addItems(list)
        }
        mainRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
    }
}