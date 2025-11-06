package com.puspa.puspamobile.ui.submenu.managechild

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puspa.puspamobile.data.DataRepository
import com.puspa.puspamobile.data.remote.response.ChildResponse
import kotlinx.coroutines.launch

class ChildViewModel(
    private val repository: DataRepository
) : ViewModel() {
    private val _childResult = MutableLiveData<Result<ChildResponse>>()
    val childResult: LiveData<Result<ChildResponse>> = _childResult

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
}