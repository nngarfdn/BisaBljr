package com.android.bisabelajar.ui.feat_dashboard

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.bisabelajar.databinding.ActivityMainBinding
import com.android.bisabelajar.ui.feat_hurufkapital.AbjadKapitalActivity
import com.android.bisabelajar.ui.feat_urutabjad.UrutAbjadActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            btnAbjadKapital.setOnClickListener {
                startActivity(Intent(this@MainActivity, AbjadKapitalActivity::class.java))
            }
            btnUrutAbjad.setOnClickListener {
                startActivity(Intent(this@MainActivity, UrutAbjadActivity::class.java))
            }
        }

        mainViewModel.saveEmail("nanang@gmail.com")
        val email = mainViewModel.getEmail()
        Toast.makeText(this, "$email", Toast.LENGTH_SHORT).show()

    }


}