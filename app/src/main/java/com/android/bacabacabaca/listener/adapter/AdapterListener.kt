package com.android.bacabacabaca.listener.adapter

import android.view.View

interface AdapterListener {
    fun onClick(data: Any?, position: Int?, view: View?)
}