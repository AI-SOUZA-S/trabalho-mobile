package com.example.aplicativodemonstracao.ui

import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aplicativodemonstracao.CustomAdapter
import com.example.aplicativodemonstracao.R
import com.example.aplicativodemonstracao.databinding.ActivityMainBinding
import com.example.aplicativodemonstracao.database.Anotacao
import android.widget.ListView
import androidx.lifecycle.lifecycleScope
import com.example.aplicativodemonstracao.database.ProvedorBD
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var addButton: ImageButton;
    private lateinit var binding: ActivityMainBinding;
    private lateinit var listView: ListView;
    private lateinit var adapter: CustomAdapter;
    private val listaAnotacoes = ArrayList<Anotacao>();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        addButton = binding.addButton

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        listView = findViewById(R.id.listView)
        adapter = CustomAdapter(this, listaAnotacoes)
        listView.adapter = adapter

        carregarAnotacoes()

        addButton.setOnClickListener {
            showNoteModal(null)
        }
        listView.setOnItemClickListener { _, _, position, _ ->
            Log.d("MainActivity", "Item clicado: posição $position")
            showNoteModal(position)
        }
    }


    private fun carregarAnotacoes(){
        val database = ProvedorBD.obterInstancia(this)
        val anotacaoDAO = database.anotacaoDAO()

        lifecycleScope.launch {
            val anotacoes = anotacaoDAO.listarTodos()
            listaAnotacoes.clear()
            listaAnotacoes.addAll(anotacoes)
            adapter.clear()
            adapter.addAll(anotacoes)
            adapter.notifyDataSetChanged()
        }

    }

    private fun showNoteModal(position: Int?) {
        val modalNote = ModalNote()
        if (position != null) {
            val anotacoes = listaAnotacoes[position].titulo
            val args = Bundle()
            args.putString("note", anotacoes)
            modalNote.arguments = args
        }
        modalNote.show(supportFragmentManager, "modalNote")
        modalNote.listener = object : ModalNote.NewNoteDialogListener {
            override fun onDialogPositiveClick(note: String) {
                val database = ProvedorBD.obterInstancia(applicationContext)
                val anotacaoDAO = database.anotacaoDAO()

                lifecycleScope.launch {
                    if(position != null){
                        val anotacao = listaAnotacoes[position]
                        anotacao.titulo = note
                        anotacaoDAO.atualizar(anotacao)
                        listaAnotacoes[position] = anotacao
                    }else{
                        val novaAnotacao = Anotacao(titulo = note)
                        anotacaoDAO.inserir(novaAnotacao)
                        listaAnotacoes.add(novaAnotacao)
                    }
                }
                adapter.clear()
                adapter.addAll(listaAnotacoes)
                adapter.notifyDataSetChanged()
            }
        }
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

