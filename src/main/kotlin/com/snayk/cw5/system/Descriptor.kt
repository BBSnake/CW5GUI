package com.snayk.cw5.system

data class Descriptor(val index: Int, val name: String, val value: String) {
    override fun toString(): String {
        return value
    }
}

