package com.puspa.puspamobile.ui.submenu.managechild

import android.os.Bundle
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.puspa.puspamobile.data.Injection
import com.puspa.puspamobile.data.remote.response.AddChildRequest
import com.puspa.puspamobile.databinding.ActivityAddChildBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddChildActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddChildBinding
    private lateinit var viewModel: AddChildViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddChildBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModelFactory = Injection.provideViewModelFactory(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[AddChildViewModel::class.java]

        setImeOption()
        setInputField()
        setAction()
        setObserver()
    }

    private fun setImeOption() {
        fun setNextListener(current: TextInputEditText, next: View) {
            current.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_NEXT ||
                    (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)) {

                    if (next is android.widget.AutoCompleteTextView) {
                        next.requestFocus()
                        next.showDropDown()
                    } else {
                        next.requestFocus()
                        if (next.id == binding.etChildBirthDate.id) next.performClick()
                    }
                    true
                } else false
            }
        }
        setNextListener(binding.etChildName, binding.etChildGender)

        binding.etChildGender.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_NEXT ||
                (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)) {
                binding.etChildBirthPlace.requestFocus()
                true
            } else false
        }
        setNextListener(binding.etChildBirthDate, binding.etChildSchool)

        binding.etChildBirthDate.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) v.performClick()
        }

        binding.etChildGender.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus && v is android.widget.AutoCompleteTextView) v.showDropDown()
        }
    }

    private fun setInputField() {
        val genderOptions = listOf("laki-laki", "perempuan")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, genderOptions)
        binding.etChildGender.setAdapter(adapter)
    }

    private fun setAction() {
        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.etChildGender.setOnClickListener {
            binding.etChildGender.showDropDown()
        }

        binding.etChildGender.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) binding.etChildGender.showDropDown()
        }

        binding.etChildBirthDate.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Pilih Tanggal Lahir")
                .build()

            datePicker.addOnPositiveButtonClickListener { selection ->
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val date = dateFormat.format(Date(selection))
                binding.etChildBirthDate.setText(date)
            }
            datePicker.show(supportFragmentManager,"datePicker")
        }

        binding.btnSave.setOnClickListener {
            val name = binding.etChildName.text.toString().trim()
            val gender = binding.etChildGender.text.toString().trim()
            val birthPlace = binding.etChildBirthPlace.text.toString().trim()
            val birthDate = binding.etChildBirthDate.text.toString().trim()
            val school = binding.etChildSchool.text.toString().trim()
            val address = binding.etChildAddress.text.toString().trim()
            val complaint = binding.etChildComplaint.text.toString().trim()

            val selectedServices = mutableListOf<String>()
            if (binding.cbAsesmenTumbuhKembang.isChecked) selectedServices.add("Asesmen Tumbuh Kembang")
            if (binding.cbAsesmenTerpadu.isChecked) selectedServices.add("Asesmen Terpadu")
            if (binding.cbKonsultasiDokter.isChecked) selectedServices.add("Konsultasi Dokter")
            if (binding.cbKonsultasiPsikolog.isChecked) selectedServices.add("Konsultasi Psikolog")
            if (binding.cbKonsultasiKeluarga.isChecked) selectedServices.add("Konsultasi Keluarga")
            if (binding.cbTestPsikolog.isChecked) selectedServices.add("Test Psikolog")
            if (binding.cbLayananMinatBakat.isChecked) selectedServices.add("Layanan Minat Bakat")
            if (binding.cbDaycare.isChecked) selectedServices.add("Daycare")
            if (binding.cbHomeCare.isChecked) selectedServices.add("Home Care")
            if (binding.cbHydrotherapy.isChecked) selectedServices.add("Hydrotherapy")
            if (binding.cbBabySpa.isChecked) selectedServices.add("Baby Spa")

            if (name.isEmpty() || gender.isEmpty() || birthPlace.isEmpty() ||
                birthDate.isEmpty() || school.isEmpty() || address.isEmpty() || complaint.isEmpty()
            ) {
                Toast.makeText(this, "Harap isi semua field", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (selectedServices.isEmpty()) {
                Toast.makeText(this, "Pilih minimal satu layanan", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val addChildRequest = AddChildRequest(
                childName = name,
                childGender = gender,
                childBirthPlace = birthPlace,
                childBirthDate = birthDate,
                childSchool = school,
                childAddress = address,
                childComplaint = complaint,
                childServiceChoice = selectedServices.joinToString(", ")
            )

            viewModel.addChild(addChildRequest)
        }
    }

    private fun setObserver() {
        viewModel.isLoading.observe(this) { isLoading ->
            binding.btnSave.isEnabled = !isLoading
            binding.btnSave.text = if (isLoading) "Loading..." else "Simpan Data Anak"
        }
        viewModel.addChildResult.observe(this) { result ->
            result.onSuccess {
                Toast.makeText(this, "Data anak berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                setResult(RESULT_OK)
                finish()
            }
            result.onFailure { e ->
                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
            }
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
}