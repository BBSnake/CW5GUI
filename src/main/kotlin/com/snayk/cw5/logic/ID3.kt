package com.snayk.cw5.logic

import com.snayk.cw5.system.Attribute
import com.snayk.cw5.system.DecisionObject
import com.snayk.cw5.system.entropy
import com.snayk.cw5.system.gain

fun id3(decisionObjects: List<DecisionObject>, attributes: MutableList<Attribute>): List<Node>? {
    val entropyS = decisionObjects.entropy()
    // entropy is 0, all objects have the same class, return leaf with decision
    if(entropyS == 0.0)
        return listOf(Node(decisionObjects[0].decision))
    // all the attributes have been used but the objects have different classes
    // find the most common decision and return it as a leaf
    if(attributes.isEmpty() && decisionObjects.entropy() != 0.0) {
        val groupedObjects = decisionObjects.groupBy { it.decision }
        val mostCommonDecision = groupedObjects.maxBy { it.value.size }!!.key
        return listOf(Node(mostCommonDecision))
    }
    val nodes: MutableList<Node> = mutableListOf()
    val gainPairs: MutableList<Pair<Attribute, Double>> = mutableListOf()
    for(attribute in attributes) {
        val gain = decisionObjects.gain(entropyS, attribute.index)
        gainPairs.add(Pair(attribute, gain))
    }
    val biggestGain = gainPairs.maxBy { it.second }
    attributes.remove(biggestGain!!.first)
    val splitObjectsByValues = decisionObjects.groupBy { it.descriptors[biggestGain.first.index].value }
    splitObjectsByValues.forEach {
        nodes.add(Node("${biggestGain.first.name}: ${it.key}",
                id3(it.value, attributes))) }
    return nodes
}

