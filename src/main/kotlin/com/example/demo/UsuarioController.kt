package com.example.demo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.random.Random


@RestController
class UsuarioController (private val usuarioRepository: UsuarioRepository) {

    @GetMapping("registrarUsuario/{nombre}/{pass}")
    @Synchronized
    fun requestCrearUsuario(@PathVariable nombre: String, @PathVariable pass: String): String {
        val userOptional = usuarioRepository.findById(nombre)

        return if (userOptional.isPresent) {
            val user = userOptional.get()
            if (user.pass == pass) {
                user.token
            } else {
                "Pass invalida"
            }
        } else {
            val user = Usuario(nombre, pass)
            usuarioRepository.save(user)
            user.token
        }

    }

    @PostMapping("asignarEquipo/{token}")
    fun obtenerEquipo(@PathVariable token: String): Any {
        val equipo = mutableListOf<Int>()
        var user: Usuario? = null
        var personaje: Doc
        var cantidad = 0
        usuarioRepository.findAll().forEach { currentUser ->
            if (currentUser.token == token) {
                user = currentUser
                return@forEach
            }
        }
        if (user == null)
            return "Error: Token invalido"

        while (cantidad <= 5) {
            personaje = characterList.docs.random()
            if (personaje.miUsuario == null) {
                personaje.miUsuario = user?.token
                equipo.add(personaje.id.toInt())
                cantidad++
            }
        }
        return equipo
    }

    @PostMapping("liberarPersona/{personajeId}/{token}")
    fun liberarPersona(@PathVariable personajeId: String, @PathVariable token: String): Any {
        var user: Usuario? = null
        var personaje: Doc? = null
        usuarioRepository.findAll().forEach { currentUser ->
            if (currentUser.token == token) {
                user = currentUser
                return@forEach
            }
        }
        if (user == null)
            return "Error: Token invalido"

        characterList.docs.forEach { character ->
            if(character.id == personajeId)
                personaje = character
                return@forEach
        }

        return if (personaje == null || personaje?.miUsuario != token)
            "El personaje no pertenece al jugador"
        else {
            personaje?.miUsuario = null
            user?.equipo?.remove(personaje?.id?.toInt())
            "Personaje liberado"
        }
    }

}
