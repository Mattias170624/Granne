package com.example.granne.model

import com.example.granne.R

data class PersonFindMatch(
    val name: Any? = null,
    val aboutMe: Any? = null,
    val interest: MutableCollection<Any>? = null,
    var uid: Any? = null,
    val buttonAdd: Any? = null,
    val image: Int? = R.drawable.userim,
) {
}