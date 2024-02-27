package com.example.macroeconomicresearch

enum class LoginType(val key : String) {
    RESEARCHER("researcher"),
    GOVT_OFFICIAL("govt_official")
}


fun loginTypeFromStorageKey(storageKey: String?) : LoginType?
{
    return LoginType.values().firstOrNull { it.key == storageKey }
}