package com.example.movieapp.utils

interface ToDataBaseMapper<T:Any> {
    fun mapToDataBaseModel(): T
}
