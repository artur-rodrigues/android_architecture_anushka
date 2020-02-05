package com.example.recyclerviewdemo1.model

data class Fruit(val name: String, val supplier: String) {
    override fun toString(): String {
        return "Fruta: $name. Fornecedor: $supplier"
    }
}