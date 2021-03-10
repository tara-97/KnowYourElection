package com.example.android.politicalpreparedness.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.android.politicalpreparedness.network.models.Election

@Dao
interface ElectionDao {

    //TODO: Add insert query
    @Insert
    suspend fun insert(election: Election)

    //TODO: Add select all election query
    @Query("SELECT * FROM election_table")
    fun getElections():LiveData<List<Election>>

    //TODO: Add select single election query
    @Query("SELECT * from election_table WHERE id = :key")
    suspend fun get(key: Int): Election?

    //TODO: Add delete query
    @Query("DELETE FROM election_table WHERE id = :key")
    suspend fun deleteElection(key:Int)

    //TODO: Add clear query
    @Query("DELETE FROM election_table")
    suspend fun clear()
}