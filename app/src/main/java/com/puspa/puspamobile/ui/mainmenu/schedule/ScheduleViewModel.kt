package com.puspa.puspamobile.ui.mainmenu.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puspa.puspamobile.data.DataRepository
import com.puspa.puspamobile.data.remote.response.AssesmentsResponse
import kotlinx.coroutines.launch

class ScheduleViewModel(
    private val repository: DataRepository
) : ViewModel() {
    private val _assesmentsResult = MutableLiveData<Result<AssesmentsResponse>>()
    val assesmenResult: LiveData<Result<AssesmentsResponse>> = _assesmentsResult

    fun getAssesments() {
        viewModelScope.launch {
            try {
                val result = repository.getAssesments()
                _assesmentsResult.value = result
            } catch (e: Exception) {
                _assesmentsResult.value = Result.failure(e)
            }
        }
    }
}