package dev.pardo.dice.app

import com.benasher44.uuid.Uuid

data class Roll(
    val id: Uuid,
    val face: Int
)
