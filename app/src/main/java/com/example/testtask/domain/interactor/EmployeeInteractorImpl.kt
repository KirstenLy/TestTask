package com.example.testtask.domain.interactor

import com.example.sdk.other.Either
import com.example.testtask.domain.interfaces.EmployeeRepository
import com.example.testtask.domain.interfaces.SpecialityRepository
import com.example.testtask.domain.model.Employee
import com.example.testtask.domain.interfaces.EmployeeInteractor
import com.example.testtask.failure.Failure
import javax.inject.Inject

class EmployeeInteractorImpl @Inject constructor(
    private val employeeRepository: EmployeeRepository,
    private val specialityRepository: SpecialityRepository) : EmployeeInteractor {

    override suspend fun getEmployees(): Either<Failure, List<Employee>> {
        val employeeListResult = employeeRepository.getEmployees()
        when (employeeListResult) {
            is Either.Data -> {
                if (specialityRepository.getSpecialities().isEmpty()) {
                    specialityRepository.setSpecialitiesFromEmployeeList(employeeListResult.data)
                }
                return employeeListResult
            }
            else -> return employeeListResult
        }
    }

    override fun getSelectedEmployee(): Employee? {
        return employeeRepository.getSelectedEmployee()
    }

    override fun setSelectedEmployee(employee: Employee) {
        employeeRepository.setSelectedEmployee(employee)
    }
}