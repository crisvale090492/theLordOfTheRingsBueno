package com.example.demo

import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.Id


@Entity
class Usuario (@Id var nombre: String, var pass: String) {
    val token = nombre + pass
    @ElementCollection
    var equipo = mutableListOf<Int>()
}
