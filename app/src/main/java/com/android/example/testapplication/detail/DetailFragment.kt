package com.android.example.testapplication.detail

import DetailViewModelFactory
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.EditText
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.android.example.testapplication.R
import com.android.example.testapplication.databases.DatabaseUser
import com.android.example.testapplication.databases.UsersDatabase
import com.android.example.testapplication.databases.getDatabase
import com.android.example.testapplication.databinding.FragmentDetailBinding
import com.android.example.testapplication.domain.UserDomain

class DetailFragment : Fragment() {

    private lateinit var dbUser: DatabaseUser
    private lateinit var dbUserForDeletion: DatabaseUser
    private lateinit var viewModel: DetailViewModel
    private lateinit var domainUser: UserDomain
    private lateinit var binding: FragmentDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        activity?.onBackPressedDispatcher?.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    updateDatabaseUser()
                    findNavController().navigate(R.id.action_detailFragment_to_overviewFragment)
                }

            })


    }


    private fun updateDatabaseUser() {
        dbUser = DatabaseUser(
            id = domainUser.id, name = binding.tvName.text.toString(),
            username = binding.tvUsername.text.toString(),
            email = binding.tvEmail.text.toString(),
            street = binding.tvStreet.text.toString(),
            suite = binding.tvSuite.text.toString(),
            city = binding.tvCity.text.toString(),
            zipcode = binding.tvZipCode.text.toString(),
            geo = binding.tvGeo.text.toString(),
            phone = binding.tvPhone.text.toString(),
            website = binding.tvWebsite.text.toString(),
            companyName = binding.tvCompanyName.text.toString(),
            catchPhrase = binding.tvCatchPhrase.text.toString(),
            bs = binding.tvBs.text.toString()
        )
        viewModel.updateUser(dbUser)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        @Suppress("UNUSED_VARIABLE")
        val application = requireNotNull(activity).application
        binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val dataSource = getDatabase(application).userDao
        domainUser = DetailFragmentArgs.fromBundle(requireArguments()).selectedUser
        val viewModelFactory = DetailViewModelFactory(domainUser, dataSource, application)
        viewModel = ViewModelProvider(
            this, viewModelFactory
        ).get(DetailViewModel::class.java)
        binding.viewModel = viewModel

        dbUserForDeletion =
            DatabaseUser(id = domainUser.id, "", "", "", "", "", "", "", "", "", "", "", "", "")



        return binding.root
    }

    /**
     * Inflates the overflow menu that contains filtering options.
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete -> {

                viewModel.deleteUser(dbUserForDeletion)
                findNavController().navigate(R.id.action_detailFragment_to_overviewFragment)
                true
            }
            else ->
                super.onOptionsItemSelected(item)
        }


    }
}