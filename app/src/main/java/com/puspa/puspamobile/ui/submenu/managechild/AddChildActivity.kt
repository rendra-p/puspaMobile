package com.puspa.puspamobile.ui.submenu.managechild

import android.app.Activity
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.puspa.puspamobile.R
import com.puspa.puspamobile.data.Injection
import com.puspa.puspamobile.data.remote.response.AddChildRequest
import com.puspa.puspamobile.databinding.ActivityAddChildBinding
import java.text.SimpleDateFormat
import java.util.Locale

class AddChildActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddChildBinding
    private lateinit var viewModel: AddChildViewModel
    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddChildBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModelFactory = Injection.provideViewModelFactory(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[AddChildViewModel::class.java]

        setInputField()
        setAction()
        setObserver()
    }

    private fun setInputField() {
        val genderOptions = listOf("laki-laki", "perempuan")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, genderOptions)
        binding.etChildGender.setAdapter(adapter)
    }

    private fun setAction() {
        binding.etChildGender.setOnClickListener {
            binding.etChildGender.showDropDown()
        }
        binding.etChildGender.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) binding.etChildGender.showDropDown()
        }

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        binding.etChildBirthDate.setOnClickListener {
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, { _, y, m, d ->
                calendar.set(y, m, d)
                binding.etChildBirthDate.setText(dateFormat.format(calendar.time))
            }, year, month, day).show()
        }

        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
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

            // Validasi
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
        viewModel.addChildResult.observe(this) { result ->
            result.onSuccess {
                Toast.makeText(this, "Data anak berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                setResult(Activity.RESULT_OK)
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