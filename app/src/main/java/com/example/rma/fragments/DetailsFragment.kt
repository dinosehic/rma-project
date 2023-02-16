package com.example.rma.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.rma.MyApplication
import com.example.rma.R
import com.example.rma.roomDB.BookItemViewModel


class DetailsFragment : Fragment(R.layout.details_fragment) {

    private val bookItemViewModel: BookItemViewModel by viewModels {
        BookItemViewModel.BookItemViewModelFactory((activity?.application as MyApplication).repository)
    }

    private var bookId: Int = 0
    private var bookName: String = ""
    private var bookAuthor: String = ""
    private var bookPublished: String = ""
    private var bookDescription: String = ""
    private var bookFavourite: Boolean = false

    lateinit var bookNameView: TextView
    lateinit var bookAuthorView: TextView
    lateinit var bookDescriptionView: TextView
    lateinit var bookDateView: TextView
    lateinit var bookFavouriteView: TextView
    lateinit var descriptionButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bookId = requireArguments().getInt("bookId")
        bookName = requireArguments().getString("bookName").toString()
        bookAuthor = requireArguments().getString("bookAuthor").toString()
        bookPublished = requireArguments().getString("bookPublished").toString()
        bookDescription = requireArguments().getString("bookDescription").toString()
        bookFavourite = requireArguments().getBoolean("bookFavourite")
        Log.d("TAG IN DETAILS FRAGMENT", bookId.toString())


    }


    @SuppressLint("MissingInflatedId", "CommitTransaction")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var descriptionShown: Boolean = false

        val view = inflater.inflate(R.layout.details_fragment, container, false)

        bookNameView = view.findViewById(R.id.bookNameView)
        bookAuthorView = view.findViewById(R.id.bookAuthorView)
        bookDescriptionView = view.findViewById(R.id.bookDescriptionView)
        bookDateView = view.findViewById(R.id.bookPublishedView)
        bookFavouriteView = view.findViewById(R.id.bookFavouriteView)
        descriptionButton = view.findViewById(R.id.descriptionButton)

        bookNameView.text = bookName
        bookAuthorView.text = bookAuthor
        bookDateView.text = bookPublished
        if (bookFavourite) {
            bookFavouriteView.text = "This book is in your favourites."
        }else {
            bookFavouriteView.text = "This book is not in your favourites."
        }

        descriptionButton.setOnClickListener {
            if (descriptionShown){
                descriptionShown = false
                bookDescriptionView.text = ""
            }else{
                descriptionShown = true
                bookDescriptionView.text = bookDescription
            }
        }


        // Inflate the layout for this fragment
        return view
    }


}