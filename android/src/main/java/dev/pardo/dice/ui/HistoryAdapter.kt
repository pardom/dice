package dev.pardo.dice.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import dev.pardo.dice.Drawables
import dev.pardo.dice.R
import dev.pardo.dice.ui.HistoryAdapter.ViewHolder

class HistoryAdapter : RecyclerView.Adapter<ViewHolder>() {

    var items: List<Int> = emptyList()
        set(value) {
            field = value.asReversed()
            notifyDataSetChanged()
        }

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.history_item_view, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemId(position: Int): Long {
        return items.size - position.toLong()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(face: Int) {
            itemView as ImageView
            itemView.setImageResource(Drawables.DIE_FACES[face - 1])
        }
    }

}
