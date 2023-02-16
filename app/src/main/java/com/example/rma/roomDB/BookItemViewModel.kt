package com.example.rma.roomDB

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class BookItemViewModel(private val bookRepository: BookItemRepository) : ViewModel() {

    val allBookItems : LiveData<MutableList<BookItem>> = bookRepository.allBookItems.asLiveData()

    val favouriteBooks : LiveData<MutableList<BookItem>> = bookRepository.favouriteBookItems.asLiveData()

    var filter = MutableLiveData<String>()

    var favourites = MutableLiveData<Boolean>()

    suspend fun getBookById(id: Int): BookItem{
        return bookRepository.getBookById(id)
    }

    suspend fun updateLike(value: Boolean, id: Int){
        bookRepository.updateLike(value, id)
    }

    // Launching a new coroutine to insert the data in a non-blocking way
    fun insert(bookItem: BookItem) = viewModelScope.launch {
        bookRepository.insert(bookItem)
    }

    fun delete(bookItem: BookItem) = viewModelScope.launch {
        bookRepository.delete(bookItem)
    }

    class BookItemViewModelFactory(private val bookItemRepository: BookItemRepository)
        : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(BookItemViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return BookItemViewModel(bookItemRepository) as T
            }
            throw IllegalArgumentException("Unknown VieModel Class")
        }

    }


}