package ua.cuscak.appliftingspacex.ui.rockets.overview

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration

import ua.cuscak.appliftingspacex.R
import ua.cuscak.appliftingspacex.databinding.FragmentRocketOverviewBinding

/**
 * A simple [Fragment] subclass.
 */
class RocketOverviewFragment : Fragment() {

    /**
     * Lazily initialize our [RocketOverviewViewModel].
     */
    private val viewModel: RocketOverviewViewModel by lazy {
        ViewModelProvider(this).get(RocketOverviewViewModel::class.java)
    }

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the RocketOverviewFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val binding = FragmentRocketOverviewBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the RocketOverviewViewModel
        binding.viewModel = viewModel

        binding.recyclerRockets.apply {
            adapter = RocketItemAdapter(RocketItemAdapter.OnClickListener{
                viewModel.displayRecipeDetails(it)
            })

            //addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        viewModel.navigateToSelectedRocket.observe(viewLifecycleOwner, Observer {
            if ( null != it ) {
                val action = RocketOverviewFragmentDirections.actionRocketOverviewFragmentToRocketDetailFragment(it)
                findNavController().navigate(action)
                viewModel.displayRecipeDetailsComplete()
            }
        })

        return binding.root
    }

}
