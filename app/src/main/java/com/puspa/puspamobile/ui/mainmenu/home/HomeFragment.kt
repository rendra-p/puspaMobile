package com.puspa.puspamobile.ui.mainmenu.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.puspa.puspamobile.R
import com.puspa.puspamobile.data.Injection
import com.puspa.puspamobile.databinding.FragmentHomeBinding
import com.puspa.puspamobile.ui.auth.BoardingActivity
import com.puspa.puspamobile.ui.submenu.managechild.ChildActivity

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val viewModelFactory = Injection.provideViewModelFactory(requireContext())
        viewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.shimmerLayout.startShimmer()

        setAction()
        setObserver()
    }

    private fun setAction() {
        binding.btnLogout.setOnClickListener {
            viewModel.logoutUser()
        }
        binding.btnChild.setOnClickListener {
            startActivity(Intent(requireContext(), ChildActivity::class.java))
        }
        binding.btnSchedule.setOnClickListener {
            findNavController().navigate(R.id.navigation_jadwal)
        }
    }

    private fun setObserver() {
        viewModel.getProfile()
        viewModel.profileResult.observe(viewLifecycleOwner) { result ->
            result.onSuccess { response ->
                response.data?.let { profileData ->
                    val imageUrl = "https://puspa.sinus.ac.id" + profileData.profilePicture
                    Glide.with(this)
                        .load(imageUrl)
                        .placeholder(R.drawable.baseline_account_circle_24)
                        .error(R.drawable.baseline_account_circle_24)
                        .into(binding.imgProfile)
                    binding.apply {
                        tvHallo.text = getString(R.string.home_greeting,profileData.guardianName)
                        shimmerLayout.stopShimmer()
                        realLayout.visibility = View.VISIBLE
                        shimmerLayout.visibility = View.GONE
                    }
                }
            }
            result.onFailure { exception ->
                Toast.makeText(requireContext(), exception.message, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.logoutResult.observe(viewLifecycleOwner) { result ->
            result.onSuccess { response ->
                Toast.makeText(requireContext(), "Logout berhasil!", Toast.LENGTH_SHORT).show()

                val intent = Intent(requireContext(), BoardingActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            result.onFailure { e ->
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}