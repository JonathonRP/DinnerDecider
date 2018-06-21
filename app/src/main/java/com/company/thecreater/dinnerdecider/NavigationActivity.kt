package com.company.thecreater.dinnerdecider

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_navigation.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.experimental.async

class NavigationActivity : AppCompatActivity(),
        NavigationView.OnNavigationItemSelectedListener,
        FragmentInteractionListener.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_navigation)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        loadData(this)

        val headerView = nav_view.getHeaderView(0)
        headerView.findViewById<TextView>(R.id.title).text = auth.currentUser?.displayName
        headerView.findViewById<TextView>(R.id.subtitle).text = auth.currentUser?.email

        if (auth.currentUser?.photoUrl != null) {
            headerView.findViewById<ImageView>(R.id.logo).setImageURI(auth.currentUser?.photoUrl)
        }

        val fragmentSelect = FragmentStateAdaptor(supportFragmentManager)
        val contentProvider = content

        setupView(fragmentSelect, contentProvider)
        navigateToFragment(0)
    }

    private fun setupView(fragmentSelect: FragmentStateAdaptor, contentProvider: ViewPager) {

        fragmentSelect.addFragment(MainFragment())
        fragmentSelect.addFragment(EditFragment())

        contentProvider.adapter = fragmentSelect
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            if (content.currentItem != 0) {
                navigateToFragment(0)
                nav_view.setCheckedItem(R.id.home)
            } else {
                super.onBackPressed()
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.home -> {
                navigateToFragment(0)
            }
            R.id.edit -> {
                navigateToFragment(1)
            }
            R.id.signOut -> {
                Kitchen.signOut(this)
                Kitchen.detachAuthListener()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onFragmentInteraction(uri: Uri) {

    }

    override fun onResume() {
        super.onResume()

        Kitchen(this).attachAuthListener()
        loadData(this)
    }

    override fun onPause() {
        super.onPause()

        Kitchen.detachAuthListener()
    }

    override fun onDestroy() {
        super.onDestroy()

        Kitchen.stopListening()
    }

    private fun loadData(context: Context) = async {
        Kitchen.loadFoods().addOnFailureListener { e ->
            Toast.makeText(context, "$e", Toast.LENGTH_LONG).show()
        }
    }

    private fun navigateToFragment(fragment: Int) {
        content.currentItem = fragment
    }
}
