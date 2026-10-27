package com.example.httpreqdemov2

import android.app.Application
import androidx.room.Room
import com.example.httpreqdemov2.data.ApiClient
import com.example.httpreqdemov2.data.FunFactDatabase
import com.example.httpreqdemov2.data.FunFactRepository
import io.ktor.client.HttpClient

class FunFactApplication : Application() {

    val database: FunFactDatabase by lazy {
        Room.databaseBuilder(applicationContext, FunFactDatabase::class.java, "fun_facts.db").build()
    }

    val httpClient: HttpClient get() = ApiClient.client

    val repository: FunFactRepository by lazy {
        FunFactRepository(database.funFactDao(), httpClient)
    }
}