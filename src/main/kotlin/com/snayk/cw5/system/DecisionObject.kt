package com.snayk.cw5.system

class DecisionObject(val index: Int, val descriptors: List<Descriptor>, val decision: String) {
    override fun toString(): String {
        var text = ""
        for(desc in descriptors) {
            text += "$desc "
        }
        text += decision
        return text
    }
}

fun List<DecisionObject>.entropy(): Double {
    val entropy = this
            .groupBy { it.decision }
            .map { it.value.size.toDouble() / this.size.toDouble() }
            .sumByDouble { -(it * (Math.log(it) / Math.log(2.0))) }
    return entropy
}

fun List<DecisionObject>.gain(entropyS: Double, index: Int): Double {
    val filteredObjects = this.groupBy { it.descriptors[index].value }
    var summed = 0.0
    filteredObjects.forEach { summed += (it.value.size.toDouble() / this.size.toDouble() * it.value.entropy()) }
    return entropyS - summed
}

