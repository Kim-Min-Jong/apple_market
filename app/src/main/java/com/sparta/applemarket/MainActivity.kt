package com.sparta.applemarket

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sparta.applemarket.adapter.ProductAdapter
import com.sparta.applemarket.adapter.listener.ItemClickListener
import com.sparta.applemarket.data.ProductsData
import com.sparta.applemarket.databinding.ActivityMainBinding
import com.sparta.applemarket.notification.ProductNotification
import com.sparta.applemarket.util.ContextUtil.toast
import com.sparta.applemarket.util.DialogUtil
import com.sparta.applemarket.util.RecyclerViewDecoration

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var productAdapter: ProductAdapter
    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            showCloseDialog()
        }
    }
    private val fadeInAnimation by lazy {
        AnimationUtils.loadAnimation(this@MainActivity, R.anim.fade_in)
    }
    private val fadeOutAnimation by lazy {
        AnimationUtils.loadAnimation(this@MainActivity, R.anim.fade_out)
    }

    private val scrollListener by lazy {
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val view = binding.fabUp
                if (binding.mainRecyclerView.canScrollVertically(-1)) {
                    view.startAnimation(fadeInAnimation)
                    delayVisibility(view, true)
                } else {
                    view.startAnimation(fadeOutAnimation)
                    delayVisibility(view, false)
                }
            }
        }
    }

    private val activityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val id = it.data?.getIntExtra("id", -1) ?: -1
                val size = it.data?.getIntExtra("count", 0) ?: 0
                val isLiked = it.data?.getBooleanExtra("liked", false) ?: false
                if(size % 2 != 0){
                    productAdapter.updateItem(id, isLiked)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        initButton()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            this.onBackPressedDispatcher.addCallback(callback)
    }

    private fun initButton() = with(binding) {
        notificationButton.setOnClickListener {
            ProductNotification(this@MainActivity).runNotification()
        }
        fabUp.setOnClickListener {
            mainRecyclerView.smoothScrollToPosition(0)
        }
    }

    private fun initRecyclerView() = with(binding) {
        val list = ProductsData(this@MainActivity).getList()
        productAdapter = ProductAdapter(this@MainActivity).apply {
            addItems(list)
        }
        mainRecyclerView.run {
            adapter = productAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            addOnScrollListener(scrollListener)
            addItemDecoration(RecyclerViewDecoration())
        }
        setClickListener()
    }

    private fun setClickListener() {
        productAdapter.setOnItemClickListener(object : ItemClickListener {
            val list = productAdapter.getItems()
            // 상세정보 페이지로
            override fun onItemClick(position: Int) {
                val intent = Intent(this@MainActivity, DetailProductActivity::class.java).apply {
                    putExtra("product", list[position])
                }
                activityLauncher.launch(intent)
            }

            override fun onLongClick(position: Int) {
                showRemoveItemDialog(position)
            }
        })
    }

    private fun showRemoveItemDialog(position: Int) = DialogUtil.showDialog(
        this@MainActivity,
        R.drawable.icon_delete,
        R.string.remove_product,
        R.string.really_remove_product,
        R.string.yes,
        R.string.no,
        { productAdapter.removeItem(position) },
        { toast(getString(R.string.cancel_remove)) }
    )

    private fun showCloseDialog() = DialogUtil.showDialog(
        this@MainActivity,
        R.drawable.dialog_done,
        R.string.close,
        R.string.really_close,
        R.string.yes,
        R.string.no,
        { finish() },
        {})

    override fun onBackPressed() =
        showCloseDialog()

    private fun delayVisibility(view: View, isVisible: Boolean) =
        Handler(Looper.getMainLooper()).postDelayed({
            view.isVisible = isVisible
        }, 500)
}