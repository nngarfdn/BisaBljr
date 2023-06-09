package com.nara.bacayuk.ui.feat_belajar_kalimat

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.nara.bacayuk.R
import com.nara.bacayuk.data.model.ReportKalimat
import com.nara.bacayuk.data.model.ReportKata
import com.nara.bacayuk.data.model.SoalKata
import com.nara.bacayuk.data.model.Student
import com.nara.bacayuk.databinding.ActivityQuizKalimatBinding
import com.nara.bacayuk.databinding.ItemQuizSusunBinding
import com.nara.bacayuk.ui.customview.AnswerStatusDialog
import com.nara.bacayuk.ui.customview.CenterLinearLayoutManager
import com.nara.bacayuk.ui.feat_baca_kata.quiz.QuizSusunAdapter
import com.nara.bacayuk.ui.feat_baca_kata.quiz.QuizViewModel
import com.nara.bacayuk.ui.listener.adapter.AdapterQuizListener
import com.nara.bacayuk.ui.listener.adapter.ViewPositionListener
import com.nara.bacayuk.utils.gone
import com.nara.bacayuk.utils.invisible
import com.nara.bacayuk.utils.loadImage
import com.nara.bacayuk.utils.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class QuizKalimatActivity : AppCompatActivity(), AdapterQuizListener, ViewPositionListener {
    private val binding by lazy { ActivityQuizKalimatBinding.inflate(layoutInflater) }
    var student: Student? = null
    var soalKata: SoalKata? = null
    var listQuestions: MutableList<String> = mutableListOf()
    var listAnswer: MutableList<String> = mutableListOf()
    var sizeQuestion = 0
    var count = 0
    var lastOptionView: View? = null
    var lastAnswerView: View? = null
    var isKata: Boolean = false
    var reportKata: ReportKata? = null
    var reportKalimat: ReportKalimat? = null
    private val quizViewModel: QuizViewModel by viewModel()
    private val adapterOption by lazy {
        QuizSusunAdapter(
            this@QuizKalimatActivity,
            this@QuizKalimatActivity,
            "option"
        )
    }
    private val adapterAnswer by lazy {
        QuizSusunAdapter(
            this@QuizKalimatActivity,
            this@QuizKalimatActivity,
            "answer"
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        isKata = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getBooleanExtra("isKata", false)
        } else {
            intent.getBooleanExtra("isKata", false)
        }

        student = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("student", Student::class.java)
        } else {
            intent.getParcelableExtra("student") as Student?
        }

        soalKata = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("quiz", SoalKata::class.java)
        } else {
            intent.getParcelableExtra("quiz") as SoalKata?
        }

        if (isKata) {
            reportKata = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra("data", ReportKata::class.java)
            } else {
                intent.getParcelableExtra("data") as ReportKata?
            }
        } else {
            reportKalimat = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra("data", ReportKalimat::class.java)
            } else {
                intent.getParcelableExtra("data") as ReportKalimat?
            }
        }

        listQuestions = stringToList(soalKata?.optionList ?: "", "-")
        listQuestions.shuffle()
        sizeQuestion = listQuestions.size

        binding.apply {
            binding.toolbar.apply {
                txtTitle.text = if (isKata) "Baca Kata" else "Baca Kalimat"
                imageView.setOnClickListener { onBackPressed() }
                imgActionRight.invisible()
            }
            imageView4.loadImage(this@QuizKalimatActivity, soalKata?.imageUrl ?: "")
            opta.opt1.apply {
                text = listQuestions[0]
                setOnClickListener {
                    opta.opt1.invisible()
                    listAnswer.add(listQuestions[0])
                    binding.txtAnswer.text = listAnswer.joinToString(if (isKata) "" else " ")
                }
            }
            opti.opt1.apply {
                text = listQuestions[1]
                setOnClickListener {
                    opti.opt1.invisible()
                    listAnswer.add(listQuestions[1])
                    binding.txtAnswer.text = listAnswer.joinToString(if (isKata) "" else " ")
                }
            }

            optu.opt1.apply {
                text = listQuestions[2]
                setOnClickListener {
                    optu.opt1.invisible()
                    listAnswer.add(listQuestions[2])
                    binding.txtAnswer.text = listAnswer.joinToString(if (isKata) "" else " ")
                }
            }

            if (isKata) {
                if ((soalKata?.level ?: 0) < 8) {
                    opte.root.gone()
                } else {
                    opte.root.visible()
                    opte.opt1.text = listQuestions[3]
                    opte.opt1.apply {
                        text = listQuestions[3]
                        setOnClickListener {
                            optu.opt1.invisible()
                            listAnswer.add(listQuestions[3])
                            binding.txtAnswer.text =
                                listAnswer.joinToString(if (isKata) "" else " ")
                        }
                    }
                }
            } else {
                opte.root.gone()
            }
//            for (data in listQuestions) {
//                val item = ItemQuizSusunBinding.inflate(layoutInflater)
//                item.opt1.text = data
//                item.opt1.setOnClickListener {
//                    it.invisible()
//                    listAnswer.add(data as String)
//                    item.opt1.animate().alpha(1.0f)
//                    .setDuration(800)
//                    .setStartDelay((10).toLong())
//                    .withEndAction {
//                        binding.txtAnswer.text = listAnswer.joinToString(if (isKata) "" else " ")
//                    }
//                    .start()
//                }
//                placeholder.addView(item.root)
//            }

            rvOption.apply {
                adapter = adapterOption
                layoutManager = CenterLinearLayoutManager(
                    this@QuizKalimatActivity,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                clipToPadding = false
            }
            rvAnswer.apply {
                adapter = adapterAnswer
                layoutManager = LinearLayoutManager(
                    this@QuizKalimatActivity,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            }
            btnLogin.setOnClickListener {
                Log.d("quizsusun", "$isKata- $reportKata- $reportKalimat- ${student?.uuid ?: "-"}")
                if (txtAnswer.text == soalKata?.correctAnswer) {
                    if (reportKata != null) {
                        for (item in reportKata!!.quizSusunKata) {
                            if (item.level == soalKata!!.level) {
                                item.isCorrect = true
                                val index = item.level - 1
                                reportKata!!.quizSusunKata[index] = item
                                quizViewModel.updateReportKata(
                                    student?.uuid ?: "-", reportKata!!
                                )
                            }
                        }
                    }

                    if (reportKalimat != null) {
                        for (item in reportKalimat!!.quizSusunKata) {
                            if (item.level == soalKata!!.level) {
                                item.isCorrect = true
                                val index = item.level - 1
                                reportKalimat!!.quizSusunKata[index] = item
                                quizViewModel.updateReportKalimat(
                                    student?.uuid ?: "-", reportKalimat!!
                                )
                            }
                        }
                    }
                    val dialog = AnswerStatusDialog(
                        this@QuizKalimatActivity,
                        icon = R.drawable.ic_checklist,
                        status = "Benar"
                    )
                    dialog.show()
                    val layoutParams = WindowManager.LayoutParams()
                    layoutParams.copyFrom(dialog.getWindow()?.getAttributes())
                    layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
                    layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
                    dialog.getWindow()?.setAttributes(layoutParams)
                } else {
                    val dialog = AnswerStatusDialog(
                        this@QuizKalimatActivity,
                        icon = R.drawable.ic_wrong_answer,
                        status = "Salah"
                    )
                    dialog.show()
                    val layoutParams = WindowManager.LayoutParams()
                    layoutParams.copyFrom(dialog.getWindow()?.getAttributes())
                    layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
                    layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
                    dialog.getWindow()?.setAttributes(layoutParams)
                }
            }
        }
    }


    fun stringToList(str: String, separator: String): MutableList<String> {
        return str.split(separator).toMutableList()
    }

    private fun moveView(viewToBeMoved: View, targetView: View) {
        val targetX: Float =
            targetView.x + targetView.width / 2 - viewToBeMoved.width / 2
        val targetY: Float =
            targetView.y + targetView.height / 2 - viewToBeMoved.height / 2

        viewToBeMoved.animate()
            .x(targetX)
            .y(targetY)
            .setDuration(1000)
            .withEndAction {
                targetView.visibility = View.VISIBLE
            }
            .start()
    }

//    override fun onClick(data: Any?, position: Int?, view: View?, type: String) {
//        when(type) {
//            "option" -> {
////                moveView(binding.rvAnswer.layoutManager?.findViewByPosition(position?:0)!!, binding.rvAnswer.layoutManager?.findViewByPosition(0)!!)
//                view?.invisible()
//                listAnswer.add(data as String)
//                adapterAnswer.submitData(listAnswer)
//                adapterAnswer.notifyDataSetChanged()
//                binding.rvAnswer.viewTreeObserver.addOnPreDrawListener(
//                    object : ViewTreeObserver.OnPreDrawListener {
//                        override fun onPreDraw(): Boolean {
//                            binding.rvAnswer.getViewTreeObserver().removeOnPreDrawListener(this)
//                            for (i in 0 until binding.rvAnswer.getChildCount()) {
//                                val v: View = binding.rvAnswer.getChildAt(i)
//                                v.alpha = 0.0f
//                                v.animate().alpha(1.0f)
//                                    .setDuration(800)
//                                    .setStartDelay((i * 50).toLong())
//                                    .start()
//                            }
//                            return true
//                        }
//                    })
//            }
//            "answer" -> {
//
//            }
//        }
//    }

    override fun getView(view: View?, type: String) {
        when (type) {
            "option" -> {
                lastOptionView = view
            }
            "answer" -> {
                //getview first
                lastAnswerView = view
            }
        }
    }


    override fun onClick(data: Any?, position: Int?, view: View, view2: View, type: String) {
        when (type) {
            "option" -> {
//                moveView(view, binding.txtAnswer )
                view.invisible()
                listAnswer.add(data as String)

                binding.txtAnswer.animate().alpha(1.0f)
                    .setDuration(800)
                    .setStartDelay((50).toLong())
                    .withEndAction { binding.txtAnswer.text = listAnswer.joinToString("") }
                    .start()
            }
            "pilgan" -> {
            }
        }
    }

//bagaimana cara membuat center di recycler view horizontal

}