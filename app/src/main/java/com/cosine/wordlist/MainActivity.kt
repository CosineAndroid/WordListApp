package com.cosine.wordlist

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CompoundButton
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import com.cosine.wordlist.databinding.WordCardBinding

class MainActivity : AppCompatActivity() {

    private lateinit var wordList: LinearLayout
    private lateinit var switchButton: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wordList = findViewById(R.id.wordList)
        switchButton = findViewById(R.id.switchButton)
        switchButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(applicationContext, "활성화", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(applicationContext, "비활성화", Toast.LENGTH_SHORT).show();
            }
        }
    }
    fun onClickWordPlusButton(view: View) {
        val index = wordList.size - 1
        val newWordCard = WordCardBinding.inflate(layoutInflater).word

        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(0, 0, 0, 60)

        wordList.addView(newWordCard, index, layoutParams)
    }
}