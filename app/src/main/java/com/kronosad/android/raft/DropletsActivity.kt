package com.kronosad.android.raft

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.kronosad.android.raft.fragments.DropletFragment
import com.kronosad.android.raft.fragments.SnapshotsFragment
import kotlinx.android.synthetic.main.activity_droplets.*
import java.util.*

class DropletsActivity : AppCompatActivity(), DropletFragment.OnFragmentInteractionListener, SnapshotsFragment.OnFragmentInteractionListener {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * [FragmentPagerAdapter] derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null


    /**
     * The [ViewPager] that will host the section contents.
     */
    private var mViewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_droplets)

        setSupportActionBar(toolbar)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        mViewPager = container as ViewPager
        mViewPager!!.adapter = mSectionsPagerAdapter

        mSectionsPagerAdapter!!.addFragment(DropletFragment(), "Droplets")
        mSectionsPagerAdapter!!.addFragment(SnapshotsFragment(), "Snapshots")

        tabs.setupWithViewPager(mViewPager)

        fab.setOnClickListener { view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show() }

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_droplets, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        when (id) {
            R.id.action_settings,
                // Eventually open the settings page :P

            R.id.action_logout -> {
                val prefs = getSharedPreferences("Authentication", Context.MODE_PRIVATE)
                prefs.edit().remove("token").apply()
                val i = Intent(this, LoginActivity::class.java)
                startActivity(i)
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onFragmentInteraction(uri: Uri) {

    }


    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        private val mFragmentTitles = ArrayList<String>()
        private val mFragments = ArrayList<Fragment>()

        fun addFragment(frag: Fragment?, title: String?) {

            if (frag == null || title == null) {
                throw IllegalArgumentException("Fragment and Title MUST NOT be null!")
            }

            mFragments.add(frag)
            mFragmentTitles.add(title)
            notifyDataSetChanged() // Android gets really upset if you don't inform it of changes...
        }

        override fun getItem(position: Int): Fragment {
            return mFragments[position]
        }

        override fun getCount(): Int {
            return mFragmentTitles.size
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mFragmentTitles[position]
        }
    }
}
