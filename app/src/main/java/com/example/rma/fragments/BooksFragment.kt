package com.example.rma.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rma.MyApplication
import com.example.rma.R
import com.example.rma.activities.DetailsActivity
import com.example.rma.roomDB.BookItem
import com.example.rma.roomDB.BookItemViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A fragment representing a list of Items.
 */
class BooksFragment : Fragment() {

    private val bookItemViewModel: BookItemViewModel by activityViewModels {
        BookItemViewModel.BookItemViewModelFactory((activity?.application as MyApplication).repository)
    }

    private var columnCount = 1

    var favouriteBooks: List<BookItem> = listOf()
    var allBooks: List<BookItem> = listOf()

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

        bookItemViewModel.allBookItems.observe(viewLifecycleOwner) { bookItemList ->
            bookItemList?.let {
                allBooks = bookItemList
                if(bookItemViewModel.favourites.value == true){
                    adapter.setBookItems(favouriteBooks)
                }else{
                    adapter.setBookItems(allBooks)
                }
            }
        }

        bookItemViewModel.filter.observe(viewLifecycleOwner) { msg ->
            msg?.let{
                adapter.filterBooks(msg)
                Log.d("msg", msg)
            }
        }

        bookItemViewModel.favouriteBooks.observe(viewLifecycleOwner) { favouriteList ->
            favouriteList?.let{
                favouriteBooks = favouriteList
            }
        }

        bookItemViewModel.favourites.observe(viewLifecycleOwner) { favourites ->
            favourites?.let{
                if(favourites){
                    adapter.setBookItems(favouriteBooks)
                }else{
                    adapter.setBookItems(allBooks)
                }
            }
        }


        adapter.onItemClick = { bookItem ->
            // log book's ID
            Log.d("TAG", bookItem.id.toString())

            val intent = Intent(activity, DetailsActivity::class.java)
            intent.putExtra("id", bookItem.id)
            intent.putExtra("bookName", bookItem.name)
            intent.putExtra("bookAuthor", bookItem.author)
            intent.putExtra("bookPublished", bookItem.published)
            intent.putExtra("bookDescription", bookItem.description)
            intent.putExtra("bookFavourite", bookItem.favourite)
            startActivity(intent)
        }


        // TODO fix this
        adapter.onButtonClick = { bookItem ->
            // log book's ID
            Log.d("TAG FROM BUTTON CLICK", bookItem.id.toString())
            if(bookItem.favourite) {
                CoroutineScope(Dispatchers.IO).launch {
                    bookItemViewModel.updateLike(false, bookItem.id)
                }

            }else{
                CoroutineScope(Dispatchers.IO).launch {
                    bookItemViewModel.updateLike(true, bookItem.id)
                }

            }
        }


        return view
    }
}