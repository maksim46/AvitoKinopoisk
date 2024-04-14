package com.max_ro.avitokinopoisk.domain.common

import javax.inject.Inject

class LastFiveIntChecker @Inject constructor() {
    private val size = 8
    private val items = mutableListOf<Int>()

    fun addItem(item: Int): Boolean {
        items.add(item)
        if (items.size > size) {
            items.removeAt(0)
        }
        return checkIfAllItemsAreEqual()
    }

    private fun checkIfAllItemsAreEqual(): Boolean {
        if (items.size == size) {
            val first = items.first()
            return items.all { it == first }
        }
        return false
    }
}
