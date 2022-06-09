package com.example.granne

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.example.granne.databinding.FragmentInterestDialogBinding
import com.example.granne.repo.RemoteStorage
import kotlinx.coroutines.launch

class InterestDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentInterestDialogBinding

    private val interest = mutableListOf<CheckboxWrapper>()

    private val remoteStorage = RemoteStorage()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentInterestDialogBinding.inflate(inflater)


        interest.add(CheckboxWrapper(binding.checkBox1, "Wildlife", 1))
        interest.add(CheckboxWrapper(binding.checkBox2, "Travel", 2))
        interest.add(CheckboxWrapper(binding.checkBox3, "Food", 3))
        interest.add(CheckboxWrapper(binding.checkBox4, "Socialising", 4))
        interest.add(CheckboxWrapper(binding.checkBox5, "Books", 5))
        interest.add(CheckboxWrapper(binding.checkBox6, "Games", 6))
        interest.add(CheckboxWrapper(binding.checkBox7, "Netflix", 7))

        binding.saveChangesButton.setOnClickListener {
            val count = countInterests()
            if (count == 0) {
                showToast("Please select at least 1 interest!")
            } else if (count > 6) {
                showToast("Max 6 interests allowed!")
            } else {
                lifecycleScope.launch {
                    remoteStorage.updateInterests(
                        generateInterestList(),
                        binding.aboutmeEditText.text.toString()
                    )
                }
                showToast("Updated interest list")
                dismiss()
            }
        }
        return binding.root
    }

    private fun countInterests(): Int {
        var count = 0
        interest.forEach {
            if (it.checkbox.isChecked)
                count++
        }
        return count
    }

    private fun generateInterestList(): HashMap<String, String> {
        val userInterests = hashMapOf<String, String>()
        interest.forEach {
            if (it.checkbox.isChecked)
                userInterests[it.getIdentifier()] = it.interest
        }
        return userInterests
    }

    private fun showToast(toastMessage: String) {
        val toast = Toast.makeText(activity, toastMessage, Toast.LENGTH_SHORT)
        toast.show()
    }

    class CheckboxWrapper(val checkbox: CheckBox, val interest: String, val id: Int) {
        fun getIdentifier(): String {
            return "interest$id"
        }
    }
}


