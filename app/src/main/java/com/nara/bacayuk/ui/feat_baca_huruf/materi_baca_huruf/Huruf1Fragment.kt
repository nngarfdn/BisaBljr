package com.nara.bacayuk.ui.feat_baca_huruf.materi_baca_huruf

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.nara.bacayuk.R
import com.nara.bacayuk.data.model.*
import com.nara.bacayuk.databinding.FragmentHuruf1Binding
import com.nara.bacayuk.utils.playAudioFromUrl
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Huruf1Fragment : Fragment() {
    private var _binding: FragmentHuruf1Binding? = null
    val binding get() = _binding!!
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var listener: (CharSequence) -> Unit
    private var abjad: Abjad? = null
    var reportKata: ReportKata? = null

    private val materiBacaHurufViewModel: MateriBacaHurufViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
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



        abjad = MateriBacaHurufActivity.dataAbjad
        when (param1) {
            "0" -> {
                binding.materi.apply {
                    txtAbjad.text = abjad?.abjadNonKapital
                    txtDesc.text = getString(R.string.ini_huruf_kecil)
                    abjad?.reportHuruf?.materiHurufNonKapital = true
                    val reportHuruf = abjad?.reportHuruf
                    imgSound.setOnClickListener {
                        playAudioFromUrl(abjad?.reportHuruf?.audioUrl ?: "")
                    }
                    materiBacaHurufViewModel.updateReportHuruf(
                        user?.uuid ?: "-",
                        MateriBacaHurufActivity.student?.uuid ?: "-",
                        reportHuruf ?: ReportHuruf()
                    )
                }
            }
            "1" -> {
                binding.materi.apply {
                    txtDesc.text = getString(R.string.ini_huruf_kapital)
                    txtAbjad.text = abjad?.abjadKapital
                    abjad?.reportHuruf?.materiHurufKapital = true
                    val reportHuruf = abjad?.reportHuruf
                    imgSound.setOnClickListener {
                        playAudioFromUrl(abjad?.reportHuruf?.audioUrl ?: "")
                    }
                    materiBacaHurufViewModel.updateReportHuruf(
                        user?.uuid ?: "-",
                        MateriBacaHurufActivity.student?.uuid ?: "-",
                        reportHuruf ?: ReportHuruf()
                    )
                }
            }
            "2" -> {
                binding.materi.apply {
                    txtDesc.text = getString(R.string.ini_perbedaan_huruf)
                    "${abjad?.abjadNonKapital} ${abjad?.abjadKapital}".also { txtAbjad.text = it }
                    abjad?.reportHuruf?.materiPerbedaanHuruf = true
                    imgSound.setOnClickListener {
                        playAudioFromUrl(abjad?.reportHuruf?.audioUrl ?: "")
                    }
                    val reportHuruf = abjad?.reportHuruf
                    materiBacaHurufViewModel.updateReportHuruf(
                        user?.uuid ?: "-",
                        MateriBacaHurufActivity.student?.uuid ?: "-",
                        reportHuruf ?: ReportHuruf()
                    )
                }
            }
        }


        materiBacaHurufViewModel.getAllReportKataFromFirestore(MateriBacaHurufActivity.student?.uuid ?: "-")
        materiBacaHurufViewModel.reportKatas.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Response.Success -> {
                    reportKata = response.data
                    Log.d("reportabjadparam2", response.data.belajarVokal.toString())
                    when (param2) {
                        "0" -> {
                            binding.materi.apply {
                                txtAbjad.text = "A a"
                                txtDesc.text = getString(R.string.ini_huruf_vokal)
                                reportKata?.belajarVokal?.isADone = true
                                imgSound.setOnClickListener {
                                    playAudioFromUrl("https://firebasestorage.googleapis.com/v0/b/bisabelajar-b0579.appspot.com/o/Huruf%20A.m4a?alt=media&token=499b04e1-e499-4279-ac82-89d085e36a44")
                                }
                                Log.d("abjadparam2", "${MateriBacaHurufActivity.student?.uuid} - ${reportKata?.belajarVokal?.isADone}")

                                materiBacaHurufViewModel.updateReportKata(
                                    MateriBacaHurufActivity.student?.uuid ?: "-",
                                    reportKata ?: ReportKata()
                                )
                            }

                        }
                        "1" -> {
                            binding.materi.apply {
                                txtAbjad.text = "I i"
                                txtDesc.text = getString(R.string.ini_huruf_vokal)
                                imgSound.setOnClickListener {
                                    playAudioFromUrl("https://firebasestorage.googleapis.com/v0/b/bisabelajar-b0579.appspot.com/o/Huruf%20I.m4a?alt=media&token=9b10c553-e0c0-40f0-b498-094cf9295c04")
                                }
                            }
                            reportKata?.belajarVokal?.isIDone = true

                            materiBacaHurufViewModel.updateReportKata(
                                MateriBacaHurufActivity.student?.uuid ?: "-",
                                reportKata ?: ReportKata()
                            )
                        }
                        "2" -> {
                            binding.materi.apply {
                                txtAbjad.text = "U u"
                                txtDesc.text = getString(R.string.ini_huruf_vokal)
                                imgSound.setOnClickListener {
                                    playAudioFromUrl("https://firebasestorage.googleapis.com/v0/b/bisabelajar-b0579.appspot.com/o/Huruf%20U.m4a?alt=media&token=0757db6e-d39a-48ee-a5cd-7c81c044016c")
                                }
                            }
                            reportKata?.belajarVokal?.isUDone = true
                            materiBacaHurufViewModel.updateReportKata(
                                MateriBacaHurufActivity.student?.uuid ?: "-",
                                reportKata ?: ReportKata()
                            )
                        }
                        "3" -> {
                            binding.materi.apply {
                                txtAbjad.text = "E e"
                                txtDesc.text = getString(R.string.ini_huruf_vokal)
                                imgSound.setOnClickListener {
                                    playAudioFromUrl("https://firebasestorage.googleapis.com/v0/b/bisabelajar-b0579.appspot.com/o/Huruf%20E.m4a?alt=media&token=f0f726f4-6707-4a47-b3fb-0f5a9cc24167")
                                }
                            }
                            reportKata?.belajarVokal?.isEDone = true
                            materiBacaHurufViewModel.updateReportKata(
                                MateriBacaHurufActivity.student?.uuid ?: "-",
                                reportKata ?: ReportKata()
                            )
                        }
                        "4" -> {
                            binding.materi.apply {
                                txtAbjad.text = "O o"
                                txtDesc.text = getString(R.string.ini_huruf_vokal)
                                imgSound.setOnClickListener {
                                    playAudioFromUrl("https://firebasestorage.googleapis.com/v0/b/bisabelajar-b0579.appspot.com/o/Huruf%20O.m4a?alt=media&token=cb7b3958-bb38-4175-ba4e-4e6417cef6ee")
                                }
                            }
                            reportKata?.belajarVokal?.isODone = true
                            materiBacaHurufViewModel.updateReportKata(
                                MateriBacaHurufActivity.student?.uuid ?: "-",
                                reportKata ?: ReportKata()
                            )
                        }
                    }
                }
                else -> {

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


        @JvmStatic
        fun newInstanceKata(param1: String, listener: (CharSequence) -> Unit) =
            Huruf1Fragment().apply {
                this.listener = listener
                arguments = Bundle().apply {
                    putString(ARG_PARAM2, param1)
                }
            }
    }
}