package br.com.developer.allefsousa.adorofilmes.pesquisarFilme.v2

import androidx.recyclerview.widget.DiffUtil
import br.com.developer.allefsousa.adorofilmes.data.Result

class SpotDiffCallback(
        private val old: List<Result>,
        private val new: List<Result>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return old.size
    }

    override fun getNewListSize(): Int {
        return new.size
    }

    override fun areItemsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return old[oldPosition].id == new[newPosition].id
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return old[oldPosition] == new[newPosition]
    }

}
