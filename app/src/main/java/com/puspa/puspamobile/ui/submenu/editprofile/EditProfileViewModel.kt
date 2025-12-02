package com.puspa.puspamobile.ui.submenu.editprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puspa.puspamobile.data.DataRepository
import com.puspa.puspamobile.data.remote.response.ProfileResponse
import com.puspa.puspamobile.data.remote.response.UpdateProfileRequest
import kotlinx.coroutines.launch
import java.io.File

class EditProfileViewModel (
    private val repository: DataRepository
) : ViewModel() {
    private val _profileResult = MutableLiveData<Result<ProfileResponse>>()
    val profileResult: LiveData<Result<ProfileResponse>> = _profileResult

    private val _updateProfileResult = MutableLiveData<Result<Void?>>()
    val updateProfileResult: LiveData<Result<Void?>> = _updateProfileResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getProfile() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val result = repository.getProfile()
                _profileResult.value = result
            } catch (e: Exception) {
                _profileResult.value = Result.failure(e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateProfile(guardianId: String, updateProfileRequest: UpdateProfileRequest) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val result = repository.updateProfile(guardianId, updateProfileRequest)
                _updateProfileResult.value = result
            } catch (e: Exception) {
                _updateProfileResult.value = Result.failure(e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateProfileWithImage(guardianId: String, updateProfileRequest: UpdateProfileRequest, imageFile: File) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val result = repository.updateProfileWithImage(guardianId, updateProfileRequest, imageFile)
                _updateProfileResult.value = result
            } catch (e: Exception) {
                _updateProfileResult.value = Result.failure(e)
            } finally {
                _isLoading.value = false
            }
        }
    }

}