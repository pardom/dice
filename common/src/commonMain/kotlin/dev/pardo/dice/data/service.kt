package dev.pardo.dice.data

import dev.pardo.dice.app.Roll

typealias GetHistory = () -> List<Roll>

typealias PutHistory = (List<Roll>) -> Unit
