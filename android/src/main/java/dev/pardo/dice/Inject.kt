package dev.pardo.dice

import android.content.Context
import android.preference.PreferenceManager
import dev.pardo.dice.app.Dice
import dev.pardo.dice.data.GetHistory
import dev.pardo.dice.data.PutHistory

class Inject(context: Context) {

    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)

    private val getHistory: GetHistory = {
        prefs.getString("history", "")
            .split(",")
            .filter { !it.isBlank() }
            .map { it.toInt() }
    }
    private val putHistory: PutHistory = { history ->
        prefs.edit()
            .putString("history", history.joinToString(","))
            .commit()
    }

    val init = Dice.makeInit(getHistory)
    val update = Dice.makeUpdate(putHistory)
}
