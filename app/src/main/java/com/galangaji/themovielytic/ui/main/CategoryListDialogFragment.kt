package com.galangaji.themovielytic.ui.main

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.galangaji.themovielytic.R
import kotlinx.android.synthetic.main.fragment_category_list_dialog_list_dialog.*
import kotlinx.android.synthetic.main.fragment_category_list_dialog_list_dialog_item.view.*

const val ARG_ITEM_COUNT = "item_count"

/**
 *
 * A fragment that shows a list of items as a modal bottom sheet.
 *
 * You can show this modal bottom sheet from your activity like this:
 * <pre>
 *    CategoryListDialogFragment.newInstance(30).show(supportFragmentManager, "dialog")
 * </pre>
 */
class CategoryListDialogFragment(private val listener: categoryListener) :
    BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_category_list_dialog_list_dialog,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        list.layoutManager = LinearLayoutManager(context)
        list.adapter = arguments?.getInt(ARG_ITEM_COUNT)?.let { CategoryAdapter(it, listener) }
    }

    private inner class ViewHolder internal constructor(
        inflater: LayoutInflater,
        parent: ViewGroup
    ) : RecyclerView.ViewHolder(
        inflater.inflate(
            R.layout.fragment_category_list_dialog_list_dialog_item,
            parent,
            false
        )
    ) {

        internal val text: TextView = itemView.text
    }

    private inner class CategoryAdapter internal constructor(
        private val mItemCount: Int,
        private val listener: categoryListener
    ) :
        RecyclerView.Adapter<ViewHolder>() {

        val categoryList = listOf("Popular", "Upcoming", "Top Rated", "Now Playing")

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(parent.context), parent)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.text.text = categoryList[position]
            holder.text.setOnClickListener {
                when (position) {
                    0 -> listener.onClickPopular()
                    1 -> listener.onClickUpcoming()
                    2 -> listener.onTopRated()
                    else -> listener.onClickNowPlaying()
                }
            }
        }

        override fun getItemCount(): Int {
            return mItemCount
        }
    }

    interface categoryListener {
        fun onClickNowPlaying()
        fun onTopRated()
        fun onClickUpcoming()
        fun onClickPopular()
    }

    companion object {

        fun newInstance(itemCount: Int, listener: categoryListener): CategoryListDialogFragment =
            CategoryListDialogFragment(listener).apply {
                arguments = Bundle().apply {
                    putInt(ARG_ITEM_COUNT, itemCount)
                }
            }

    }
}
