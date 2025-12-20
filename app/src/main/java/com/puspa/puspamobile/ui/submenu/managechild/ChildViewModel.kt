package com.puspa.puspamobile.ui.submenu.managechild

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puspa.puspamobile.data.DataRepository
import com.puspa.puspamobile.data.remote.response.ChildDetailResponse
import com.puspa.puspamobile.data.remote.response.ChildResponse
import kotlinx.coroutines.launch

class ChildViewModel(
    private val repository: DataRepository
) : ViewModel() {
    private val _childResult = MutableLiveData<Result<ChildResponse>>()
    val childResult: LiveData<Result<ChildResponse>> = _childResult

    private val _childDetailResult = MutableLiveData<Result<ChildDetailResponse>>()
    val childDetailResult: LiveData<Result<ChildDetailResponse>> = _childDetailResult

    private val _deleteChildResult = MutableLiveData<Result<Void?>>()
    val deleteChildResult: LiveData<Result<Void?>> = _deleteChildResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getChild() {
        viewModelScope.launch {
            try {
                val result = repository.getChild()
                _childResult.value = result
            } catch (e: Exception) {
                _childResult.value = Result.failure(e)
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

    fun deleteChild(childId: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val result = repository.deleteChild(childId)
                _deleteChildResult.value = result
            } catch (e: Exception) {
                _deleteChildResult.value = Result.failure(e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}