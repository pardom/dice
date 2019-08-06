package dev.pardo.dice.app

import com.benasher44.uuid.Uuid
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
        val rolls: List<Roll>
    )

    sealed class Msg {
        object UserShookDevice : Msg()
        object UserClickedRollButton : Msg()
        data class SetRolls(val rolls: List<Roll>) : Msg()
        data class AddRoll(val roll: Roll) : Msg()
    }

    data class Props(
        val rolls: List<Roll>,
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
                        delay(100)
                        val roll = Roll(
                            Uuid(),
                            Random.nextInt(1..6)
                        )
                        dispatch(Msg.AddRoll(roll))
                    }
                }
                is Msg.SetRolls -> {
                    model.copy(rolls = msg.rolls.takeLast(5)) to none()
                }
                is Msg.AddRoll -> {
                    val rolls = (model.rolls + msg.roll).takeLast(5)
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
