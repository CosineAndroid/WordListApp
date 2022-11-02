package com.cosine.wordlist

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.children
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var navigationView: NavigationView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var wordList: LinearLayout

    private lateinit var defaultCategory: DefaultCategory
    private lateinit var otherCategory: OtherCategory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigationView = findViewById(R.id.categoryNavigation)
        drawerLayout = findViewById(R.id.drawerLayout)
        //wordList = findViewById(R.id.wordList)

        defaultCategory = DefaultCategory()
        otherCategory = OtherCategory()
        supportFragmentManager.beginTransaction().add(com.google.android.material.R.id.container, defaultCategory).commit()

        navigationView.setNavigationItemSelectedListener(this)
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
        when (val order = categories.indexOf(item)) {
            last -> {
                menu.add(0, R.id.categoryItem, 1, "1")
            }
            else -> {
                onChangedFragment(order)
            }
            //            if (drawerLayout.isDrawerOpen(GravityCompat.START))
            //                drawerLayout.closeDrawer(GravityCompat.START)
        }
        return true
    }
    fun onChangedFragment(order: Int) {
        supportFragmentManager.beginTransaction().replace(com.google.android.material.R.id.container, defaultCategory).commit()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.categories, menu)
        return true
    }
    fun onClickEditModeButton(view: View) {
        val intent = Intent(applicationContext, EditActivity::class.java)
        startActivity(intent)
    }
//    fun addWordCard(word: String, answer: String) {
//        val newWordCard = WordCardBinding.inflate(layoutInflater)
//
//        newWordCard.apply {
//            this.wordBack.root.text = answer.toEditable()
//            this.wordFront.root.text = word.toEditable()
//        }
//
//        val layoutParams = LinearLayout.LayoutParams(
//            LinearLayout.LayoutParams.MATCH_PARENT,
//            LinearLayout.LayoutParams.WRAP_CONTENT
//        )
//        layoutParams.setMargins(0, 0, 0, 60)
//
//        wordList.addView(newWordCard.wordEdit, layoutParams)
//    }
}