package com.puspa.puspamobile.ui.submenu.managechild.action

import android.os.Bundle
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.puspa.puspamobile.data.Injection
import com.puspa.puspamobile.data.remote.response.UpdateChildRequest
import com.puspa.puspamobile.databinding.ActivityUpdateChildBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UpdateChildActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateChildBinding
    private lateinit var viewModel: UpdateChildViewModel

    private var childId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUpdateChildBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModelFactory = Injection.provideViewModelFactory(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[UpdateChildViewModel::class.java]

        childId = intent.getStringExtra(EXTRA_CHILD_ID).toString()

        setupImeOption()
        setInputField()
        setupObserver()
        setupAction()
        viewModel.getChildDetail(childId)
    }

    private fun setupImeOption() {
        fun setNextListener(current: TextInputEditText, next: View) {
            current.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_NEXT ||
                    (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)
                ) {
                    next.requestFocus()
                    if (next.id == binding.etChildBirthDate.id) {
                        next.performClick()
                    }
                    true
                } else false
            }
        }

        fun setNextListenerAuto(current: AutoCompleteTextView, next: View) {
            current.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_NEXT ||
                    (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)
                ) {
                    next.requestFocus()
                    if (next.id == binding.etChildBirthDate.id) {
                        next.performClick()
                    }
                    true
                } else false
            }

            current.setOnItemClickListener { _, _, _, _ ->
                next.requestFocus()
                if (next.id == binding.etChildBirthDate.id) {
                    next.performClick()
                }
            }

            current.setOnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                    next.requestFocus()
                    if (next.id == binding.etChildBirthDate.id) next.performClick()
                    true
                } else false
            }
        }

        setNextListener(binding.etChildName, binding.etChildBirthPlace)
        setNextListener(binding.etChildBirthPlace, binding.etChildGender)
        setNextListenerAuto(binding.etChildGender, binding.etChildBirthDate)
        setNextListener(binding.etChildBirthDate, binding.etChildReligion)
        setNextListener(binding.etChildReligion, binding.etChildSchool)
        setNextListener(binding.etChildSchool, binding.etChildAddress)

        binding.etChildAddress.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                (v is TextInputEditText && actionId == EditorInfo.IME_ACTION_UNSPECIFIED)
            ) {
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
                binding.btnSave.performClick()
                true
            } else false
        }

        binding.etChildBirthDate.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) v.performClick()
        }
    }

    private fun setInputField() {
        val genderOptions = listOf("laki-laki", "perempuan")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, genderOptions)
        binding.etChildGender.setAdapter(adapter)
        binding.etChildGender.threshold = 0
    }

    private fun setupObserver() {
        viewModel.isLoading.observe(this) { isLoading ->
            binding.btnSave.isEnabled = !isLoading
            binding.btnSave.text = if (isLoading) "Loading..." else "Simpan Perubahan"
        }

        viewModel.childDetailResult.observe(this) { result ->
            result.onSuccess { response ->
                val data = response.data ?: return@onSuccess
                val splitBirthInfo = data.childBirthInfo?.split(",", limit = 2)
                val childBirthPlace = splitBirthInfo?.getOrNull(0)?.trim()
                val childBirthDateRaw = splitBirthInfo?.getOrNull(1)?.trim()
                val childBirthDate = formatIndoToShortCompat(childBirthDateRaw) ?: childBirthDateRaw
                binding.apply {
                    etChildName.setText(data.childName ?: "")
                    etChildBirthPlace.setText(childBirthPlace ?: "")
                    etChildGender.setText(data.childGender ?: "", false)
                    etChildBirthDate.setText(childBirthDate ?: "")
                    etChildReligion.setText(data.childReligion ?: "")
                    etChildSchool.setText(data.childSchool ?: "")
                    etChildAddress.setText(data.childAddress ?: "")
                }
            }.onFailure { e ->
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.updateChildResult.observe(this) { result ->
            result.onSuccess { response ->
                Toast.makeText(this, "Data anak berhasil diperbarui", Toast.LENGTH_SHORT).show()
                setResult(RESULT_OK)
                finish()
            }.onFailure { e ->
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupAction() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.etChildBirthDate.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Pilih Tanggal Lahir")
                .build()

            datePicker.addOnPositiveButtonClickListener { selection ->
                val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                val date = dateFormat.format(Date(selection))
                binding.etChildBirthDate.setText(date)
            }
            datePicker.show(supportFragmentManager, "datePicker")
        }

        binding.etChildGender.setOnClickListener {
            binding.etChildGender.clearListSelection()
            binding.etChildGender.showDropDown()
        }

        binding.etChildGender.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) binding.etChildGender.showDropDown()
        }

        binding.btnSave.setOnClickListener {
            val childName = binding.etChildName.text.toString().trim()
            val childBirthPlace = binding.etChildBirthPlace.text.toString().trim()
            val childGender = binding.etChildGender.text.toString().trim()
            val childBirthDate = binding.etChildBirthDate.text.toString().trim()
            val childReligion = binding.etChildReligion.text.toString().trim()
            val childSchool = binding.etChildSchool.text.toString().trim()
            val childAddress = binding.etChildAddress.text.toString().trim()

            if (childName.isEmpty() || childBirthPlace.isEmpty() || childGender.isEmpty() || childBirthDate.isEmpty() ||
                childReligion.isEmpty() || childSchool.isEmpty() || childAddress.isEmpty()
            ) {
                Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val updateRequest = UpdateChildRequest (
                childName = childName,
                childBirthPlace = childBirthPlace,
                childGender = childGender,
                childBirthDate = childBirthDate,
                childReligion = childReligion,
                childSchool = childSchool,
                childAddress = childAddress
            )

            viewModel.updateChild(childId, updateRequest)
        }
    }

    private fun formatIndoToShortCompat(birthDateIndo: String?): String? {
        if (birthDateIndo.isNullOrBlank()) return null
        return try {
            val inFmt = SimpleDateFormat("d MMMM yyyy", Locale("id"))
            val outFmt = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val date: Date = inFmt.parse(birthDateIndo)
            outFmt.format(date)
        } catch (e: Exception) {
            null
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            currentFocus!!.clearFocus()
        }
        return super.dispatchTouchEvent(ev)
    }

    companion object {
        const val EXTRA_CHILD_ID = "extra_child_id"
    }
}