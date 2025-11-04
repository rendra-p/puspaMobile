package com.puspa.puspamobile.ui.mainmenu

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.puspa.puspamobile.R
import com.puspa.puspamobile.data.Injection
import com.puspa.puspamobile.databinding.FragmentHomeBinding
import com.puspa.puspamobile.ui.auth.BoardingActivity

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

        setAction()
        setObserver()
    }

    private fun setAction() {
        binding.btnLogout.setOnClickListener {
            viewModel.logoutUser()
        }
    }

    private fun setObserver() {
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