package com.target.targetcasestudy.ui.dealDetails

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.target.targetcasestudy.R
import com.target.targetcasestudy.data.Product
import com.target.targetcasestudy.helpers.AppConstants
import com.target.targetcasestudy.ui.MainActivity
import kotlinx.android.synthetic.main.fragment_product_details.*
import java.math.BigDecimal


class DealDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).toggleBackButtonVisibility(View.VISIBLE)
        val product = arguments?.getParcelable<Product>(AppConstants.BUNDLE_PRODUCT)
        product?.let {
            setDataInUi(it)
        }
    }

    private fun setDataInUi(product: Product) {
        Glide.with(requireContext())
            .load(product.imageUrl)
            .into(iv_product)
        tv_product_price.text = product.regularPrice.displayString
        tv_product_description.text = product.description
        tv_product_title.text = product.title
        var discountedPrice = "0"
        kotlin.runCatching {
            discountedPrice = fetchDiscountedPrice(product?.regularPrice?.amountInCents)
        }
        tv_discounted_price.text = Html.fromHtml("Reg: <s>\$$discountedPrice </s>")
    }

    private fun fetchDiscountedPrice(amountInCents: Int): String {
        val payment: BigDecimal = BigDecimal(amountInCents - 100).movePointLeft(2)
        return payment.toString()
    }


}
