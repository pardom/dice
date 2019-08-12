package dev.pardo.dice.app

import com.benasher44.uuid.Uuid
import dev.pardo.dice.app.Dice.Model
import dev.pardo.dice.app.Dice.Msg
import dev.pardo.dice.app.Dice.makeUpdate
import dev.pardo.dice.data.GetHistory
import dev.pardo.dice.data.PutHistory
import kotlin.test.Test
import kotlin.test.assertEquals

class DiceTest {

    private val getHistory: GetHistory = { stubRolls(3) }
    private val putHistory: PutHistory = { }

    @Test
    fun `SetRolls updates roll list`() {
        val rolls = stubRolls(3)
        val update = makeUpdate(putHistory)
        val model = Model(emptyList(), 0)
        val next = update(Msg.SetRolls(rolls), model)
        assertEquals(next.first.rolls, rolls)
    }

    @Test
    fun `AddRoll appends roll to roll list`() {
        val update = makeUpdate(putHistory)
        val model = Model(emptyList(), 0)

        val roll1 = stubRoll(1)
        val next1 = update(Msg.AddRoll(roll1), model)
        assertEquals(next1.first.rolls, listOf(roll1))

        val roll2 = stubRoll(2)
        val next2 = update(Msg.AddRoll(roll2), model)
        assertEquals(next2.first.rolls, listOf(roll1, roll2))

        val roll3 = stubRoll(3)
        val next3 = update(Msg.AddRoll(roll3), model)
        assertEquals(next3.first.rolls, listOf(roll1, roll2, roll3))
    }

    private fun stubRolls(count: Int): List<Roll> {
        return (1..count).map(::stubRoll)
    }

    private fun stubRoll(i: Int): Roll {
        return Roll(i, i)
    }

}
