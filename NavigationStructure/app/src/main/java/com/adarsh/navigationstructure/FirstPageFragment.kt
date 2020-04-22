package com.adarsh.navigationstructure

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_first_page.*

class FirstPageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //One method to navigate
        goToContact.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_firstPageFragment_to_contactFragment))

        //Another method to navigate
        goToHelp.setOnClickListener {
            it.findNavController().navigate(R.id.action_firstPageFragment_to_helpFragment)
        }

        goToSearch.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_firstPageFragment_to_searchFragment))


    }

}
