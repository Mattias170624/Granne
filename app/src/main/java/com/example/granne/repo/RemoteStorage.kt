package com.example.granne.repo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RemoteStorage: Interests {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseFirestore = Firebase.firestore

    override suspend fun updateInterests(interests: HashMap<String, String>, aboutMe: String) {
        val currentUser = firebaseAuth.currentUser
        val docRef = firebaseFirestore.collection("userData").document(currentUser!!.uid)

        docRef.collection("interests").document("interestlist")
            .set(interests)
            .addOnSuccessListener {
                if (aboutMe.isNotEmpty()) {
                    docRef.update("aboutme", aboutMe)
                        .addOnSuccessListener {

                        }
                }

            }
    }
}