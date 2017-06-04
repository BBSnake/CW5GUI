package com.snayk.cw5.view

import com.snayk.cw5.logic.Node
import javafx.scene.control.TreeItem
import javafx.scene.layout.VBox
import tornadofx.*

class ResultView(val tree: Node) : View("Decision Tree") {
    override val root = VBox()

    init {
        with(root) {
            treeview<Node> {
                root = TreeItem(tree)
                cellFormat { text = it.name }
                populate { it.value.children }
            }
        }
    }
}
