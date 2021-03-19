package id.co.foodrecipe.util

import androidx.recyclerview.widget.DiffUtil
import id.co.foodrecipe.models.Result

class RecipesDiffUtil(
    private val oldList: List<Result>,
    private val newList: List<Result>
): DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}