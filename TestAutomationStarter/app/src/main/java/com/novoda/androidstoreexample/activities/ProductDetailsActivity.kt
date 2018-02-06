package com.novoda.androidstoreexample.activities

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.novoda.androidstoreexample.R
import com.novoda.androidstoreexample.dagger.component.AppComponent
import com.novoda.androidstoreexample.dagger.productDetails.ProductDetailsModule
import com.novoda.androidstoreexample.models.Product
import com.novoda.androidstoreexample.mvp.presenter.ProductDetailPresenter
import com.novoda.androidstoreexample.mvp.view.ProductDetailView
import com.novoda.androidstoreexample.utilities.ImageHelper
import com.novoda.androidstoreexample.utilities.PRODUCT_ID_EXTRA
import kotlinx.android.synthetic.main.activity_product_details.*
import javax.inject.Inject

class ProductDetailsActivity : BaseActivity(), ProductDetailView {

    @Inject
    lateinit var presenter: ProductDetailPresenter

    lateinit var product: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val itemId = intent.getIntExtra(PRODUCT_ID_EXTRA, -0)
        presenter.loadProductDetails(itemId)
    }

    override fun populateProduct(product: Product) {
        val resourceId = ImageHelper().getResourceIdForImage(this, product.image)
        productDetailImage.setImageResource(resourceId)
        productDetailTitle.text = product.title
        productDetailDescription.text = product.productDescription
        productDetailPrice.text = product.price
        this.product = product
    }

    override fun getActivityLayout(): Int {
        return R.layout.activity_product_details
    }

    override fun injectDependencies(appComponent: AppComponent) {
        appComponent.injectProductDetails(ProductDetailsModule(this)).inject(this)
    }

    override fun showProgress() {
        productDetailsProgressBar.visibility = VISIBLE
    }

    override fun hideProgress() {
        productDetailsProgressBar.visibility = GONE
    }

    fun addToBasket(view: View) {
        presenter.addToBasket(product)
    }
}
