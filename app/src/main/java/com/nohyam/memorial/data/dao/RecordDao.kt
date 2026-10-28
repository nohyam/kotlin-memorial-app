package com.nohyam.memorial.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.nohyam.memorial.data.entity.RecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDao{
    @Insert
    suspend fun addRecord(record: RecordEntity)

    @Update
    suspend fun editRecord(record: RecordEntity)

    @Update
    suspend fun saveRecordList(records: List<RecordEntity>)

    @Delete
    suspend fun deleteRecord(record: RecordEntity)

    @Query("SELECT * FROM records WHERE createdAt >= :start AND createdAt < :end")
    fun getRecordListByDate(start: Long, end: Long) : Flow<List<RecordEntity>>
}
