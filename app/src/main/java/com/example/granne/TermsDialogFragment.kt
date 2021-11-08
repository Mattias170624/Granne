package com.example.granne

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class TermsDialogFragment :DialogFragment() {

    val db = Firebase.firestore
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       var rootView: View = inflater.inflate(R.layout.fragment_customterms_dialog,container,false)

        var cancelButton = rootView.findViewById<Button>(R.id.cancelButton)

        cancelButton.setOnClickListener(object :View.OnClickListener {
            override fun onClick(v: View?) {
              dismiss()
            }
        })

        var AgreeButton = rootView.findViewById<Button>(R.id.AgreeButton)
        AgreeButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
              AgreeButton.setOnClickListener{
                  val intent = Intent(context, CreateAccountActivity::class.java)
                startActivity(intent as Intent?)
              }
                if(AgreeButton.isPressed) {
                    Toast.makeText(
                        context,
                        "You have to click on the Terms to continue",
                        Toast.LENGTH_SHORT
                    ).show()
                }else{
                    startActivity(Intent())
                }
            }
        })


        return rootView
    }

}

private fun RadioGroup.checkBoxId(): Int {
return checkBoxId()
}

private fun Any.setOnClickListener(function: () -> Unit) {

}
