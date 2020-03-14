package ua.cuscak.appliftingspacex.ui.launches

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import ua.cuscak.appliftingspacex.R
import ua.cuscak.appliftingspacex.databinding.FragmentLaunchesBinding
import ua.cuscak.appliftingspacex.network.LaunchesApiFilter

/**
 * A simple [Fragment] subclass.
 */
class LaunchesFragment : Fragment() {

    /**
     * Lazily initialize our [LaunchesViewModel].
     */
    private val viewModel: LaunchesViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, LaunchesViewModel.Factory(activity.application)).get(LaunchesViewModel::class.java)
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
        setHasOptionsMenu(true)

        return binding.root
    }

    /**
     * Inflates the overflow menu that contains filtering options.
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    /**
     * Updates the filter in the [LaunchesViewModel] when the menu items are selected from the
     * overflow menu.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.updateFilter(
            when (item.itemId){
                R.id.show_upcoming_menu -> LaunchesApiFilter.SHOW_UPCOMING
                R.id.show_past_menu -> LaunchesApiFilter.SHOW_PAST
                else -> LaunchesApiFilter.SHOW_ALL
            }
        )
        return true
    }
}


