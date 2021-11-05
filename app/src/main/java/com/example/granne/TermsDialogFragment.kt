package com.example.granne


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class TermsDialogFragment :DialogFragment (), View.OnClickListener {

    val db = Firebase.firestore
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        var rootView: View = inflater.inflate(R.layout.dialog_terms_fragment, container, false)
        var cancelButton = rootView.findViewById<Button>(R.id.cancelButton)
        var AgreeButton = rootView.findViewById<Button>(R.id.AgreeButton)
        var checkBoxOne = rootView.findViewById<CheckBox>(R.id.oneCheckBox)
        var checkBoxTwo = rootView.findViewById<CheckBox>(R.id.twoCheckBox)
        var checkBoxThree = rootView.findViewById<CheckBox>(R.id.threeCheckBox)
        var checkBoxFour = rootView.findViewById<CheckBox>(R.id.fourCheckBox)
        var radioGroup = rootView.findViewById<RadioGroup>(R.id.TermsRadioGroup)


        cancelButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                dismiss()

            }
        })

        AgreeButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                AgreeButton.setOnClickListener{
                    val intent = Intent(context,MainActivity::class.java)
                    startActivity(intent as Intent?)
                }

                if(AgreeButton.isPressed) {
                    Toast.makeText(
                        context,
                        "You have to click on the Terms to continue",
                        Toast.LENGTH_SHORT
                    ).show()

                    if (checkBoxOne.isChecked) {
                    }
                    if (checkBoxTwo.isChecked) {
                    }
                    if (checkBoxThree.isChecked) {
                    }
                    if (checkBoxFour.isChecked) {
                    }
                }else {

                    val intent = Intent(this@TermsDialogFragment, MainActivity::class.java)
                }
            }

        })
        return rootView
    }

    private fun Intent(termsDialogFragment: TermsDialogFragment, java: Class<MainActivity>): Any {

       val intent  = Intent(this,MainActivity::class.java)
        return (intent)
    }
    override fun onClick(v: View?) {

    }

    private fun CheckBoxes(): CheckBox{
        var checkBoxOne : CheckBox
        var checkBoxTwo : CheckBox
        var checkBoxThree : CheckBox
        var checkBoxFour : CheckBox

        return CheckBoxes()
    }
}

private operator fun Boolean.invoke(value: Any) {

}


