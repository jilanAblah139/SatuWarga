package com.md.satuwargaapp.ui.papanpengumuman

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.md.satuwargaapp.R


class DialogPengumumanFragment : DialogFragment() {

    interface DialogPengumumanListener {
        fun onLanjutTambahPostingan()
    }

    var listener: DialogPengumumanListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_dialog_pengumuman, null)

        val builder = AlertDialog.Builder(requireContext())
            .setView(view)
            .setCancelable(true)

        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        view.findViewById<Button>(R.id.btnCancel).setOnClickListener {
            dialog.dismiss()
        }

        view.findViewById<Button>(R.id.btnContinue).setOnClickListener {
            listener?.onLanjutTambahPostingan()
            dialog.dismiss()
        }

        return dialog
    }
}

