package com.example.rma.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.MutableLiveData
import com.example.rma.MyApplication
import com.example.rma.R
import com.example.rma.fragments.BooksFragment
import com.example.rma.fragments.DetailsFragment
import com.example.rma.roomDB.BookItem
import com.example.rma.roomDB.BookItemDao
import com.example.rma.roomDB.BookItemRoomDB
import com.example.rma.roomDB.BookItemViewModel
import java.util.Locale.filter


class MainActivity : AppCompatActivity() {

    private val bookItemViewModel: BookItemViewModel by viewModels {
        BookItemViewModel.BookItemViewModelFactory((application as MyApplication).repository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<BooksFragment>(R.id.fragment_container_view)
            }
        }
    }

    // calling on create option menu
    // layout to inflate our menu file.
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // below line is to get our inflater
        val inflater = menuInflater

        // inside inflater we are inflating our menu file.
        inflater.inflate(R.menu.search_menu, menu)

        // below line is to get our menu item.
        val searchItem: MenuItem = menu.findItem(R.id.actionSearch)

        val favouritesButton: MenuItem = menu.findItem(R.id.favourites)

        // show liked
        favouritesButton.setOnMenuItemClickListener {
            if (bookItemViewModel.favourites.value == true) {
                bookItemViewModel.favourites.value = false
                return@setOnMenuItemClickListener false
            }else{
                bookItemViewModel.favourites.value = true
                return@setOnMenuItemClickListener false
            }
        }

        // getting search view of our item.
        val searchView: SearchView = searchItem.actionView as SearchView

        // below line is to call set on query text listener method.
        searchView.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(msg: String): Boolean {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                bookItemViewModel.filter.value = msg
                return false
            }
        })
        return true
    }






}