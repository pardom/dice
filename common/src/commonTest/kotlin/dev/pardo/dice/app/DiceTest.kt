package dev.pardo.dice.app

import dev.pardo.dice.app.Dice.Model
import dev.pardo.dice.app.Dice.Msg
import dev.pardo.dice.app.Dice.makeUpdate
import dev.pardo.dice.data.GetHistory
import dev.pardo.dice.data.PutHistory
import kotlin.test.Test
import kotlin.test.assertEquals

class DiceTest {

    private val getHistory: GetHistory = { listOf(1, 2, 3) }
    private val putHistory: PutHistory = { }

    @Test
    fun `SetRolls updates roll list`() {
        val update = makeUpdate(putHistory)
        val model = Model(emptyList())
        val next = update(Msg.SetRolls(listOf(1, 2, 3)), model)
        assertEquals(next.first.rolls, listOf(1, 2, 3))
    }

    @Test
    fun `AddRoll appends roll to roll list`() {
        val update = makeUpdate(putHistory)
        val model = Model(emptyList())

        val next1 = update(Msg.AddRoll(1), model)
        assertEquals(next1.first.rolls, listOf(1))

        val next2 = update(Msg.AddRoll(2), model)
        assertEquals(next2.first.rolls, listOf(1, 2))

        val next3 = update(Msg.AddRoll(3), model)
        assertEquals(next3.first.rolls, listOf(1, 2, 3))
    }

}
