package com.adarsh.fragmentdynamic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main3.*

//Adding views dynamically to ViewPager
class Main3Activity : AppCompatActivity() {

    private var mainPagerAdapter : MainPageAdapter = MainPageAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        mainPagerAdapter = MainPageAdapter()
        viewPagerinst.adapter = mainPagerAdapter

        val inflater:LayoutInflater = layoutInflater
        val v0:FrameLayout = inflater.inflate(R.layout.dummy_resource, null) as FrameLayout
        mainPagerAdapter.addView(v0, 0)
        mainPagerAdapter.notifyDataSetChanged()

        addView((layoutInflater.inflate(R.layout.dummy_2_resource , null) as FrameLayout))
    }

    private fun addView(newPage: View){
        val pageIndex: Int = mainPagerAdapter.addView(newPage)

        //DO this, if you want to make this page as current page
        viewPagerinst.setCurrentItem(pageIndex, true)
        mainPagerAdapter.notifyDataSetChanged()
    }

    private fun removeView(newPage: View){
        var pageIndex: Int = mainPagerAdapter.removeView(viewPagerinst, newPage)
        //If you want to choose what page to display if this page is deleted
        if(pageIndex == mainPagerAdapter.count)
            pageIndex--
        viewPagerinst.currentItem = pageIndex
    }

    private fun getCurrentPage(): View = mainPagerAdapter.getView(viewPagerinst.currentItem)

    private fun setCurrentPage(pageToShow: View)=
        viewPagerinst.setCurrentItem(mainPagerAdapter.getItemPosition(pageToShow), true)

}
