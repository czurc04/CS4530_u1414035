package com.example.assignment1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button

/**
 * Fragment that displays five buttons with text, as well as setting up the
 * second fragment.
 */
class FirstFragment : Fragment(R.layout.fragment_first) {

    /**
     * Builds list of button ids and attaches click listener to each button.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonIds = listOf(R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5)
        for (id in buttonIds) {
            val button = view.findViewById<Button>(id)
            button.setOnClickListener { navigateToSecondFragment(button.text.toString()) }
        }
    }

    /**
     * Switches screens by creating a new second fragment, applying the selected
     * button text.
     */
    private fun navigateToSecondFragment(selectedText: String) {
        val secondFragment = SecondFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_SELECTED_TEXT, selectedText)
            }
        }
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, secondFragment)
            .addToBackStack(null)
            .commit()
    }

    /**
     * Allows first and second fragment to reference the same button text.
     */
    companion object {
        const val ARG_SELECTED_TEXT = "selected_text"
    }
}
