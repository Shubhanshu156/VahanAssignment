package com.example.vahanassignment.network.models


data class UniversityResponseItem(
    val alpha_two_code: String,
    val country: String,
    val domains: List<String>,
    val name: String,
    val stateprovince: String,
    val web_pages: List<String>
)