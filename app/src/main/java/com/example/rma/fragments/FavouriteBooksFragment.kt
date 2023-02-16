package com.example.rma.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import com.example.rma.MyApplication
import com.example.rma.R
import com.example.rma.roomDB.BookItemViewModel

/**
 * A fragment representing a list of Items.
 */
class FavouriteBooksFragment : Fragment() {

    private val favouriteBookItemViewModel: BookItemViewModel by activityViewModels {
        BookItemViewModel.BookItemViewModelFactory((activity?.application as MyApplication).repository)
    }

    private var columnCount = 1
    lateinit var likeButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            columnCount = 1
        }
    }



    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)
        var adapter = MyBooksRecyclerViewAdapter()

        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
            }
            view.adapter = adapter
        }
        favouriteBookItemViewModel.favouriteBooks.observe(viewLifecycleOwner) { bookItemList ->
            bookItemList?.let {
                adapter.setBookItems(bookItemList)
            }
        }

        return view
    }

    fun finishFragment(){
        parentFragmentManager.popBackStackImmediate()
    }
}