package com.example.aplicativodemonstracao.database
import android.content.Context
import androidx.room.Room

object ProvedorBD {
    private var instancia: AppDatabase? = null

    fun obterInstancia(context: Context): AppDatabase {
        if (instancia == null) {
            synchronized(AppDatabase::class) {
                instancia = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "anotacoes.db"
                ).build()
            }
        }
        return instancia!!
    }

}