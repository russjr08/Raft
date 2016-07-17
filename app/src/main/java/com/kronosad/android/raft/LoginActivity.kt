package com.kronosad.android.raft

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.View
import butterknife.ButterKnife
import butterknife.OnClick

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        ButterKnife.bind(this)

        val prefs = getSharedPreferences("Authentication", Context.MODE_PRIVATE)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        if (prefs.getString("token", null) != null) {
            openDropletActivity(prefs.getString("token", null))
            finish()
        }

        if (intent.action == Intent.ACTION_VIEW) {
            retrieveTokenAndPersist()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_login, menu)
        return true
    }

    @OnClick(R.id.activity_login_btn)
    fun onLoginBtnClick(v: View) {
        val typedValue = TypedValue()
        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true)

        val builder = CustomTabsIntent.Builder()
        builder.setToolbarColor(typedValue.data)

        builder.build().launchUrl(this, Uri.parse(Constants.OAUTH_URL))

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun retrieveTokenAndPersist() {
        val token = intent.data.getQueryParameter("code")
        Log.d("Raft", "Retrieved Token: " + token)

        val prefs = getSharedPreferences("Authentication", Context.MODE_PRIVATE)
        prefs.edit().putString("token", token).apply()

        openDropletActivity(token)
    }

    private fun openDropletActivity(token: String) {
        val intent = Intent(this, DropletsActivity::class.java)

        intent.putExtra(Constants.AUTHENTICATION_FINISHED_WITH_TOKEN, token)

        startActivity(intent)

        finish() // Prevent user from going back to this activity.
    }
}
