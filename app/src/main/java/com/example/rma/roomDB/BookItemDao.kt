package com.example.rma.roomDB

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BookItemDao {
    @Query("SELECT * FROM BookItem") //ORDER BY id
    fun getBookItems(): Flow<MutableList<BookItem>>

    @Query("SELECT * FROM BookItem WHERE favourite = 1")
    fun getFavouriteBooks(): Flow<MutableList<BookItem>>

    @Query("SELECT * FROM BookItem WHERE id = :id")
    fun getBookById(id: Int): BookItem

    @Query("UPDATE BookItem SET favourite = :value WHERE id = :id")
    fun updateLike(value: Boolean, id: Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(bookItem: BookItem)

    @Delete
    fun delete(bookItem: BookItem)
}
