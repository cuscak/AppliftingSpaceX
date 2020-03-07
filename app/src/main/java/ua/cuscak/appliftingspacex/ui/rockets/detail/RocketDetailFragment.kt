package ua.cuscak.appliftingspacex.ui.rockets.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs

import ua.cuscak.appliftingspacex.R
import ua.cuscak.appliftingspacex.databinding.FragmentRocketDetailBinding

/**
 * A simple [Fragment] subclass.
 */
class RocketDetailFragment : Fragment() {

    private val args: RocketDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val viewModel = ViewModelProvider(this, RocketDetailViewModelFactory(args.rocketId)).get(RocketDetailViewModel::class.java)

        // Inflate the layout for this fragment
        val binding = FragmentRocketDetailBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        return binding.root
    }

}
