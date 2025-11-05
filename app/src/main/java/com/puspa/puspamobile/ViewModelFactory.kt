package com.puspa.puspamobile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.puspa.puspamobile.data.DataRepository
import com.puspa.puspamobile.data.local.TokenDataStore
import com.puspa.puspamobile.ui.auth.LoginViewModel
import com.puspa.puspamobile.ui.auth.RegisterViewModel
import com.puspa.puspamobile.ui.mainmenu.AccountViewModel
import com.puspa.puspamobile.ui.mainmenu.HomeViewModel
import com.puspa.puspamobile.ui.submenu.ChildViewModel
import com.puspa.puspamobile.ui.submenu.EditProfileViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val repository: DataRepository,
    private val tokenDataStore: TokenDataStore? = null
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository, tokenDataStore!!) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }
            modelClass.isAssignableFrom(AccountViewModel::class.java) -> {
                AccountViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ChildViewModel::class.java) -> {
                ChildViewModel(repository) as T
            }
            modelClass.isAssignableFrom(EditProfileViewModel::class.java) -> {
                EditProfileViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}