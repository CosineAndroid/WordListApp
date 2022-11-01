package com.cosine.wordlist

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import com.cosine.wordlist.databinding.WordAndAnswerBinding
import com.cosine.wordlist.databinding.WordCardBinding

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var preferences: SharedPreferences
        lateinit var editor: SharedPreferences.Editor
    }

    private lateinit var wordList: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        preferences = getPreferences(Context.MODE_PRIVATE)

        wordList = findViewById(R.id.wordList)

        wordList.removeAllViews()
        for ((word, answer) in preferences.all) {
            addWordCard(word, answer.toString())
        }

        editor = preferences.edit()
    }

    override fun onResume() {
        super.onResume()

        wordList.removeAllViews()
        for ((word, answer) in preferences.all) {
            addWordCard(word, answer.toString())
        }
    }
    fun onClickEditModeButton(view: View) {
        val intent = Intent(applicationContext, EditActivity::class.java)
        startActivity(intent)
    }
    fun addWordCard(word: String, answer: String) {
        val newWordCard = WordCardBinding.inflate(layoutInflater)

        newWordCard.apply {
            this.wordBack.root.text = answer.toEditable()
            this.wordFront.root.text = word.toEditable()
        }

        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(0, 0, 0, 60)

        wordList.addView(newWordCard.wordEdit, layoutParams)
    }
}