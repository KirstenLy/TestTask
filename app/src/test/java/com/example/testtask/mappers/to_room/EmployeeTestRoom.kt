package com.example.testtask.mappers.to_room

import com.example.testtask.data.datasource.database.room.model.EmployeeDB
import com.example.testtask.data.datasource.database.room.model.SpecialtyDB
import com.example.testtask.data.toDBModel
import com.example.testtask.domain.model.Employee
import com.example.testtask.domain.model.Speciality
import org.junit.Test
import kotlin.test.assertNotNull

class EmployeeTestRoom {

    @Test fun `Employee to EmployeeDB should map correctly`(){
        val employee = Employee("name","lastName","22-33-44","avatarURL", emptyList())
        checkIfSpecialityFieldsContainsNull(employee.toDBModel(0))
    }

    private fun checkIfSpecialityFieldsContainsNull(employeeDB:EmployeeDB){
        assertNotNull(employeeDB)
        assertNotNull(employeeDB.id)
        assertNotNull(employeeDB.firstName)
        assertNotNull(employeeDB.lastName)
        assertNotNull(employeeDB.birthday)
        assertNotNull(employeeDB.avatarUrl)
        assertNotNull(employeeDB.specialtyDBList)
    }
}