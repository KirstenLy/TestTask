package com.example.testtask.mappers.from_network

import com.example.testtask.data.model.SpecialtyNetwork
import com.example.testtask.data.toDomain
import com.example.testtask.domain.model.Speciality
import org.junit.Test
import kotlin.test.assertNotNull

class SpecialityTestNetwork {

    @Test fun `SpecialityNetwork to Speciality should map correctly`(){
        var specialityNetwork = SpecialtyNetwork(0,"name")

        checkIfSpecialityFieldsContainsNull(specialityNetwork.toDomain())

        specialityNetwork = SpecialtyNetwork(null,"name")
        checkIfSpecialityFieldsContainsNull(specialityNetwork.toDomain())

        specialityNetwork = SpecialtyNetwork(null,null)
        checkIfSpecialityFieldsContainsNull(specialityNetwork.toDomain())
    }

    private fun checkIfSpecialityFieldsContainsNull(speciality: Speciality){
        assertNotNull(speciality)
        assertNotNull(speciality.specialityID)
        assertNotNull(speciality.specialityName)
    }
}