package dev.pardo.dice

import dev.pardo.dice.app.Dice
import dev.pardo.dice.data.GetHistory
import dev.pardo.dice.data.PutHistory

object Inject {
    private val getHistory: GetHistory = { emptyList() }
    private val putHistory: PutHistory = {}
    val init = Dice.makeInit(getHistory)
    val update = Dice.makeUpdate(putHistory)
}
