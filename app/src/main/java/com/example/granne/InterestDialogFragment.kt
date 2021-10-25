package com.example.granne

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.DialogFragment

class InterestDialogFragment : DialogFragment() {

    private lateinit var checkBox1: CheckBox
    private lateinit var checkBox2: CheckBox
    private lateinit var checkBox3: CheckBox
    private lateinit var checkBox4: CheckBox
    private lateinit var checkBox5: CheckBox
    private lateinit var checkBox6: CheckBox



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


        return rootView

    }

}


