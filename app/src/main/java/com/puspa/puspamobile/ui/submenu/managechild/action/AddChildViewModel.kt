package com.puspa.puspamobile.ui.submenu.managechild.action

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puspa.puspamobile.data.DataRepository
import com.puspa.puspamobile.data.remote.response.AddChildRequest
import kotlinx.coroutines.launch

class AddChildViewModel(
    private val repository: DataRepository
) : ViewModel() {
    private val _addChildResult = MutableLiveData<Result<Void?>>()
    val addChildResult: LiveData<Result<Void?>> = _addChildResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun addChild(addChildRequest: AddChildRequest) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val result = repository.addChild(addChildRequest)
                _addChildResult.value = result
            } catch (e: Exception) {
                _addChildResult.value = Result.failure(e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}