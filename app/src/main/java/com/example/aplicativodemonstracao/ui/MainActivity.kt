package com.example.aplicativodemonstracao.ui

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.provider.Settings.Global
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.aplicativodemonstracao.R
import com.example.aplicativodemonstracao.database.Anotacao
import com.example.aplicativodemonstracao.database.ProvedorBD
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        /*val database = ProvedorBD.obterInstancia(this)
        val anotacaoDAO = database.anotacaoDAO()

        lifecycleScope.launch {
            anotacaoDAO.inserir(Anotacao(titulo = "Anotação 1", descricao = "Descrição da anotação 1"))
            val anotacoes = anotacaoDAO.listarTodos()
            anotacoes.forEach {
                println("Título: ${it.titulo}, Descrição: ${it.descricao}")
            }
        }*/
    }
}

/*fun main(context: Context){
    val database = ProvedorBD.obterInstancia(context)
    val anotacaoDAO = database.anotacaoDAO()

    GlobalScope.launch {
        anotacaoDAO.inserir(Anotacao(titulo = "Anotação 1", descricao = "Descrição da anotação 1"))
        val anotacoes = anotacaoDAO.listarTodos()
        anotacoes.forEach {
            println("Título: ${it.titulo}, Descrição: ${it.descricao}")
        }
    }
}*/
