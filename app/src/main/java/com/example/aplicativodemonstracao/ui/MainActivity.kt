package com.example.aplicativodemonstracao.ui

import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.example.aplicativodemonstracao.CustomAdapter
import com.example.aplicativodemonstracao.R
import com.example.aplicativodemonstracao.database.Anotacao
import com.example.aplicativodemonstracao.database.ProvedorBD
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var addButton: ImageButton
    private lateinit var listView: ListView
    private lateinit var adapter: CustomAdapter
    private val _listaAnotacoesLiveData = MutableLiveData<List<Anotacao>>(emptyList())
    val listaAnotacoesLiveData: LiveData<List<Anotacao>> = _listaAnotacoesLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        enableEdgeToEdge()

        addButton = findViewById(R.id.addButton)
        listView = findViewById(R.id.listView)

        adapter = CustomAdapter(this, arrayListOf())
        listView.adapter = adapter

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        listaAnotacoesLiveData.observe(this) { anotacoes ->
            adapter.clear()
            adapter.addAll(anotacoes)
            adapter.notifyDataSetChanged()
        }

        carregarAnotacoes()

        addButton.setOnClickListener {
            showNoteModal(null)
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            Log.d("MainActivity", "Item clicado: posição $position")
            showNoteModal(position)
        }
    }

    private fun carregarAnotacoes() {
        val database = ProvedorBD.obterInstancia(this)
        val anotacaoDAO = database.anotacaoDAO()

        lifecycleScope.launch {
            try {
                val anotacoes = anotacaoDAO.listarTodos()
                _listaAnotacoesLiveData.value = anotacoes
            } catch (e: Exception) {
                Log.e("MainActivity", "Erro ao carregar anotações: ${e.message}", e)
            }
        }
    }
    private fun showNoteModal(position: Int?) {
        val modalNote = ModalNote()
        if (position != null) {
            val anotacoes = listaAnotacoesLiveData.value?.get(position)?.titulo
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
                    if (position != null) {
                        val anotacao = listaAnotacoesLiveData.value?.get(position)
                        anotacao?.let {
                            it.titulo = note
                            anotacaoDAO.atualizar(it)
                        }
                    } else {
                        val novaAnotacao = Anotacao(titulo = note)
                        anotacaoDAO.inserir(novaAnotacao)
                    }
                    carregarAnotacoes()
                }
            }
        }
    }
}
