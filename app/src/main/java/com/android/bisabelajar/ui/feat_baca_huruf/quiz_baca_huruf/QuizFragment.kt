package com.android.bisabelajar.ui.feat_baca_huruf.quiz_baca_huruf

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.bisabelajar.R
import com.android.bisabelajar.data.model.Abjad
import com.android.bisabelajar.ui.feat_baca_huruf.materi_baca_huruf.Huruf1Fragment

private const val ARG_PARAM1 = "param1"

class QuizFragment : Fragment() {
    private var param1: String? = null
    private lateinit var listener: (CharSequence) -> Unit
    private var abjad: Abjad? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, listener: (CharSequence) -> Unit) =
            QuizFragment().apply {
                this.listener = listener
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}