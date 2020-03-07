package ua.cuscak.appliftingspacex.ui.rockets.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RocketDetailViewModelFactory(private val id: String): ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RocketDetailViewModel(id) as T
    }
}