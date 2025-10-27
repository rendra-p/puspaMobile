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
import com.puspa.puspamobile.ViewModelFactory
import com.puspa.puspamobile.data.Injection
import com.puspa.puspamobile.databinding.FragmentAccountBinding
import com.puspa.puspamobile.ui.auth.BoardingActivity
import com.puspa.puspamobile.ui.auth.LoginActivity

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = Injection.provideViewModelFactory(requireContext())
        viewModel = ViewModelProvider(this, factory)[AccountViewModel::class.java]

        viewModel.getProfile()

        viewModel.profileResult.observe(viewLifecycleOwner) { result ->
            val profileResult = result.getOrNull()
            profileResult?.let { response ->
                response.data?.let { profileData ->
                    binding.tvName.text = profileData.guardianName
                    binding.tvNumber.text = profileData.guardianPhone
                }
            }
        }

        binding.btnLogout.setOnClickListener {
            viewModel.logoutUser()
        }

        viewModel.logoutResult.observe(viewLifecycleOwner) { result ->
            result.onSuccess { response ->
                Toast.makeText(requireContext(), "Logout berhasil!", Toast.LENGTH_SHORT).show()

                val intent = Intent(requireContext(), BoardingActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }

            result.onFailure { e ->
                Toast.makeText(requireContext(), "Logout gagal: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}