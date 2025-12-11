package com.puspa.puspamobile.ui.submenu.managechild.action

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puspa.puspamobile.data.DataRepository
import com.puspa.puspamobile.data.remote.response.ChildDetailResponse
import com.puspa.puspamobile.data.remote.response.UpdateChildRequest
import kotlinx.coroutines.launch

class UpdateChildViewModel(
    private val repository: DataRepository
) : ViewModel() {
    private val _updateChildResult = MutableLiveData<Result<Void?>>()
    val updateChildResult: LiveData<Result<Void?>> = _updateChildResult

    private val _childDetailResult = MutableLiveData<Result<ChildDetailResponse>>()
    val childDetailResult: LiveData<Result<ChildDetailResponse>> = _childDetailResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun updateChild(childId: String, updateChildRequest: UpdateChildRequest){
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val result = repository.updateChild(childId, updateChildRequest)
                _updateChildResult.value = result
            } catch (e: Exception) {
                _updateChildResult.value = Result.failure(e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getChildDetail(childId: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val result = repository.getChildDetail(childId)
                _childDetailResult.value = result
            } catch (e: Exception) {
                _childDetailResult.value = Result.failure(e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}