package com.raiffeisen.bejancorneliu.tools

import android.databinding.BindingAdapter
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.raiffeisen.bejancorneliu.R

@BindingAdapter("android:src_pic")
fun setImageViewResource(imageView: ImageView, resource: String) {
    Glide.with(imageView.context).load(resource).diskCacheStrategy(DiskCacheStrategy.ALL).apply( RequestOptions().override(60, 60)).apply(RequestOptions.circleCropTransform()).into(imageView)
}

@BindingAdapter("android:src_country")
fun setImageViewResourceCountry(imageView: ImageView, resource: String) {

    var flag = 0

    when(resource) {
        "CH" -> flag = R.drawable.ic_ch
        "DE" -> flag = R.drawable.ic_de
        "FI" -> flag = R.drawable.ic_fi
        "FR" -> flag = R.drawable.ic_fr
        "IC" -> flag = R.drawable.ic_ic
        "IE" -> flag = R.drawable.ic_ie
        "IR" -> flag = R.drawable.ic_ir
        "NZ" -> flag = R.drawable.ic_nz
    }

    if(flag!=0) {
        Glide.with(imageView.context).load(flag).diskCacheStrategy(DiskCacheStrategy.ALL).apply(RequestOptions.circleCropTransform()).into(imageView)
    } else {
        imageView.visibility=View.GONE
    }
}