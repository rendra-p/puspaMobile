package com.puspa.puspamobile.ui.mainmenu.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puspa.puspamobile.data.DataRepository
import com.puspa.puspamobile.data.remote.response.AssessmentsResponse
import kotlinx.coroutines.launch

class ScheduleViewModel(
    private val repository: DataRepository
) : ViewModel() {
    private val _assessmentsResult = MutableLiveData<Result<AssessmentsResponse>>()
    val assessmentResult: LiveData<Result<AssessmentsResponse>> = _assessmentsResult

    fun getAssessments() {
        viewModelScope.launch {
            try {
                val result = repository.getAssessments()
                _assessmentsResult.value = result
            } catch (e: Exception) {
                _assessmentsResult.value = Result.failure(e)
            }
        }
    }
}