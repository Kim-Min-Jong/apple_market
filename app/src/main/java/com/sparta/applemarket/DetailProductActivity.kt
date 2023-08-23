package com.sparta.applemarket

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sparta.applemarket.databinding.ActivityDetailProductBinding
import com.sparta.applemarket.model.Product
import com.sparta.applemarket.util.Format

class DetailProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val product = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra("product", Product::class.java)
        } else {
            intent?.getParcelableExtra("product")
        } ?: Product(-1,-1,"오류", "오류","오류",-1,"오류",-1,-1)
        initViews(product)
        initButton()
    }

    private fun initViews(product: Product) = with(binding) {
        productImageView.setImageResource(product.image)
        cardImageView.clipToOutline = true
        cardViewName.text = product.seller
        cardViewAddress.text = product.address
        productTitle.text = product.productName
        productDescription.text = product.description
        "${Format.thousandsByComma(product.price)}${getString(R.string.won)}".also {
            productPrice.text = it
        }
    }
    private fun initButton() = with(binding){
        backButton.setOnClickListener {
            finish()
        }
    }

}