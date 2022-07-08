package com.android.example.testapplication.overview

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.android.example.testapplication.R
import com.android.example.testapplication.databinding.FragmentOverviewBinding
import com.android.example.testapplication.domain.UserDomain
import com.firebase.ui.auth.AuthUI

class OverviewFragment : Fragment() {

    /**
     * Lazily initialize our [OverviewViewModel].
     */
    private val viewModel: OverviewViewModel by lazy {
        ViewModelProvider(this).get(OverviewViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the OverviewFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentOverviewBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel
        binding.usersList.adapter = UserAdapter(UserAdapter.OnClickListener {
            viewModel.displayUserDetails(it)
        })



        viewModel.navigateToSelectedUser.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                this.findNavController().navigate(
                    OverviewFragmentDirections.actionOverviewFragmentToDetailFragment(it)
                )
                viewModel.displayUserDetailsComplete()
            }
        })


        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_create -> {

                var lastId = viewModel.getLastDatabaseId()
                lastId++
                val user: UserDomain = UserDomain(
                    lastId, "", "", "", "", "", "", "", "", "", "", "", "", ""
                )
                viewModel.insertUser(user)
                findNavController().navigate(
                    OverviewFragmentDirections.actionOverviewFragmentToDetailFragment(
                        user
                    )
                )
                true
            }
            R.id.action_log_out -> {

                findNavController().navigate(R.id.action_overviewFragment_to_mainFragment)
                AuthUI.getInstance().signOut(requireContext())
                true
            }
            else ->
                super.onOptionsItemSelected(item)
        }


    }


}