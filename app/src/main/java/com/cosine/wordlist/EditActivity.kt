package com.cosine.wordlist

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.view.size
import com.cosine.wordlist.databinding.WordAndAnswerBinding

class EditActivity : AppCompatActivity() {

    private lateinit var wordList: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contents_edit_mode)
        wordList = findViewById(R.id.wordListEdit)
    }

    override fun onStop() {
        super.onStop()

        val wordCount = wordList.childCount - 1

        for (loop in 0..wordCount) {
            val wordAndAnswer = wordList.getChildAt(loop)

            if (wordAndAnswer is LinearLayout) {
                val word = (wordAndAnswer.getChildAt(0) as EditText).text.toString()
                val answer = (wordAndAnswer.getChildAt(1) as EditText).text.toString()
                if (word.isEmpty() || answer.isEmpty()) continue
                MainActivity.editor.putString(word, answer)
                MainActivity.editor.apply()
            }
        }
    }

    fun onClickWordPlusButton(view: View) {
        val index = wordList.size - 1
        val newWordCard = WordAndAnswerBinding.inflate(layoutInflater).wordAndAnswerEdit

        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(30, 0, 0, 60)

        wordList.addView(newWordCard, index, layoutParams)
    }
}