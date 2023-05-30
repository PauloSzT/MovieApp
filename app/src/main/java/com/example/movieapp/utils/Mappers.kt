package com.example.movieapp.utils

interface ToDataBaseMapper<T : Any> {
    fun mapToDataBaseModel(): T
}

interface ToUiMapper<T : Any> {
    fun mapToUiModel(ids: List<String>): T
}

interface UiModelIntegrationMapper<T : Any> {
    fun mapToUiModel(): T
}
