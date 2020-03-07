package ua.cuscak.appliftingspacex.ui.rockets.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ua.cuscak.appliftingspacex.R

/**
 * A simple [Fragment] subclass.
 */
class RocketOverviewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rocket_overview, container, false)
    }

}
