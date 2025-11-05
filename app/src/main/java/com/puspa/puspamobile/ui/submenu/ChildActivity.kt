package com.puspa.puspamobile.ui.submenu

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.puspa.puspamobile.R
import com.puspa.puspamobile.data.Injection
import com.puspa.puspamobile.databinding.ActivityChildBinding

class ChildActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChildBinding
    private lateinit var viewmodel: ChildViewModel
    private lateinit var childAdapter: ChildAdapter

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