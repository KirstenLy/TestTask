package com.example.testtask.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testtask.domain.interactor.employee.EmployeeInteractorImpl
import com.example.testtask.domain.interactor.employee.EmployeeInteractorResult
import com.example.testtask.domain.interactor.speciality.SpecialityInteractorImpl
import com.example.testtask.domain.model.Employee
import com.example.testtask.domain.model.Speciality
import javax.inject.Inject

class SharedViewModel @Inject constructor(
        private var employeeInteractor: EmployeeInteractorImpl,
        private var specialityInteractor: SpecialityInteractorImpl
) : ViewModel() {

    val employeeList = MutableLiveData<ArrayList<Employee>>()
    val specialtyList = MutableLiveData<ArrayList<Speciality>>()
    val selectedEmployee = MutableLiveData<Employee>()

    suspend fun init() {
        val employeeListResult = employeeInteractor.getEmployees()
        when (employeeListResult) {
            is EmployeeInteractorResult.Data -> {
                employeeList.value = employeeListResult.employees as ArrayList<Employee>
                specialtyList.value = specialityInteractor.getSpecialities()
                selectedEmployee.value = employeeInteractor.getSelectedEmployee()
            }
        }
    }

    fun setSelectedEmployee(employee: Employee) {
        selectedEmployee.value = employee
        employeeInteractor.setSelectedEmployee(employee)
    }
}