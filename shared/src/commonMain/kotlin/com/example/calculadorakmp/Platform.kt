package com.example.calculadorakmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform