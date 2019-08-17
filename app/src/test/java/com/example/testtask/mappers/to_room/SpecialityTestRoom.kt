package com.example.testtask.mappers.to_room

import com.example.testtask.data.datasource.database.room.model.SpecialtyDB
import com.example.testtask.data.toDBModel
import com.example.testtask.domain.model.Speciality
import org.junit.Test
import kotlin.test.assertNotNull

class SpecialityTestRoom {

    @Test fun `Speciality to SpecialityDB should map correctly`(){
        val speciality = Speciality(0,"name")
        checkIfSpecialityFieldsContainsNull(speciality.toDBModel())
    }

    private fun checkIfSpecialityFieldsContainsNull(specialty: SpecialtyDB){
        assertNotNull(specialty)
        assertNotNull(specialty.id)
        assertNotNull(specialty.specialityID)
        assertNotNull(specialty.specialityName)
    }
}