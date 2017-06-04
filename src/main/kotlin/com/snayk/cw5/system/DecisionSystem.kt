package com.snayk.cw5.system

class DecisionSystem(fileText: String) {
    val decisionObjects: MutableList<DecisionObject> = mutableListOf()
    val uniqueDecisions: List<String> by lazy {
        decisionObjects.map {it.decision }.toSet().toList()
    }
    val attributes: List<Attribute> by lazy {
        (1 until columnNames.size - 1).map { Attribute(it, columnNames[it]) }
    }
    var columnNames: List<String> = listOf()
    val entropy: Double by lazy {
        decisionObjects.entropy()
    }

    init {
        val rows: List<String> = fileText.trim().split("\n")
        columnNames = rows[0].trim().split(" ")
        for(i in 1 until rows.size) {
            val row: List<String> = rows[i].trim().split(" ")
            val descriptorsList: MutableList<Descriptor> = mutableListOf()
            (0 until row.size - 1).mapTo(descriptorsList) { Descriptor(it, columnNames[it], row[it]) }
            decisionObjects.add(DecisionObject(i - 1, descriptorsList, row[row.size - 1]))
        }
    }

    override fun toString(): String {
        var text = ""
        for(columnName in columnNames) {
            text+= "$columnName "
        }
        text += "\n"
        for(dOb in decisionObjects) {
            text += "$dOb\n"
        }
        return text
    }
}
