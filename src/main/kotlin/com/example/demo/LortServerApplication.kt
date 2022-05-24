package com.example.demo

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.io.File

@SpringBootApplication
class DemoApplication

val characterList = Json.decodeFromString<CharacterList>(File("character.json").readText())

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)

	characterList.docs.forEach {
		println(it.name)
	}
}
