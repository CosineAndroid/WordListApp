package com.cosine.wordlist

import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.core.view.size
import com.cosine.wordlist.MainActivity.Companion.data
import com.cosine.wordlist.MainActivity.Companion.nowCategory
import com.cosine.wordlist.MainActivity.Companion.setting
import com.cosine.wordlist.databinding.ActivityMainBinding
import com.cosine.wordlist.databinding.ContentsEditModeBinding
import com.cosine.wordlist.databinding.WordAndAnswerBinding
import org.json.JSONArray
import org.json.JSONObject

class EditActivity : AppCompatActivity() {

    private lateinit var editBinding: ContentsEditModeBinding

    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private lateinit var wordListEdit: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        editBinding = ContentsEditModeBinding.inflate(layoutInflater)
        setContentView(editBinding.root)

        wordListEdit = editBinding.wordListEdit

        preferences = getSharedPreferences(data, MODE_PRIVATE)
        editor = preferences.edit()
        loadWordData()
    }
    override fun onStop() {
        super.onStop()
        saveWordData()
        setting = false
    }
    private fun loadWordData() {
        val jsonString = preferences.getString("$nowCategory", null) ?: return
        val categoryArray = JSONObject(jsonString)

        for (word in categoryArray.keys()) {
            val answer = categoryArray.getString(word)
            addWordSetting(word, answer)
        }
    }
    private fun addWordSetting(word: String, answer: String) {
        val index = wordListEdit.size - 1
        val newWordCard = WordAndAnswerBinding.inflate(layoutInflater)
        val wordEdit = newWordCard.wordEdit
        val answerEdit = newWordCard.answerEdit
        wordEdit.text = word.toEditable()
        answerEdit.text = answer.toEditable()

        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(0, 0, 0, 60)

        wordListEdit.addView(newWordCard.wordAndAnswerEdit, index, layoutParams)
    }
    private fun saveWordData() {
        val wordCount = wordListEdit.childCount - 1
        if (wordCount == 0) return

        val categoryArray = JSONObject()
        for (loop in 0..wordCount) {
            val wordAndAnswer = wordListEdit.getChildAt(loop)
            if (wordAndAnswer is LinearLayout) {
                val word = (wordAndAnswer.getChildAt(0) as EditText).text.toString()
                val answer = (wordAndAnswer.getChildAt(1) as EditText).text.toString()
                if (word.isEmpty() || answer.isEmpty()) continue
                categoryArray.put(word, answer)
            }
        }
        editor.putString("$nowCategory", categoryArray.toString())
        editor.apply()
    }
    fun onClickWordPlusButton(view: View) {
        val index = wordListEdit.size - 1
        val newWordCard = WordAndAnswerBinding.inflate(layoutInflater).wordAndAnswerEdit

        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(0, 0, 0, 50)

        wordListEdit.addView(newWordCard, index, layoutParams)
    }
}