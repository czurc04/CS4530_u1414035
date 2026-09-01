package com.example.emailsplitter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.core.os.bundleOf

class InputFragment : Fragment(R.layout.fragment_input) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val emailInput = view.findViewById<android.widget.EditText>(R.id.emailInput)
        val button = view.findViewById<android.widget.Button>(R.id.button)

        button.setOnClickListener {
            val email = emailInput.text.toString()
            val pieces = email.split('@')

            if (pieces.size != 2 || pieces.any(String::isEmpty)) {
                android.widget.Toast.makeText(
                    requireContext(),
                    "Invalid email!",
                    android.widget.Toast.LENGTH_SHORT
                ).show()
            } else {
                val result = bundleOf(
                    "username" to pieces[0],
                    "domain" to pieces[1]
                )

                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, ResultFragment())
                    .addToBackStack(null)
                    .commit()

                parentFragmentManager.setFragmentResult("emailResult", result)
            }
        }
    }
}