package com.example.testtask.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sdk.components.SingleLiveEvent
import com.example.testtask.interactor.EmployeeInteractor
import kotlinx.coroutines.*
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(private val employeeInteractor: EmployeeInteractor) : ViewModel() {
    val loaderLiveData = MutableLiveData<Boolean>()
    val dataReadyLiveData = SingleLiveEvent<Unit>()

    init {
        loaderLiveData.value = true
        GlobalScope.launch(Dispatchers.Main) {
            employeeInteractor.getEmployees()
            loaderLiveData.value = false
            dataReadyLiveData.call()

        }
    }
}
