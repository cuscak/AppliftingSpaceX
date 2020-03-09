package ua.cuscak.appliftingspacex.ui.launches

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

import ua.cuscak.appliftingspacex.R
import ua.cuscak.appliftingspacex.databinding.FragmentLaunchesBinding

/**
 * A simple [Fragment] subclass.
 */
class LaunchesFragment : Fragment() {

    /**
     * Lazily initialize our [LaunchesViewModel].
     */
    private val viewModel: LaunchesViewModel by lazy {
        ViewModelProvider(this).get(LaunchesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentLaunchesBinding.inflate(inflater)

        binding.lifecycleOwner = this

        // Giving the binding access to the RocketOverviewViewModel
        binding.viewmodel = viewModel

        binding.recyclerLaunches.apply {
            adapter = LaunchItemAdapter()
        }

        return binding.root
    }

}
