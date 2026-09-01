package com.example.emailsplitter

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class ResultFragment : Fragment(R.layout.fragment_result) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parentFragmentManager.setFragmentResultListener("emailResult", this) { _, bundle ->
            val username = bundle.getString("username")
            val domain = bundle.getString("domain")

            view.findViewById<TextView>(R.id.userView).text = username
            view.findViewById<TextView>(R.id.domainView).text = domain

            Toast.makeText(requireContext(), "Data received!", Toast.LENGTH_SHORT).show()
        }
    }
}