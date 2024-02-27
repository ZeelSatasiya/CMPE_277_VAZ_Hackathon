package com.example.macroeconomicresearch.notes

import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.recyclerview.widget.RecyclerView


class DiffUtilHelper<T>(private val diffCallback: DiffUtil.ItemCallback<T>,
                        private val adapter: RecyclerView.Adapter<*>,
                        private val headerOffset: Int = 1) {


    fun submitList(list: List<T>?, commitCallback : Runnable? = null) {
        mHelper.submitList(list, commitCallback)
    }

    fun justSetList(list: List<T>?)
    {
        mHelper.justSetList(list)
    }

    class OffsetListUpdateCallback(
        private val adapter: RecyclerView.Adapter<*>,
        private val offset: Int
    ) : ListUpdateCallback {

        private fun offsetPosition(originalPosition: Int): Int {
            return originalPosition + offset
        }

        override fun onInserted(position: Int, count: Int) {
            adapter.notifyItemRangeInserted(offsetPosition(position), count)
        }

        override fun onRemoved(position: Int, count: Int) {
//            Log.e("NOTIFYING ITEM REMOVE", "$position  $count"  )
            adapter.notifyItemRangeRemoved(offsetPosition(position), count)
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            adapter.notifyItemMoved(offsetPosition(fromPosition), offsetPosition(toPosition))
        }

        override fun onChanged(position: Int, count: Int, payload: Any?) {
            adapter.notifyItemRangeChanged(offsetPosition(position), count, payload)
        }
    }


     val mHelper by lazy {
         AsyncListDiffer<T>(
             OffsetListUpdateCallback(
                 adapter,
                 headerOffset
             ),
             AsyncDifferConfig.Builder(diffCallback).build()
         )
    }


}