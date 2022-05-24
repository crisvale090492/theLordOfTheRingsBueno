package com.example.demo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class CharacterController {

    @GetMapping("getHumans")
    fun gerHumans(): List<Doc> {
        var listaHumanos = mutableListOf<Doc>()
        characterList.docs.forEach {
            if (it.race == "Human")
                listaHumanos.add(it)
        }
        return listaHumanos
    }

    //todo: getelfs
    //todo: getorcs
    //todo: getdragons
    //todo: getgoodguys(elfs+humans)
    //todo: getbadguys(orcs+dragons)
    //todo: getothers(no good guy.no bad guy)
}