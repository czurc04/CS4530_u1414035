package com.example.assignment1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.TextView

/**
 * Second fragment that displays the button text pressed
 * from the first fragment, and lets the user return to the first screen
 */
class SecondFragment : Fragment(R.layout.fragment_second) {

    /**
     * Runs the second fragment layout when the second fragment is ready.
     * Pulls selected text from the first fragment. Sets up the back button
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectedText = arguments?.getString(FirstFragment.ARG_SELECTED_TEXT)
        view.findViewById<TextView>(R.id.selectedTextView).text = selectedText

        view.findViewById<Button>(R.id.backButton).setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}