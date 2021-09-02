package com.munny.nearplacecategory.base

interface ApiAuth {
    fun getApiUrl(): String
    fun getHeaders(): Map<String, Any>
}