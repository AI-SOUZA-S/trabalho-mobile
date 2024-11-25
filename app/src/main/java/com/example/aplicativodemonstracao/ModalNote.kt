import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.aplicativodemonstracao.R

class ModalNote : DialogFragment() {

    interface NewNoteDialogListener {
        fun onDialogPositiveClick(note: String)
    }

    var listener: NewNoteDialogListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.modal_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val noteEditText: EditText = view.findViewById(R.id.noteEditText)
        val saveButton: Button = view.findViewById(R.id.saveButton)

        saveButton.setOnClickListener {
            val note = noteEditText.text.toString()
            listener?.onDialogPositiveClick(note)
            dismiss()
        }
    }
}