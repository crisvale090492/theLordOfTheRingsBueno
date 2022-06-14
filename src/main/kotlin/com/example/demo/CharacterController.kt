package com.example.demo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class CharacterController {

    @GetMapping("getHumans")
    fun getHumans(): List<Doc> {
        var listaHumanos = mutableListOf<Doc>()
        characterList.docs.forEach {
            if (it.race == "Human")
                listaHumanos.add(it)
        }
        return listaHumanos
    }

    @GetMapping("getElfs")
    fun getElfs(): List<Doc> {
        val listaElfs = mutableListOf<Doc>()
        characterList.docs.forEach {
            if (it.race == "Elfs")
                listaElfs.add(it)
        }
        return listaElfs
    }

    @GetMapping("getOrcs")
    fun getOrcs(): List<Doc> {
        val listaOrcs = mutableListOf<Doc>()
        characterList.docs.forEach {
            if (it.race == "Orcs")
                listaOrcs.add(it)
        }
        return listaOrcs
    }

    @GetMapping("getDragons")
    fun getDragons(): List<Doc> {
        val listaDragons = mutableListOf<Doc>()
        characterList.docs.forEach {
            if (it.race == "Dragons")
                listaDragons.add(it)
        }
        return listaDragons
    }

    @GetMapping("getGoodGuys")
    fun getGoodGuys(): List<Doc> {
        val listaGoodGuys = mutableListOf<Doc>()
        characterList.docs.forEach {
            if (it.race == "Humans" || it.race == "Elfs")
                listaGoodGuys.add(it)
        }
        return listaGoodGuys
    }

    @GetMapping("getBadGuys")
    fun getBadGuys(): List<Doc> {
        val listaBadGuys = mutableListOf<Doc>()
        characterList.docs.forEach {
            if (it.race == "Orcs" || it.race == "Dragons")
                listaBadGuys.add(it)
        }
        return listaBadGuys
    }

    @GetMapping("getOthers")
    fun getOthers(): List<Doc> {
        val listaOthers = mutableListOf<Doc>()
        characterList.docs.forEach {
            if (it.race != "Orcs" && it.race != "Dragons" && it.race != "Elfs" && it.race != "Humans")
                listaOthers.add(it)
        }
        return listaOthers
    }
}