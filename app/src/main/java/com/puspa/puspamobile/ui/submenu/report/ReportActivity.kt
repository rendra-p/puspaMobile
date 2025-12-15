package com.puspa.puspamobile.ui.submenu.report

import android.app.DownloadManager
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.puspa.puspamobile.data.Injection
import com.puspa.puspamobile.databinding.ActivityReportBinding
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import com.puspa.puspamobile.data.local.TokenDataStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class ReportActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReportBinding
    private lateinit var viewmodel: ReportViewModel
    private lateinit var reportAdapter: ReportAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModelFactory = Injection.provideViewModelFactory(this)
        viewmodel = ViewModelProvider(this, viewModelFactory)[ReportViewModel::class.java]

        setupAction()
        setupRecycleView()
        setupObserver()
    }

    private fun setupAction() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setupRecycleView() {
        viewmodel.getAssessments()
        reportAdapter = ReportAdapter(
            onItemClick = { assessments ->
                if (assessments.report?.available == true) {
                    lifecycleScope.launch {
                        val token = TokenDataStore
                            .getInstance(this@ReportActivity)
                            .token
                            .firstOrNull()

                        if (token.isNullOrBlank()) {
                            Toast.makeText(
                                this@ReportActivity,
                                "Token tidak tersedia, silakan login ulang",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@launch
                        }

                        downloadFile(
                            url = assessments.report.downloadUrl!!,
                            fileName = "Laporan Asesmen ${assessments.childName}, tanggal ${assessments.scheduledDate}",
                            token = token
                        )
                    }
                } else {
                    Toast.makeText(this, "Tidak Ada File Yang Tersedia", Toast.LENGTH_SHORT).show()
                }
            }
        )
        binding.recyclerviewAssesments.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = reportAdapter
        }
    }

    private fun setupObserver() {
        viewmodel.assessmentResult.observe(this) { result ->
            result.onSuccess { response ->
                val list = response.data?.filterNotNull() ?: emptyList()
                reportAdapter.updateData(list)
            }
            result.onFailure { exception ->
                Toast.makeText(this, exception.message ?: "Terjadi kesalahan saat memuat data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun downloadFile(url: String, fileName: String, token: String) {
        val request = DownloadManager.Request(url.toUri())
            .setTitle(fileName)
            .setDescription("Sedang mengunduh laporan")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)
            .setMimeType("application/pdf")
            .addRequestHeader("Authorization", "Bearer $token")
            .setDestinationInExternalFilesDir(
                this,
                Environment.DIRECTORY_DOWNLOADS,
                "$fileName.pdf"
            )

        val downloadManager =
            getSystemService(DOWNLOAD_SERVICE) as DownloadManager

        downloadManager.enqueue(request)

        Toast.makeText(this, "Download dimulai", Toast.LENGTH_SHORT).show()
    }
}