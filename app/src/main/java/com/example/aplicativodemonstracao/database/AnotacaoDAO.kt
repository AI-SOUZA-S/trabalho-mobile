package com.example.aplicativodemonstracao.database
import androidx.room.*

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