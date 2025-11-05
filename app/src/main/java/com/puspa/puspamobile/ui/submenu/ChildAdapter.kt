package com.puspa.puspamobile.ui.submenu

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.puspa.puspamobile.data.remote.response.ChildData
import com.puspa.puspamobile.databinding.ItemChildBinding

class ChildAdapter(
    private var childList: List<ChildData> = emptyList()
) : RecyclerView.Adapter<ChildAdapter.ChildViewHolder>() {

    inner class ChildViewHolder(private val binding: ItemChildBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(child: ChildData) {
            with(binding) {
                tvName.text = child.childName ?: "-"
                tvGender.text = "(${child.childGender ?: "-"})"
                tvBirthDate.text = "Tanggal Lahir: ${child.childBirthDate ?: "-"}"
                tvAge.text = "Usia: ${child.childAge ?: "-"}"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val binding = ItemChildBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChildViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        holder.bind(childList[position])
    }

    override fun getItemCount(): Int = childList.size

    fun updateData(newList: List<ChildData?>?) {
        childList = newList?.filterNotNull() ?: emptyList()
        notifyDataSetChanged()
    }
}