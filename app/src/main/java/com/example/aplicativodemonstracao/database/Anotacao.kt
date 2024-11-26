package com.example.aplicativodemonstracao.database
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anotacao")
class Anotacao(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var titulo: String
)