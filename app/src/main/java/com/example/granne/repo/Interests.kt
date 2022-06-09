package com.example.granne.repo

interface Interests {
    suspend fun updateInterests(interests: HashMap<String, String>, aboutMe: String)
}