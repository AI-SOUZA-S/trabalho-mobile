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

class MainActivity : AppCompatActivity() {
    private lateinit var addButton: ImageButton;
    private lateinit var binding: ActivityMainBinding;
    private lateinit var listView: android.widget.ListView;
    private lateinit var adapter: CustomAdapter;
    private val listaTeste = ArrayList<Int>();

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
        listaTeste.add(1)
        listaTeste.add(2)
        listaTeste.add(3)

        listView = findViewById(R.id.listView)
        adapter = CustomAdapter(this, listaTeste)
        listView.adapter = adapter
        this.addButton.setOnClickListener {
            showNoteModal(null)
        }
        listView.setOnItemClickListener { _, _, position, _ ->
            Log.d("MainActivity", "Item clicado: posição $position")
            showNoteModal(position)
        }
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
    private fun showNoteModal(position: Int?) {
        val modalNote = ModalNote()
        if (position != null) {
            val note = listaTeste[position].toString()
            val args = Bundle()
            args.putString("note", note)
            modalNote.arguments = args
        }
        modalNote.show(supportFragmentManager, "modalNote")
        modalNote.listener = object : ModalNote.NewNoteDialogListener {
            override fun onDialogPositiveClick(note: String) {
                if (position != null) {
                    listaTeste[position] = note.toInt()
                } else {
                    listaTeste.add(note.toInt())
                }
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
