package com.example.aplicativodemonstracao.database
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

object ProvedorBD {
    private var instancia: AppDatabase? = null

    fun obterInstancia(context: Context): AppDatabase {
        return instancia ?: synchronized(this) {
            val novaInstancia = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "meu_database"
            ).setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
                .build()
            instancia = novaInstancia
            novaInstancia
        }
    }

}