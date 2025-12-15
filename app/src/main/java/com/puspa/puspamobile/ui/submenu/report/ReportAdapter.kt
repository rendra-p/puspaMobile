package com.puspa.puspamobile.ui.submenu.report

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.puspa.puspamobile.data.remote.response.AssessmentsData
import com.puspa.puspamobile.databinding.ItemReportBinding

class ReportAdapter(
    private var assessmentsList: List<AssessmentsData> = emptyList(),
    private var onItemClick: (AssessmentsData) -> Unit
) : RecyclerView.Adapter<ReportAdapter.ReportViewHolder>() {

    inner class ReportViewHolder(private val binding: ItemReportBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(assessments: AssessmentsData) {
            with(binding) {
                tvReportName.text = assessments.childName ?: "-"
                tvReportBirthInfo.text = assessments.childBirthInfo ?: "-"
                tvReportSchedule.text = assessments.scheduledDate ?: "-"

                if (assessments.report?.available == true) {
                    tvReportNoFile.visibility = View.GONE
                    iconDownload.visibility = View.VISIBLE
                } else {
                    tvReportNoFile.visibility = View.VISIBLE
                    iconDownload.visibility = View.GONE
                }


                root.setOnClickListener {
                    onItemClick(assessments)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val binding = ItemReportBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReportViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        holder.bind(assessmentsList[position])
    }

    override fun getItemCount(): Int = assessmentsList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newList: List<AssessmentsData?>?) {
        assessmentsList = newList?.filterNotNull() ?: emptyList()
        notifyDataSetChanged()
    }
}