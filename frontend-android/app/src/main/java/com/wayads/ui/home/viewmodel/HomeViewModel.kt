package com.wayads.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wayads.app.R

class HomeViewModel : ViewModel() {
    private val _bannerPrincipal = MutableLiveData<Int>(R.drawable.banner_subway)
    val bannerPrincipal: LiveData<Int> = _bannerPrincipal

    private val _bannerInferior = MutableLiveData<Int>(R.drawable.ad_placeholder)
    val bannerInferior: LiveData<Int> = _bannerInferior

    // Exemplo: atualizar banners dinamicamente
    fun atualizarBanners(novoBanner: Int, novoAd: Int) {
        _bannerPrincipal.value = novoBanner
        _bannerInferior.value = novoAd
    }
}