package dev.pardo.dice

import android.content.Context
import android.preference.PreferenceManager
import com.benasher44.uuid.Uuid
import dev.pardo.dice.app.Dice
import dev.pardo.dice.app.Roll
import dev.pardo.dice.data.GetHistory
import dev.pardo.dice.data.PutHistory

class Inject(context: Context) {

    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)

    private val getHistory: GetHistory = {
        prefs.getString("history", "")
            .split(",")
            .filter { !it.isBlank() }
            .map { it.split(":") }
            .map { Roll(Uuid.parse(it[0])!!, it[1].toInt()) }
    }
    private val putHistory: PutHistory = { history ->
        val serialized = history
            .map { roll -> "${roll.id}:${roll.face}" }
            .joinToString(",")

        prefs.edit()
            .putString("history", serialized)
            .commit()
    }

    val init = Dice.makeInit(getHistory)
    val update = Dice.makeUpdate(putHistory)
}
