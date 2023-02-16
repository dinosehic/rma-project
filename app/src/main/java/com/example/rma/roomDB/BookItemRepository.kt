package com.example.rma.roomDB

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow

class BookItemRepository(private val bookItemDao: BookItemDao) {

    val allBookItems: Flow<MutableList<BookItem>> = bookItemDao.getBookItems()

    val favouriteBookItems: Flow<MutableList<BookItem>> = bookItemDao.getFavouriteBooks()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateLike(value: Boolean, id: Int){
        return bookItemDao.updateLike(value,id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getBookById(id: Int): BookItem {
        return bookItemDao.getBookById(id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(bookItem: BookItem){
        bookItemDao.insert(bookItem)
    }


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(bookItem: BookItem){
        bookItemDao.delete(bookItem)
    }
}