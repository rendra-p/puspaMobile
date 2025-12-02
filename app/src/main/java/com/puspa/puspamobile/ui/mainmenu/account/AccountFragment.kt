package com.puspa.puspamobile.ui.mainmenu.account

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.puspa.puspamobile.R
import com.puspa.puspamobile.data.Injection
import com.puspa.puspamobile.data.remote.response.ChangePasswordRequest
import com.puspa.puspamobile.databinding.FragmentAccountBinding
import com.puspa.puspamobile.ui.auth.BoardingActivity
import com.puspa.puspamobile.ui.submenu.editprofile.EditProfileActivity

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AccountViewModel

    private val editProfileLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) (
            viewModel.getProfile()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)

        val viewModelFactory = Injection.provideViewModelFactory(requireContext())
        viewModel = ViewModelProvider(this, viewModelFactory)[AccountViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.shimmerLayout.startShimmer()

        viewModel.getProfile()
        setAction()
        setObserver()
    }

    private fun setAction() {
        binding.btnUpdateProfile.setOnClickListener {
            val intent = Intent(requireContext(), EditProfileActivity::class.java)
            editProfileLauncher.launch(intent)
        }
        binding.btnChangePassword.setOnClickListener {
            showChangePasswordDialog()
        }
        binding.btnLogout.setOnClickListener {
            viewModel.logoutUser()
        }
    }

    private fun setObserver() {
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
                        tvName.text = profileData.guardianName
                        tvName.text = profileData.guardianName
                        tvNumber.text = profileData.guardianPhone
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
        viewModel.changePasswordResult.observe(viewLifecycleOwner) { result ->
            result.onSuccess { response ->
                Toast.makeText(requireContext(), "Password berhasil diubah!", Toast.LENGTH_SHORT).show()
            }
            result.onFailure { e ->
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun showChangePasswordDialog() {
        val context = requireContext()
        val linearLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            val padding = (16 * resources.displayMetrics.density).toInt()
            setPadding(padding, padding, padding, padding)
        }

        // Input untuk Password Saat Ini
        val currentPasswordInput = EditText(context).apply {
            hint = "Password Saat Ini"
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
        val currentPasswordLayout = TextInputLayout(context).apply {
            addView(currentPasswordInput)
        }

        // Input untuk Password Baru
        val newPasswordInput = EditText(context).apply {
            hint = "Password Baru"
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
        val newPasswordLayout = TextInputLayout(context).apply {
            addView(newPasswordInput)
        }

        // Input untuk Konfirmasi Password Baru
        val confirmPasswordInput = EditText(context).apply {
            hint = "Konfirmasi Password Baru"
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
        val confirmPasswordLayout = TextInputLayout(context).apply {
            addView(confirmPasswordInput)
        }

        val validateNewPassword = {
            val password = newPasswordInput.text.toString()
            when {
                password.length < 8 -> {
                    newPasswordLayout.error = "Minimal 8 karakter"
                    false
                }
                !password.any { it.isUpperCase() } -> {
                    newPasswordLayout.error = "Harus ada huruf besar"
                    false
                }
                !password.any { it.isDigit() } -> {
                    newPasswordLayout.error = "Harus ada angka"
                    false
                }
                !password.any { !it.isLetterOrDigit() } -> {
                    newPasswordLayout.error = "Harus ada simbol"
                    false
                }
                else -> {
                    newPasswordLayout.error = null
                    newPasswordLayout.isErrorEnabled = false
                    true
                }
            }
        }

        newPasswordInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateNewPassword()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        linearLayout.addView(currentPasswordLayout)
        linearLayout.addView(newPasswordLayout)
        linearLayout.addView(confirmPasswordLayout)

        MaterialAlertDialogBuilder(context)
            .setTitle("Ubah Password")
            .setView(linearLayout)
            .setNegativeButton("Batal", null)
            .setPositiveButton("Simpan") { _, _ ->
                val currentPassword = currentPasswordInput.text.toString()
                val newPassword = newPasswordInput.text.toString()
                val confirmPassword = confirmPasswordInput.text.toString()

                if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(context, "Semua field harus diisi!", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                if (!validateNewPassword()) {
                    Toast.makeText(context, "Password baru tidak memenuhi syarat.", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                if (newPassword != confirmPassword) {
                    confirmPasswordLayout.error = "Kata sandi baru tidak cocok!"
                    Toast.makeText(context, "Konfirmasi password tidak cocok!", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                } else {
                    confirmPasswordLayout.error = null
                    confirmPasswordLayout.isErrorEnabled = false
                }

                val request = ChangePasswordRequest(
                    currentPassword = currentPassword,
                    password = newPassword,
                    passwordConfirmation = confirmPassword
                )
                viewModel.changePassword(request)
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}