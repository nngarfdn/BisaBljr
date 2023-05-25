package com.nara.bacayuk.ui.feat_baca_huruf.materi_baca_huruf

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.nara.bacayuk.R
import com.nara.bacayuk.data.model.Abjad
import com.nara.bacayuk.data.model.ReportHuruf
import com.nara.bacayuk.data.model.Response
import com.nara.bacayuk.data.model.User
import com.nara.bacayuk.databinding.FragmentHuruf1Binding
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val ARG_PARAM1 = "param1"

class Huruf1Fragment : Fragment() {
    private var _binding: FragmentHuruf1Binding? = null
    val binding get() = _binding!!
    private var param1: String? = null
    private lateinit var listener: (CharSequence) -> Unit
    private var abjad: Abjad? = null
    private val materiBacaHurufViewModel: MateriBacaHurufViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHuruf1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user: User? = materiBacaHurufViewModel.getUserDataStore()
        user?.uuid?.let { materiBacaHurufViewModel.getUser(it) }
        Log.d("materihuruf", "${user?.uuid}, ${MateriBacaHurufActivity.student?.uuid}")
        materiBacaHurufViewModel.user.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Response.Success -> {
                    Log.d("LoginActivity", "onCreate: ${response.data.email}")
                    if (!response.data.isReadyHurufDataSet) {
                        response.data.uuid?.let {
                            materiBacaHurufViewModel.createReportHurufDataSets(true,
                                it,MateriBacaHurufActivity.student?.uuid ?: "-"
                            )
                        }
                    }
                }
                is Response.Error -> {
                    Toast.makeText(context, "${response.e?.message}", Toast.LENGTH_SHORT).show()
                    Log.d("LoginActivity", "onCreate: ${response.message}")
                }
                else -> {

                }
            }
        }

        abjad = MateriBacaHurufActivity.dataAbjad
        when (param1) {
            "0" -> {
                binding.materi.apply {
                    txtAbjad.text = abjad?.abjadNonKapital
                    txtDesc.text = getString(R.string.ini_huruf_kecil)
                    abjad?.reportHuruf?.materiHurufNonKapital = true
                    val reportHuruf = abjad?.reportHuruf
                    materiBacaHurufViewModel.updateReportHuruf(user?.uuid?: "-",
                        MateriBacaHurufActivity.student?.uuid ?: "-",
                        reportHuruf ?: ReportHuruf())
                }

            }
            "1" -> {
                binding.materi.apply {
                    txtDesc.text = getString(R.string.ini_huruf_kapital)
                    txtAbjad.text = abjad?.abjadKapital
                    abjad?.reportHuruf?.materiHurufKapital = true
                    val reportHuruf = abjad?.reportHuruf
                    materiBacaHurufViewModel.updateReportHuruf(user?.uuid?: "-",
                        MateriBacaHurufActivity.student?.uuid ?: "-",
                        reportHuruf ?: ReportHuruf())
                }
            }
            "2" -> {
                binding.materi.apply {
                    txtDesc.text = getString(R.string.ini_perbedaan_huruf)
                    "${abjad?.abjadNonKapital} ${abjad?.abjadKapital}".also { txtAbjad.text = it }
                    abjad?.reportHuruf?.materiPerbedaanHuruf = true
                    val reportHuruf = abjad?.reportHuruf
                    materiBacaHurufViewModel.updateReportHuruf(user?.uuid?: "-",
                        MateriBacaHurufActivity.student?.uuid ?: "-",
                        reportHuruf ?: ReportHuruf())
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, listener: (CharSequence) -> Unit) =
            Huruf1Fragment().apply {
                this.listener = listener
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}