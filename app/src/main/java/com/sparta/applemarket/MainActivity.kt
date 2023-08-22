package com.sparta.applemarket

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.sparta.applemarket.adapter.ProductAdapter
import com.sparta.applemarket.adapter.listener.ItemClickListener
import com.sparta.applemarket.data.ProductsData
import com.sparta.applemarket.databinding.ActivityMainBinding
import com.sparta.applemarket.notification.ProductNotification

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ProductAdapter
    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            showDialog()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        initButton()
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            this.onBackPressedDispatcher.addCallback(callback)
    }
    private fun initButton() {
        binding.notificationButton.setOnClickListener {
            Toast.makeText(this, "asd", Toast.LENGTH_SHORT).show()
            ProductNotification(this@MainActivity).runNotification()
        }
    }

    private fun initRecyclerView() = with(binding) {
        val list = ProductsData(this@MainActivity).getList()
        adapter = ProductAdapter(this@MainActivity).apply {
            addItems(list)
        }
        mainRecyclerView.adapter = adapter
        mainRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        setClickListener()
    }
    private fun setClickListener() {
        adapter.setOnItemClickListener(object: ItemClickListener {
            // 상세정보 페이지로
            override fun onItemClick(position: Int) {
                val list = adapter.getItems()
                val intent = Intent(this@MainActivity, DetailProductActivity::class.java).apply {
                    putExtra("product", list[position])
                }
                startActivity(intent)
            }

            // TODO 선택과제 2
            override fun onLongClick(position: Int) {

            }

        })
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setIcon(R.drawable.dialog_done)
            .setTitle(getString(R.string.close))
            .setMessage(getString(R.string.really_close))
            .setCancelable(false)
            .setPositiveButton(getString(R.string.yes)){ _, _ ->
                finish()
            }.setNegativeButton(getString(R.string.no), null)
            .create()
            .show()
    }
    override fun onBackPressed() {
        showDialog()
    }


}