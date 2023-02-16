package com.example.rma.fragments

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.rma.databinding.FragmentItemBinding
import com.example.rma.roomDB.BookItem
import java.util.*
import kotlin.collections.ArrayList


class MyBooksRecyclerViewAdapter(
) : RecyclerView.Adapter<MyBooksRecyclerViewAdapter.ViewHolder>() {

    var onItemClick: ((BookItem) -> Unit)? = null
    var onButtonClick: ((BookItem) -> Unit)? = null
    var books: List<BookItem> = listOf()
    var booksCopy: List<BookItem> = listOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setBookItems(bookItems: List<BookItem>){
        books = bookItems
        booksCopy = bookItems
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterBooks(text: String) {
        // creating a new array list to filter our data.
        val filteredList: ArrayList<BookItem> = ArrayList()
        books = listOf()
        // running a for loop to compare elements.
        for (book in booksCopy) {
            // checking if the entered string matched with any item of our recycler view.
            if (book.name.lowercase().contains(text.lowercase()) || book.author.lowercase().contains(text.lowercase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredList.add(book)
            }
        }
        return if (filteredList.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            // books = booksCopy
            notifyDataSetChanged()
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            books = filteredList
            notifyDataSetChanged()
        }

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = books[position]
        holder.bookName.text = item.name
        holder.bookAuthor.text = item.author
        holder.likeButton.isSelected = item.favourite
    }

    override fun getItemCount(): Int = books.size

    inner class ViewHolder(binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val bookName: TextView = binding.bookName
        val bookAuthor: TextView = binding.bookAuthor
        val layout: CardView = binding.book
        val likeButton: ImageButton = binding.like

        init {
            layout.setOnClickListener{
                onItemClick?.invoke(books[bindingAdapterPosition])
            }
            likeButton.setOnClickListener {
                onButtonClick?.invoke(books[bindingAdapterPosition])
            }
        }

        override fun toString(): String {
            return super.toString() + " '" + bookName.text + "'" + bookAuthor.text + "'"
        }
    }
}