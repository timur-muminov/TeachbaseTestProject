package com.teachbaseTestProject.app_dependencies.local.entity.movie_detail

import androidx.room.TypeConverter
import com.teachbaseTestProject.entities.movie.Person

class PersonsConverter {

    private val personFieldsDelimiter = ";"
    private val personObjectsDelimiter = "#"

    @TypeConverter
    fun fromPersons(persons: List<Person?>): String {
        return persons.joinToString(separator = personObjectsDelimiter) {
            listOf(
                it?.id,
                it?.photo,
                it?.enName,
                it?.rusName,
                it?.enProfession
            ).joinToString(separator = personFieldsDelimiter)
        }
    }

    @TypeConverter
    fun toPersons(data: String): List<Person?> {
        if (data.isEmpty()) return emptyList()
        val persons = mutableListOf<Person>()
        data.split(personObjectsDelimiter).forEach { person ->
            persons.add(person.convertToPerson())
        }
        return persons.toList()
    }

    private fun String.convertToPerson(): Person {
        val fields = this.split(personFieldsDelimiter)
        return Person(
            id = fields[0].toInt(),
            photo = fields[1],
            enName = fields[2],
            rusName = fields[3],
            enProfession = fields[4]
        )
    }
}