package com.puspa.puspamobile.ui.submenu.editprofile

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.puspa.puspamobile.R
import com.puspa.puspamobile.data.Injection
import com.puspa.puspamobile.data.remote.response.UpdateProfileRequest
import com.puspa.puspamobile.databinding.ActivityEditProfileBinding
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var viewModel: EditProfileViewModel

    private var guardianId: String = ""

    private var selectedImageUri: Uri? = null

    private val imagePickerLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
            Glide.with(this)
                .load(it)
                .placeholder(R.drawable.baseline_account_circle_24)
                .into(binding.imgProfile)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModelFactory = Injection.provideViewModelFactory(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[EditProfileViewModel::class.java]

        setupImeOption()
        setupObservers()
        setupActions()
        viewModel.getProfile()
    }

    private fun setupImeOption() {
        fun setNextListener(current: TextInputEditText, next: View) {
            current.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_NEXT ||
                    (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)) {
                    next.requestFocus()
                    if (next.id == binding.etBirthDate.id) {
                        next.performClick()
                    }
                    true
                } else false
            }
        }
        setNextListener(binding.etGuardianName, binding.etRelationshipWithChild)
        setNextListener(binding.etRelationshipWithChild, binding.etBirthDate)
        setNextListener(binding.etGuardianPhone, binding.etEmail)
        setNextListener(binding.etEmail, binding.etGuardianOccupation)

        binding.etGuardianOccupation.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)

                binding.btnSave.performClick()
                true
            } else false
        }

        binding.etBirthDate.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                v.performClick()
            }
        }
    }

    private fun setupObservers() {
        viewModel.isLoading.observe(this) { isLoading ->
            binding.btnSave.isEnabled = !isLoading
            binding.btnSave.text = if (isLoading) "Loading..." else "Simpan Perubahan"
        }
        viewModel.profileResult.observe(this) { result ->
            result.onSuccess { response ->
                val data = response.data ?: return@onSuccess
                guardianId = data.guardianId.toString()
                val imageUrl = "https://puspa.sinus.ac.id" + data.profilePicture
                Glide.with(this)
                    .load(imageUrl)
                    .placeholder(R.drawable.baseline_account_circle_24)
                    .error(R.drawable.baseline_account_circle_24)
                    .into(binding.imgProfile)
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
                setResult(RESULT_OK)
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

        binding.btnSelectImage.setOnClickListener {
            imagePickerLauncher.launch("image/*")
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
                guardianName = name,
                relationshipWithChild = relation,
                guardianBirthDate = birthDate,
                guardianPhone = phone,
                email = email,
                guardianOccupation = occupation
            )

            if (selectedImageUri != null) {
                val file = uriToFile(selectedImageUri!!, this)
                viewModel.updateProfileWithImage(guardianId, updateRequest, file)
            } else {
                viewModel.updateProfile(guardianId, updateRequest)
            }

        }
    }

    private fun uriToFile(uri: Uri, context: Context): File {
        val contentResolver = context.contentResolver
        val tempFile = File.createTempFile("profile_", ".jpg", context.cacheDir)
        val inputStream = contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(tempFile)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()
        return tempFile
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            currentFocus!!.clearFocus()
        }
        return super.dispatchTouchEvent(ev)
    }
}
