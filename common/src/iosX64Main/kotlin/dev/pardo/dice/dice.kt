package dev.pardo.dice

import dev.pardo.dice.app.Dice
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainLoopDispatcher
import oolong.Init
import oolong.Oolong
import oolong.Render
import oolong.Update

fun Dice.runtime(
    init: Init<Dice.Model, Dice.Msg>,
    update: Update<Dice.Model, Dice.Msg>,
    render: Render<Dice.Msg, Dice.Props>
) = Oolong.runtime(init, update, view, render, GlobalScope, MainLoopDispatcher, MainLoopDispatcher)
