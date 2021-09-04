package com.munny.nearplacecategory._base

interface ApiAuth {
    fun getApiUrl(): String
    fun getHeaders(): Map<String, Any>
}