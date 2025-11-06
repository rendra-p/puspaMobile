package com.puspa.puspamobile.ui.submenu.managechild

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.puspa.puspamobile.data.Injection
import com.puspa.puspamobile.databinding.ActivityChildBinding

class ChildActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChildBinding
    private lateinit var viewmodel: ChildViewModel
    private lateinit var childAdapter: ChildAdapter

    private val addChildLauncher = registerForActivityResult(
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
            addChildLauncher.launch(intent)
        }
    }

    private fun setRecyclerView() {
        viewmodel.getChild()
        childAdapter = ChildAdapter()
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
            }
            result.onFailure { exception ->
                Toast.makeText(this, exception.message ?: "Terjadi kesalahan saat memuat data", Toast.LENGTH_SHORT).show()
            }
        }
    }
}