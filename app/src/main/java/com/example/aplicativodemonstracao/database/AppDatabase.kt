package com.example.aplicativodemonstracao.database
import android.content.Context
//import androidx.privacysandbox.tools.core.generator.build
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Anotacao::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase()  {
    abstract fun anotacaoDAO(): AnotacaoDAO

    /*companion object{
        @Volatile
        private var instancia: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return instancia ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "my_database"
                ).build()
                instancia = instance
                instance
            }
        }
    }*/
}