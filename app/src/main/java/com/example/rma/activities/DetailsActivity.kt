package com.example.rma.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.rma.R
import com.example.rma.fragments.DetailsFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext


class DetailsActivity : AppCompatActivity(), CoroutineScope {

    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        if (savedInstanceState == null) {
            val b: Bundle? = intent.extras
            val id: Int = b?.getInt("id") ?: 0

            Log.d("TAG IN DETAILS ACTIVITY", id.toString())
            val bundle = bundleOf("bookId" to id,
                "bookName" to b?.getString("bookName"),
                "bookAuthor" to b?.getString("bookAuthor"),
                "bookPublished" to b?.getString("bookPublished"),
                "bookDescription" to b?.getString("bookDescription"),
                "bookFavourite" to b?.getBoolean("bookFavourite"))
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<DetailsFragment>(R.id.details_fragment_view, args = bundle)
            }
        }
    }

    fun finishDetailsActivity(view: View) {
        finish()
    }

}