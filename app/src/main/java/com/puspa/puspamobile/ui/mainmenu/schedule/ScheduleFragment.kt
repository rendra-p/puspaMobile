package com.puspa.puspamobile.ui.mainmenu.schedule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.puspa.puspamobile.R
import com.puspa.puspamobile.data.Injection
import com.puspa.puspamobile.databinding.FragmentScheduleBinding

class ScheduleFragment : Fragment() {
    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ScheduleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScheduleBinding.inflate(inflater, container, false)

        val viewModelFactory = Injection.provideViewModelFactory(requireContext())
        viewModel = ViewModelProvider(this, viewModelFactory)[ScheduleViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObserver()
    }

    private fun setObserver() {
        viewModel.getAssessments()
        viewModel.assessmentResult.observe(viewLifecycleOwner) { result ->
            result.onSuccess { response ->
                response.data?.let { assessmentsData ->
                    if (assessmentsData.isEmpty()) {
                        binding.frameAssesments.visibility = View.GONE
                        binding.tvNoAssesments.visibility = View.VISIBLE
                    } else {
                        assessmentsData.firstOrNull()?.let { data ->
                            binding.apply {
                                tvAtasNama.text = getString(R.string.atas_nama_assesments, data.childName)
                                tvSchedule.text = data.scheduledDate
                            }
                        }
                    }
                }
            }
            result.onFailure { exception ->
                Toast.makeText(requireContext(), exception.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}