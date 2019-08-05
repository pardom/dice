package dev.pardo.dice.app

import dev.pardo.dice.data.GetHistory
import dev.pardo.dice.data.PutHistory
import kotlinx.coroutines.delay
import oolong.Init
import oolong.Update
import oolong.View
import oolong.effect
import oolong.effect.none
import kotlin.random.Random
import kotlin.random.nextInt

object Dice {

    data class Model(
        val rolls: List<Int>
    )

    sealed class Msg {
        object UserShookDevice : Msg()
        object UserClickedRollButton : Msg()
        data class SetRolls(val rolls: List<Int>) : Msg()
        data class AddRoll(val roll: Int) : Msg()
    }

    data class Props(
        val rolls: List<Int>,
        val onUserShookDevice: () -> Msg,
        val onUserClickedRollButton: () -> Msg
    )

    val makeInit: (GetHistory) -> Init<Model, Msg> = { getHistory ->
        {
            Model(emptyList()) to { dispatch ->
                val history = getHistory()
                dispatch(Msg.SetRolls(history))
            }
        }
    }

    val makeUpdate: (PutHistory) -> Update<Model, Msg> = { putHistory ->
        { msg, model ->
            when (msg) {
                Msg.UserShookDevice,
                Msg.UserClickedRollButton -> {
                    model to { dispatch ->
                        val roll = Random.nextInt(1..6)
                        delay(100)
                        dispatch(Msg.AddRoll(roll))
                    }
                }
                is Msg.SetRolls -> {
                    model.copy(rolls = msg.rolls) to none()
                }
                is Msg.AddRoll -> {
                    val rolls = model.rolls + msg.roll
                    model.copy(rolls = rolls) to effect {
                        putHistory(rolls)
                    }
                }
            }
        }
    }

    val view: View<Model, Props> = { model ->
        Props(
            model.rolls,
            { Msg.UserShookDevice },
            { Msg.UserClickedRollButton }
        )
    }

}
