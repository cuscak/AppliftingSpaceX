package ua.cuscak.appliftingspacex.ui.rockets.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ua.cuscak.appliftingspacex.databinding.RocketOverviewItemBinding
import ua.cuscak.appliftingspacex.models.Rocket

class RocketItemAdapter(private val onClickListener: OnClickListener): ListAdapter<Rocket, RocketItemAdapter.RocketViewHolder>(DiffCallback){

    /**
     * The RocketViewHolder constructor takes the binding variable from the associated
     * RocketOverviewItem, which nicely gives it access to the full [Rocket] information.
     */
    class RocketViewHolder(private var binding: RocketOverviewItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(rocket: Rocket){
            binding.rocket = rocket
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [Rocket]
     * has been updated.
     */
    companion object DiffCallback: DiffUtil.ItemCallback<Rocket>(){
        override fun areItemsTheSame(oldItem: Rocket, newItem: Rocket): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Rocket, newItem: Rocket): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RocketViewHolder {
        return RocketViewHolder(RocketOverviewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: RocketViewHolder, position: Int) {
        // get the Rocket object associated with current RecyclerView position
        val rocket = getItem(position)
        holder.itemView.setOnClickListener{
            onClickListener.onClick(rocket.rocket_name)
        }
        holder.bind(rocket)
    }

    class OnClickListener(val clickListener: (name:String) -> Unit) {
        fun onClick(name:String) = clickListener(name)
    }
}