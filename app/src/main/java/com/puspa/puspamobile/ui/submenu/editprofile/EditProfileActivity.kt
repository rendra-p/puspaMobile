package com.puspa.puspamobile.ui.submenu.editprofile

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.datepicker.MaterialDatePicker
import com.puspa.puspamobile.data.Injection
import com.puspa.puspamobile.data.remote.response.UpdateProfileRequest
import com.puspa.puspamobile.databinding.ActivityEditProfileBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var viewModel: EditProfileViewModel

    private var guardianId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModelFactory = Injection.provideViewModelFactory(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[EditProfileViewModel::class.java]

        setupObservers()
        setupActions()
        viewModel.getProfile()
    }

    private fun setupObservers() {
        viewModel.profileResult.observe(this) { result ->
            result.onSuccess { response ->
                val data = response.data ?: return@onSuccess
                guardianId = data.guardianId.toString()
                binding.apply {
                    etGuardianName.setText(data.guardianName ?: "")
                    etRelationshipWithChild.setText(data.relationshipWithChild ?: "")
                    etBirthDate.setText(data.guardianBirthDate ?: "")
                    etGuardianPhone.setText(data.guardianPhone ?: "")
                    etEmail.setText(data.email ?: "")
                    etGuardianOccupation.setText(data.guardianOccupation ?: "")
                }
            }.onFailure { e ->
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.updateProfileResult.observe(this) { result ->
            result.onSuccess { response ->
                Toast.makeText(this, "Profil berhasil diperbarui", Toast.LENGTH_SHORT).show()
                finish()
            }.onFailure { e ->
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupActions() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.etBirthDate.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Pilih Tanggal Lahir")
                .build()

            datePicker.addOnPositiveButtonClickListener { selection ->
                val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                val date = dateFormat.format(Date(selection))
                binding.etBirthDate.setText(date)
            }
            datePicker.show(supportFragmentManager, "datePicker")
        }

        binding.btnSave.setOnClickListener {
            val name = binding.etGuardianName.text.toString().trim()
            val relation = binding.etRelationshipWithChild.text.toString().trim()
            val birthDate = binding.etBirthDate.text.toString().trim()
            val phone = binding.etGuardianPhone.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val occupation = binding.etGuardianOccupation.text.toString().trim()

            if (name.isEmpty() || relation.isEmpty() || birthDate.isEmpty() ||
                phone.isEmpty() || email.isEmpty() || occupation.isEmpty()
            ) {
                Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val updateRequest = UpdateProfileRequest(
                guardian_name = name,
                relationship_with_child = relation,
                guardian_birth_date = birthDate,
                guardian_phone = phone,
                email = email,
                guardian_occupation = occupation
            )

            viewModel.updateProfile(guardianId, updateRequest)
        }
    }
}
