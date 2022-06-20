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
    fun requestregistrarUsuario(@PathVariable nombre: String, @PathVariable pass: String): String {
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
    fun asignarEquipo(@PathVariable token: String): Any {
        val equipo = mutableListOf<String>()
        var user: Usuario? = null
        var personaje: Doc
        var cantidad = 0
        var tamano = 5
        usuarioRepository.findAll().forEach { currentUser ->
            if (currentUser.token == token) {
                user = currentUser
                return@forEach
            }
        }
        if (user == null)
            return "Error: Token invalido"

        if (user?.facil == true && user?.medio == true && user?.dificil == true) {
            tamano += 1
            user?.equipo?.forEach {  personaje ->
                user?.equipo?.remove(personaje)
            }
            user?.facil = false
            user?.medio = false
            user?.dificil = false
        }

        while (cantidad <= tamano) {
            personaje = characterList.docs.random()
            if (personaje.miUsuario == null) {
                personaje.miUsuario = user?.token
                equipo.add(personaje.id)
                cantidad++
            }
        }
        user?.let {
            usuarioRepository.save(it)
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
        print(user?.equipo)
        characterList.docs.forEach { character ->
            if(character.id == personajeId)
                personaje = character
                return@forEach
        }

        return if (personaje == null || personaje?.miUsuario != token)
            "El personaje no pertenece al jugador"
        else {
            personaje?.miUsuario = null
            user?.equipo?.remove(personaje?.id)
            user?.let {
                usuarioRepository.save(it)
                print(user?.equipo)
            }
            "Personaje liberado"
        }

    }

    @PostMapping("nivelFacil/{token}")
    fun nivelfacil(@PathVariable token: String): Any {
        var user: Usuario? = null
        val equipoFinal = mutableListOf<Doc>()
        val probabilidad = Random.nextInt(0,100)
        usuarioRepository.findAll().forEach { currentUser ->
            if (currentUser.token == token) {
                user = currentUser
                return@forEach
            }
        }
        if (user == null)
            return "Error: Usuario no encontrado"

        if (user?.facil == true)
            return "Error: Mazmorra ya superada"

        else
            user?.equipo?.forEach { personaje ->
                characterList.docs.forEach { character ->
                    if (personaje == character.id && probabilidad < 25) {
                        character.vivo = false
                        equipoFinal.add(character)
                        user?.equipo?.remove(personaje)
                    }
                    else {
                        user?.facil = true
                    }
                }
                user?.let {
                    usuarioRepository.save(it)
                    print(user?.equipo)
                }
            }

        return if (user?.equipo?.size == 0)
            "Equipo muerto"
        else
            equipoFinal
    }

    @PostMapping("nivelMedio/{token}")
    fun nivelMedio(@PathVariable token: String): Any {
        var user: Usuario? = null
        val equipoFinal = mutableListOf<Doc>()
        val probabilidad = Random.nextInt(0,100)
        usuarioRepository.findAll().forEach { currentUser ->
            if (currentUser.token == token) {
                user = currentUser
                return@forEach
            }
        }
        if (user == null)
            return "Error: Usuario no encontrado"

        if (user?.medio == true)
            return "Error: Mazmorra ya superada"

        else
            user?.equipo?.forEach { personaje ->
                characterList.docs.forEach { character ->
                    if (personaje == character.id && probabilidad < 50) {
                        character.vivo = false
                        equipoFinal.add(character)
                        user?.equipo?.remove(personaje)
                    }
                    else {
                        user?.medio = true
                    }
                }
                user?.let {
                    usuarioRepository.save(it)
                    print(user?.equipo)
                }
            }

        return if (user?.equipo?.size == 0)
            "Equipo muerto"
        else
            equipoFinal
    }

    @PostMapping("nivelDificil/{token}")
    fun nivelDificil(@PathVariable token: String): Any {
        var user: Usuario? = null
        val equipoFinal = mutableListOf<Doc>()
        val probabilidad = Random.nextInt(0,100)
        usuarioRepository.findAll().forEach { currentUser ->
            if (currentUser.token == token) {
                user = currentUser
                return@forEach
            }
        }

        if (user == null)
            return "Error: Usuario no encontrado"


        user?.equipo?.forEach { personaje ->
            characterList.docs.forEach { character ->
                if (personaje == character.id && probabilidad < 75) {
                    character.vivo = false
                    equipoFinal.add(character)
                    user?.equipo?.remove(personaje)
                    }
                else {
                    user?.dificil = true
                }
            }
            user?.let {
                usuarioRepository.save(it)
                print(user?.equipo)
            }
        }

        return if (user?.equipo?.size == 0)
            "Equipo muerto"
        else
            equipoFinal
    }


}
