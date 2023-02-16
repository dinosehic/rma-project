package com.example.rma.roomDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.Date

@Entity(tableName="BookItem")
data class BookItem(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int
                ,@ColumnInfo(name = "name") val name: String
                ,@ColumnInfo(name = "author") val author: String
                ,@ColumnInfo(name = "description") val description: String
                ,@ColumnInfo(name = "favourite") val favourite: Boolean
                ,@ColumnInfo(name = "published") val published: String
)