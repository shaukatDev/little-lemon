package com.example.littlelemon

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

@Entity
data class MenuItemDB(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val price: Int,
    val image: String,
    val category: String,
)

@Dao
interface MenuItemDao {
    @Query("SELECT * FROM MenuItemDB")
    fun getAll(): LiveData<List<MenuItemDB>>

    @Insert
    fun insertAll(vararg menuItems: MenuItemDB)

    @Query("SELECT (SELECT COUNT(*) FROM MenuItemDB) == 0")
    fun isEmpty(): Boolean
}

@Database(entities = [MenuItemDB::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun menuItemDao(): MenuItemDao
}
