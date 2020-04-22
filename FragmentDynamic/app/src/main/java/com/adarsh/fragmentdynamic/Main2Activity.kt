package com.adarsh.fragmentdynamic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val bundle = Bundle()
        bundle.putString("Key", "Adarsh Singh")
        val fragment = FirstFragment()
        fragment.arguments = bundle

        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.apply {
            add(fragment)
            add(BlankFragment())
            add(FirstFragment())
        }
        viewPager.adapter = viewPagerAdapter
        viewPager.setPageTransformer(true, DepthPageTransformer())


    }
}
