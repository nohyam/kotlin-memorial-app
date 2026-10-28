package com.nohyam.memorial.data.repository

import com.nohyam.memorial.data.dao.RecordDao
import com.nohyam.memorial.data.entity.RecordEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.ZoneId

class RecordRepository(private val recordDao: RecordDao){
    val zone = ZoneId.systemDefault()

    suspend fun addRecord(recordEntity: RecordEntity) {
        return recordDao.addRecord(recordEntity)
    }

    suspend fun editRecord(recordEntity: RecordEntity) {
        return recordDao.editRecord(recordEntity)
    }

    suspend fun saveRecordList(records: List<RecordEntity>){
        return recordDao.saveRecordList(records)
    }

    suspend fun deleteRecord(recordEntity: RecordEntity) {
        return recordDao.deleteRecord(recordEntity)
    }

    fun getTodayRecordList(date: LocalDate): Flow<List<RecordEntity>> {
        val today = date
            .atStartOfDay(zone)
            .toInstant()
            .toEpochMilli()

        val tomorrow = date
            .plusDays(1)
            .atStartOfDay(zone)
            .toInstant()
            .toEpochMilli()

        return recordDao.getRecordListByDate(start = today, end = tomorrow)
    }

    fun getYesterdayRecordList(date: LocalDate) : Flow<List<RecordEntity>>{
        val yesterday = date
            .minusDays(1)
            .atStartOfDay(zone)
            .toInstant()
            .toEpochMilli()

        val today = date
            .atStartOfDay(zone)
            .toInstant()
            .toEpochMilli()

        return recordDao.getRecordListByDate(yesterday,today)
    }

}
