package com.rodrigo_quilodran_eval1.model

class ItemMesa(val itemMenu: ItemMenu, var cantidad: Int) {
    fun calcularSubtotal(): Int {
        val precioInt = itemMenu.precio.replace(".", "").replace("$", "").toInt()
        return cantidad * precioInt
    }
}
