package com.example.rma

import android.app.Application
import com.example.rma.roomDB.BookItemRepository
import com.example.rma.roomDB.BookItemRoomDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MyApplication : Application() {

    // No need to cancel this scope as it'll be torn down with the process
    private val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    private val database by lazy { BookItemRoomDB.getDatabase(this, applicationScope) }
    val repository by lazy { BookItemRepository(database.bookItemDao())}

}