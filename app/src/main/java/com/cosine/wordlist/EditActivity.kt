package com.cosine.wordlist

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.view.size
import com.cosine.wordlist.databinding.WordAndAnswerBinding
import com.wajahatkarim3.easyflipview.EasyFlipView

class EditActivity : AppCompatActivity() {

    private lateinit var wordList: LinearLayout
    private lateinit var wordListEdit: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contents_edit_mode)
//        wordListEdit = findViewById(R.id.wordListEdit)
//        wordList = findViewById(R.id.wordList)
    }

    override fun onStop() {
        super.onStop()

//        val wordCount = wordListEdit.childCount - 1
//
//        if (wordCount == 0) return

//        for (loop in 0..wordCount) {
//            val wordAndAnswer = wordListEdit.getChildAt(loop)
//
//            if (wordAndAnswer is LinearLayout) {
//                val word = (wordAndAnswer.getChildAt(0) as EditText).text.toString()
//                val answer = (wordAndAnswer.getChildAt(1) as EditText).text.toString()
//                if (word.isEmpty() || answer.isEmpty()) continue
//                MainActivity.editor.putString(word, answer)
//                MainActivity.editor.apply()
//            }
//        }
    }
    fun setWordListToEditor() {

    }
    fun onClickWordPlusButton(view: View) {
        val index = wordListEdit.size - 1
        val newWordCard = WordAndAnswerBinding.inflate(layoutInflater).wordAndAnswerEdit

        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(30, 0, 0, 60)

        wordListEdit.addView(newWordCard, index, layoutParams)
    }
}