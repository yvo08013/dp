package com.example.dp.ui.home

import android.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.dp.core.ui.BaseFragment
import com.example.dp.core.ui.adapter.AppListAdapter
import com.example.dp.core.ui.adapter.appListAdapter
import com.example.dp.core.utils.appViewModels
import com.example.dp.core.utils.hideKeyboard
import com.example.dp.core.utils.observe
import com.example.dp.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collectLatest

class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {
    private val viewModel: HomeViewModel by appViewModels()

    private val onItemClickListener: (UserUIModel) -> Unit = { user ->
        findNavController().navigate(
            HomeFragmentDirections.actionFHomeToFUserInfo(user.ID)
        )
    }

    private val recyclerAdapter: AppListAdapter by appListAdapter(
        userAdapterDelegate(onItemClickListener)
    )

    private val onQueryChangedListener = object : SearchView.OnQueryTextListener,
        androidx.appcompat.widget.SearchView.OnQueryTextListener {
        override fun onQueryTextChange(newText: String): Boolean {
            viewModel.updateSearchQuery(newText)
            return false
        }

        override fun onQueryTextSubmit(query: String): Boolean {
            return false
        }
    }

    private val onScrollStateListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                hideKeyboard()
            }
        }
    }

    override fun initUI() {
        with(binding.rvSearchInput) {
            adapter = recyclerAdapter
            setHasFixedSize(true)
            addOnScrollListener(onScrollStateListener)
        }

        binding.etSearchInput.setOnQueryTextListener(onQueryChangedListener)
    }

    override fun subscribeUI() {
        observe(Lifecycle.State.STARTED) {
            viewModel.usersList.collectLatest(recyclerAdapter::submitList)
        }
    }
}