package com.example.granne

import android.graphics.Color
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.DialogFragment

class InterestDialogFragment : DialogFragment() {

    private lateinit var checkBox1: CheckBox
    private lateinit var checkBox2: CheckBox
    private lateinit var checkBox3: CheckBox
    private lateinit var checkBox4: CheckBox
    private lateinit var checkBox5: CheckBox
    private lateinit var checkBox6: CheckBox
    private lateinit var checkBox7: CheckBox
    lateinit var saveChangesButton: Button


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_interest_dialog, container, false)

        checkBox1 = rootView.findViewById(R.id.checkBox1)
        checkBox2 = rootView.findViewById(R.id.checkBox2)
        checkBox3 = rootView.findViewById(R.id.checkBox3)
        checkBox4 = rootView.findViewById(R.id.checkBox4)
        checkBox5 = rootView.findViewById(R.id.checkBox5)
        checkBox6 = rootView.findViewById(R.id.checkBox6)
        checkBox7 = rootView.findViewById(R.id.checkBox7)
        saveChangesButton = rootView.findViewById(R.id.saveChangesButton)

        saveChangesButton.setOnClickListener {
            var count = 0

            if (checkBox1.isChecked) {
                count++
                Log.d("!", "1")
            }
            if (checkBox2.isChecked) {
                count++
                Log.d("!", "2")
            }
            if (checkBox3.isChecked) {
                count++
                Log.d("!", "3")
            }
            if (checkBox4.isChecked) {
                count++
                Log.d("!", "4")
            }
            if (checkBox5.isChecked) {
                count++
                Log.d("!", "5")
            }
            if (checkBox6.isChecked) {
                count++
                Log.d("!", "6")
            }
            if (checkBox7.isChecked) {
                count++
                Log.d("!", "7")
            }

            if (count > 6) {
                showToast("You can only a maximum of 6 interests!")
            } else {
                Log.d("!", "Good. count <= 6")
            }


        }

        return rootView
    }

    private fun showToast(toastMessage: String) {
        val toast = Toast.makeText(activity, toastMessage, Toast.LENGTH_SHORT)
        toast.show()
    }
}


