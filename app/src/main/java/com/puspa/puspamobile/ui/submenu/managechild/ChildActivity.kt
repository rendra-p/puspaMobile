package com.puspa.puspamobile.ui.submenu.managechild

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.puspa.puspamobile.data.Injection
import com.puspa.puspamobile.data.remote.response.ChildDetailData
import com.puspa.puspamobile.databinding.ActivityChildBinding
import com.puspa.puspamobile.ui.submenu.managechild.action.AddChildActivity
import com.puspa.puspamobile.ui.submenu.managechild.action.UpdateChildActivity

class ChildActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChildBinding
    private lateinit var viewmodel: ChildViewModel
    private lateinit var childAdapter: ChildAdapter

    private val childLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            viewmodel.getChild()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityChildBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.shimmerLayout.startShimmer()

        val viewModelFactory = Injection.provideViewModelFactory(this)
        viewmodel = ViewModelProvider(this, viewModelFactory)[ChildViewModel::class]

        setAction()
        setRecyclerView()
        setObserver()
    }

    private fun setAction() {
        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.btnAddChild.setOnClickListener {
            val intent = Intent(this@ChildActivity, AddChildActivity::class.java)
            childLauncher.launch(intent)
        }
    }

    private fun setRecyclerView() {
        viewmodel.getChild()
        childAdapter = ChildAdapter(
            onItemClick = { child ->
                if (!child.childId.isNullOrBlank()) {
                    viewmodel.getChildDetail(child.childId)
                } else {
                    Toast.makeText(this, "ID anak tidak ditemukan", Toast.LENGTH_SHORT).show()
                }
            },
            onEditClick = { child ->
                if (child.childId == null) {
                    Toast.makeText(this, "ID anak tidak ditemukan", Toast.LENGTH_SHORT).show()
                }

                val intent = Intent(this, UpdateChildActivity::class.java).apply {
                    putExtra(UpdateChildActivity.EXTRA_CHILD_ID, child.childId)
                }
                childLauncher.launch(intent)
            }
        )
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = childAdapter
        }
    }

    private fun setObserver() {
        viewmodel.childResult.observe(this) { result ->
            result.onSuccess { response ->
                val list = response.data?.filterNotNull() ?: emptyList()
                childAdapter.updateData(list)
                binding.apply {
                    shimmerLayout.stopShimmer()
                    recyclerView.visibility = View.VISIBLE
                    shimmerLayout.visibility = View.GONE
                }
            }
            result.onFailure { exception ->
                Toast.makeText(this, exception.message ?: "Terjadi kesalahan saat memuat data", Toast.LENGTH_SHORT).show()
            }
        }

        viewmodel.childDetailResult.observe(this) { result ->
            result.onSuccess { response ->
                val detail = response.data
                if (detail != null) {
                    showChildDetailDialog(detail)
                } else {
                    Toast.makeText(this, "Data detail kosong", Toast.LENGTH_SHORT).show()
                }
            }
            result.onFailure { exception ->
                Toast.makeText(this, exception.message ?: "Terjadi kesalahan saat memuat detail anak", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showChildDetailDialog(detail: ChildDetailData) {
        val message = """
        Nama     : ${detail.childName ?: "-"}
        Tgl Lahir: ${detail.childBirthInfo ?: "-"}
        Usia     : ${detail.childAge ?: "-"}
        Gender   : ${detail.childGender ?: "-"}
        Agama    : ${detail.childReligion ?: "-"}
        Sekolah  : ${detail.childSchool ?: "-"}
        Alamat   : ${detail.childAddress ?: "-"}
    """.trimIndent()

        val textView = TextView(this).apply {
            text = message
            setPadding(40, 20, 40, 20)
            typeface = Typeface.MONOSPACE
            textSize = 16f
        }

        MaterialAlertDialogBuilder(this)
            .setTitle("Detail Anak")
            .setView(textView)
            .setPositiveButton("OK", null)
            .show()
    }

}