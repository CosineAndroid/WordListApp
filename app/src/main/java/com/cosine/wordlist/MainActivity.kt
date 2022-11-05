package com.cosine.wordlist

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.children
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import com.cosine.wordlist.databinding.ActivityMainBinding
import com.cosine.wordlist.databinding.WordCardBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.*
import org.json.JSONObject

class MainActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener, OnClickListener {

    companion object {
        val scope = CoroutineScope(Dispatchers.Main)
        var nowCategory: Int = 0
        var setting = false
        var data = "WordData"
    }

    private lateinit var mainBinding: ActivityMainBinding

    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private lateinit var navigationView: NavigationView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var editButton: ImageButton
    private lateinit var categoryTitle: EditText

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

    private lateinit var adView : AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        MobileAds.initialize(this) {}
        adView = mainBinding.category.adView
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        preferences = getSharedPreferences(data, MODE_PRIVATE)
        editor = preferences.edit()

        navigationView = mainBinding.categoryNavigation
        drawerLayout = mainBinding.drawerLayout
        editButton = mainBinding.category.editModeButton
        categoryTitle = mainBinding.category.categoryTitle

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
        categoryTitle.addTextChangedListener(textWatcher)

        setDefaultTitle()
        loadWordData()
    }
    private fun setDefaultTitle() {
        (0..10).forEach {
            if (preferences.contains("타이틀$it")) {
                val title = preferences.getString("타이틀$it", "")
                mainBinding.categoryNavigation.menu[it].title = title
                setNavigationTitleToEditText()
            } else {
                editor.putString("타이틀$it", "카테고리$it")
                if (it == 0) editor.putString("타이틀0", "기본")
                editor.apply()
            }
        }
    }
    private fun loadWordData() {
        removeViewFromTargetView()
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

        addWordCardToTargetView(newWordCard.wordEdit, layoutParams)
    }
    private fun removeViewFromTargetView() {
        if (nowCategory == 0) categoryWordList0.removeAllViews()
        if (nowCategory == 1) categoryWordList1.removeAllViews()
        if (nowCategory == 2) categoryWordList2.removeAllViews()
        if (nowCategory == 3) categoryWordList3.removeAllViews()
        if (nowCategory == 4) categoryWordList4.removeAllViews()
        if (nowCategory == 5) categoryWordList5.removeAllViews()
        if (nowCategory == 6) categoryWordList6.removeAllViews()
        if (nowCategory == 7) categoryWordList7.removeAllViews()
        if (nowCategory == 8) categoryWordList8.removeAllViews()
        if (nowCategory == 9) categoryWordList9.removeAllViews()
        if (nowCategory == 10) categoryWordList10.removeAllViews()
    }
    private fun addWordCardToTargetView(child: View, params: ViewGroup.LayoutParams) {
        if (nowCategory == 0) categoryWordList0.addView(child, params)
        if (nowCategory == 1) categoryWordList1.addView(child, params)
        if (nowCategory == 2) categoryWordList2.addView(child, params)
        if (nowCategory == 3) categoryWordList3.addView(child, params)
        if (nowCategory == 4) categoryWordList4.addView(child, params)
        if (nowCategory == 5) categoryWordList5.addView(child, params)
        if (nowCategory == 6) categoryWordList6.addView(child, params)
        if (nowCategory == 7) categoryWordList7.addView(child, params)
        if (nowCategory == 8) categoryWordList8.addView(child, params)
        if (nowCategory == 9) categoryWordList9.addView(child, params)
        if (nowCategory == 10) categoryWordList10.addView(child, params)
    }
    private val textWatcher = object: TextWatcher {
        // 입력 전
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
        // 입력 변화 시
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
        // 입력 끝
        override fun afterTextChanged(p0: Editable?) {
            if (p0.toString().isEmpty()) {
                Toast.makeText(applicationContext, "카테고리 이름을 적어주세요!", Toast.LENGTH_SHORT).show()
                return
            }
            val categoryTitleName = p0.toString()
            mainBinding.categoryNavigation.menu[nowCategory].title = categoryTitleName
            saveTitle(categoryTitleName)
        }
    }
    private fun saveTitle(title: String) {
        editor.putString("타이틀$nowCategory", title)
        editor.apply()
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

        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START)

        if (nowCategory == order) return false

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
        return true
    }
    private fun onChangeCategory(targetCategory: LinearLayout) {
        mainBinding.category.root.children.forEach {
            if (it is ScrollView) {
                if (it.children.first() == targetCategory) {
                    it.visibility = View.VISIBLE
                    loadWordData()
                    setNavigationTitleToEditText()
                } else {
                    it.visibility = View.INVISIBLE
                }
            }
        }
    }
    private fun setNavigationTitleToEditText() {
        val title = preferences.getString("타이틀$nowCategory", "Error")
        mainBinding.category.categoryTitle.text = title!!.toEditable()
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