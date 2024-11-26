package com.example.aplicativodemonstracao

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import com.example.aplicativodemonstracao.database.Anotacao
import com.example.aplicativodemonstracao.database.ProvedorBD
import com.example.aplicativodemonstracao.ui.MainActivity
import com.example.aplicativodemonstracao.ui.ModalNote
import kotlinx.coroutines.launch

class CustomAdapter(
    private val context: Context,
    private val dataSource: ArrayList<Anotacao>
) : BaseAdapter() {
    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int = dataSource.size

    override fun getItem(position: Int): Any = dataSource[position]

    override fun getItemId(position: Int): Long = dataSource[position].id.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: inflater.inflate(R.layout.item_list, parent, false)

        val itemText: TextView = view.findViewById(R.id.textView)
        val deleteButton: ImageButton = view.findViewById(R.id.deleteButton)
        val editButton: ImageButton = view.findViewById(R.id.editButton)

        val anotacao = dataSource[position]
        itemText.text = anotacao.titulo

        deleteButton.setOnClickListener {
            val database = ProvedorBD.obterInstancia(context)
            val anotacaoDAO = database.anotacaoDAO()

            (context as MainActivity).lifecycleScope.launch {
                anotacaoDAO.deletar(anotacao)
                dataSource.removeAt(position)
                notifyDataSetChanged()
            }
        }

        editButton.setOnClickListener {
            val modalNote = ModalNote()
            val args = Bundle()
            args.putInt("position", position)
            args.putString("note", anotacao.titulo)
            modalNote.arguments = args
            modalNote.listener = object : ModalNote.NewNoteDialogListener {
                override fun onDialogPositiveClick(note: String) {
                    (context as MainActivity).lifecycleScope.launch {
                        anotacao.titulo = note
                        val database = ProvedorBD.obterInstancia(context)
                        val anotacaoDAO = database.anotacaoDAO()
                        anotacaoDAO.atualizar(anotacao)
                        dataSource[position] = anotacao
                        notifyDataSetChanged()
                    }
                }
            }
            modalNote.show((context as MainActivity).supportFragmentManager, "modalNote")
        }

        return view
    }

    fun clear() {
        dataSource.clear()
        notifyDataSetChanged()
    }

    fun addAll(items: List<Anotacao>) {
        dataSource.addAll(items)
        notifyDataSetChanged()
    }

}

