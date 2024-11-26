package com.example.aplicativodemonstracao.database
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.aplicativodemonstracao.database.Anotacao
import java.util.concurrent.Flow

@Dao
interface AnotacaoDAO {
    @Insert
    suspend fun inserir(anotacao: Anotacao)

    @Update
    suspend fun atualizar(anotacao: Anotacao)

    @Delete
    suspend fun deletar(anotacao: Anotacao)

    @Query("SELECT * FROM anotacao")
    suspend fun listarTodos(): List<Anotacao>

}