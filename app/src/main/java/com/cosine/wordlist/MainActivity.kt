package com.cosine.wordlist

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.children
import androidx.drawerlayout.widget.DrawerLayout
import com.cosine.wordlist.databinding.ActivityMainBinding
import com.cosine.wordlist.databinding.WordCardBinding
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.*
import org.json.JSONObject


class MainActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener, OnClickListener {

    companion object {
        val scope = CoroutineScope(Dispatchers.Main)
        var nowCategory: Int = 0
        var setting = false
    }

    private lateinit var mainBinding: ActivityMainBinding

    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private lateinit var navigationView: NavigationView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var editButton: ImageButton

    private lateinit var categoryWordList0: LinearLayout
    private lateinit var categoryWordList1: LinearLayout
    private lateinit var categoryWordList2: LinearLayout
    private lateinit var categoryWordList3: LinearLayout
    private lateinit var categoryWordList4: LinearLayout
    private lateinit var categoryWordList5: LinearLayout
    private lateinit var categoryWordList6: LinearLayout
    private lateinit var categoryWordList7: LinearLayout
    private lateinit var categoryWordList8: LinearLayout
    private lateinit var categoryWordList9: LinearLayout
    private lateinit var categoryWordList10: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        preferences = getSharedPreferences("Data", MODE_PRIVATE)
        editor = preferences.edit()

        navigationView = mainBinding.categoryNavigation
        drawerLayout = mainBinding.drawerLayout
        editButton = mainBinding.category.editModeButton

        categoryWordList0 = mainBinding.category.category0.wordList
        categoryWordList1 = mainBinding.category.category1.wordList
        categoryWordList2 = mainBinding.category.category2.wordList
        categoryWordList3 = mainBinding.category.category3.wordList
        categoryWordList4 = mainBinding.category.category4.wordList
        categoryWordList5 = mainBinding.category.category5.wordList
        categoryWordList6 = mainBinding.category.category6.wordList
        categoryWordList7 = mainBinding.category.category7.wordList
        categoryWordList8 = mainBinding.category.category8.wordList
        categoryWordList9 = mainBinding.category.category9.wordList
        categoryWordList10 = mainBinding.category.category10.wordList

        navigationView.setNavigationItemSelectedListener(this)
        editButton.setOnClickListener(this)

        loadWordData()
    }
    private fun loadWordData() {
        categoryWordList0.removeAllViews()
        val jsonString = preferences.getString("$nowCategory", null) ?: return
        val categoryArray = JSONObject(jsonString)

        for (word in categoryArray.keys()) {
            val answer = categoryArray.getString(word)
            addWordCard(word, answer)
        }
    }
    private fun addWordCard(word: String, answer: String) {
        val newWordCard = WordCardBinding.inflate(layoutInflater)

        newWordCard.apply {
            wordBack.root.text = answer.toEditable()
            wordFront.root.text = word.toEditable()
        }

        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(0, 0, 0, 60)

        categoryWordList0.addView(newWordCard.wordEdit, layoutParams)
    }
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val menu = navigationView.menu
        val categories = menu.children
        val last = categories.indexOf(categories.last())
        val order = categories.indexOf(item)
        nowCategory = order
        when (order) {
            0 -> onChangeCategory(categoryWordList0)
            1 -> onChangeCategory(categoryWordList1)
            2 -> onChangeCategory(categoryWordList2)
            3 -> onChangeCategory(categoryWordList3)
            4 -> onChangeCategory(categoryWordList4)
            5 -> onChangeCategory(categoryWordList5)
            6 -> onChangeCategory(categoryWordList6)
            7 -> onChangeCategory(categoryWordList7)
            8 -> onChangeCategory(categoryWordList8)
            9 -> onChangeCategory(categoryWordList9)
            10 -> onChangeCategory(categoryWordList10)
        }
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
    private fun onChangeCategory(targetCategory: LinearLayout) {
        mainBinding.category.root.children.forEach {
            println("뷰: $it")
            if (it is LinearLayout) {
                if (it != targetCategory) {
                    it.visibility = View.INVISIBLE
                }
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.categories, menu)
        return true
    }

    override fun onClick(p0: View?) {
        val intent = Intent(applicationContext, EditActivity::class.java)
        startActivity(intent)

        setting = true
        scope.launch {
            while (this.isActive) {
                delay(100)
                if (!setting) {
                    this.cancel()
                    loadWordData()
                }
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}