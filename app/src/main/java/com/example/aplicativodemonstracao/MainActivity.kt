package com.example.aplicativodemonstracao

import ModalNote
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aplicativodemonstracao.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var addButton: ImageButton ;
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("A", "onCreate: Iniciado")
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        addButton = binding.addButton
        setContentView(binding.root)
        enableEdgeToEdge()
        val listaTeste = ArrayList<Int>();
        listaTeste.add(1)
        listaTeste.add(2)
        listaTeste.add(3)

        val listView: android.widget.ListView = findViewById(R.id.listView)
        val adapter = CustomAdapter(this, listaTeste)
        listView.adapter = adapter
        this.addButton.setOnClickListener {
            showNoteModal(listaTeste)
        }
    }

    private fun showNoteModal(list: ArrayList<Int>) {
        val modalNote = ModalNote()
        modalNote.show(supportFragmentManager, "modalNote")
        modalNote.listener = object : ModalNote.NewNoteDialogListener {
            override fun onDialogPositiveClick(note: String) {
                list.add(note.toInt())
                binding.listView.adapter = CustomAdapter(this@MainActivity, list)
            }
        }
    }
}