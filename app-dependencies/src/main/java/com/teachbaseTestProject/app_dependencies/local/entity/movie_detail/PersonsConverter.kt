package com.teachbaseTestProject.app_dependencies.local.entity.movie_detail

import androidx.room.TypeConverter

class PersonsConverter {

    @TypeConverter
    fun fromPersons(persons: List<String?>): String {
        return persons.joinToString(",")
    }

    @TypeConverter
    fun toPersons(data: String): List<String?> {
        return data.split(",").toList()
    }
}