package com.puspa.puspamobile.ui.submenu.editprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puspa.puspamobile.data.DataRepository
import com.puspa.puspamobile.data.remote.response.ProfileResponse
import com.puspa.puspamobile.data.remote.response.UpdateProfileRequest
import kotlinx.coroutines.launch

class EditProfileViewModel (
    private val repository: DataRepository
) : ViewModel() {
    private val _profileResult = MutableLiveData<Result<ProfileResponse>>()
    val profileResult: LiveData<Result<ProfileResponse>> = _profileResult

    private val _updateProfileResult = MutableLiveData<Result<Void?>>()
    val updateProfileResult: LiveData<Result<Void?>> = _updateProfileResult

    fun getProfile() {
        viewModelScope.launch {
            try {
                val result = repository.getProfile()
                _profileResult.value = result
            } catch (e: Exception) {
                _profileResult.value = Result.failure(e)
            }
        }
    }

    fun updateProfile(guardianId: String, updateProfileRequest: UpdateProfileRequest) {
        viewModelScope.launch {
            try {
                val result = repository.updateProfile(guardianId, updateProfileRequest)
                _updateProfileResult.value = result
            } catch (e: Exception) {
                _updateProfileResult.value = Result.failure(e)
            }
        }
    }
}