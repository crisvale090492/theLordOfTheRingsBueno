package com.example.demo

import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.Id


@Entity
data class Usuario (@Id var nombre: String, var pass: String) {
    val token = nombre + pass
    var facil = false
    var medio = false
    var dificil = false

    @ElementCollection
    var equipo = mutableListOf<String>()
}
