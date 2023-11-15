package com.example.group6

data class Rating(
    val projID: String ?= null,
    val stars: Int,
    val userID: String ?= null
)
