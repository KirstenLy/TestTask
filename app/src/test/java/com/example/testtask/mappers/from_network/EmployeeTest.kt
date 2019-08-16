package com.example.testtask.mappers.from_network

import com.example.testtask.data.model.EmployeeNetwork
import com.example.testtask.data.toDomain
import com.example.testtask.domain.model.Employee
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class EmployeeTest {

    @Test fun `EmployeeNetwork to Employee should map correctly`(){
        var employeeNetwork = EmployeeNetwork("name","lastName","22-12-1992", "avatr_url", emptyList())
        checkIfEmployeeFieldsContainsNull(employeeNetwork.toDomain())

        employeeNetwork = EmployeeNetwork(null,"lastName","22-12-1992", "avatr_url", emptyList())
        checkIfEmployeeFieldsContainsNull(employeeNetwork.toDomain())

        employeeNetwork = EmployeeNetwork(null,null,"22-12-1992", "avatr_url", emptyList())
        checkIfEmployeeFieldsContainsNull(employeeNetwork.toDomain())

        employeeNetwork = EmployeeNetwork(null,null,null, "avatr_url", emptyList())
        checkIfEmployeeFieldsContainsNull(employeeNetwork.toDomain())

        employeeNetwork = EmployeeNetwork(null,null,null, null, emptyList())
        checkIfEmployeeFieldsContainsNull(employeeNetwork.toDomain())

        employeeNetwork = EmployeeNetwork(null,null,null, null, null)
        checkIfEmployeeFieldsContainsNull(employeeNetwork.toDomain())

        employeeNetwork = EmployeeNetwork(null,null,"Unparsable text", null, emptyList())
        checkIfEmployeeFieldsContainsNull(employeeNetwork.toDomain())
        assertEquals(employeeNetwork.toDomain().birthday,"Unparsable date")
    }

    private fun checkIfEmployeeFieldsContainsNull(employee:Employee){
        assertNotNull(employee)
        assertNotNull(employee.firstName)
        assertNotNull(employee.lastName)
        assertNotNull(employee.birthday)
        assertNotNull(employee.avatarUrl)
        assertNotNull(employee.specialtyList)
    }
}