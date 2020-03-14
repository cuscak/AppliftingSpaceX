package ua.cuscak.appliftingspacex.ui.launches

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ua.cuscak.appliftingspacex.databinding.LaunchItemBinding
import ua.cuscak.appliftingspacex.domain.Launch

class LaunchItemAdapter : ListAdapter<Launch, LaunchItemAdapter.LaunchViewHolder>(DiffCallback) {

    class LaunchViewHolder(private var binding: LaunchItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(launch: Launch) {
            binding.launch = launch
            binding.executePendingBindings()
        }

    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [Launch]
     * has been updated.
     */
    companion object DiffCallback: DiffUtil.ItemCallback<Launch>(){
        override fun areItemsTheSame(oldItem: Launch, newItem: Launch): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Launch, newItem: Launch): Boolean {
            return oldItem.missionName == newItem.missionName
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchViewHolder {
        return LaunchViewHolder(LaunchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: LaunchViewHolder, position: Int) {
        val currentLaunch = getItem(position)
        holder.bind(currentLaunch)
    }
}