package com.adarsh.staticfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_count_up.*
import kotlinx.android.synthetic.main.fragment_count_up.view.*

/**
 * A simple [Fragment] subclass.
 */
class CountUpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        var count =0;
        val fragmentView = inflater.inflate(R.layout.fragment_count_up, container, false)

        fragmentView.button.setOnClickListener {
            count++;
            fragmentView.textView.text = count.toString()
        }


        return fragmentView;
    }

}
