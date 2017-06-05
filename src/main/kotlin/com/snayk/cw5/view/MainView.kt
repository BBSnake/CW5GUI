package com.snayk.cw5.view

import com.snayk.cw5.logic.Node
import com.snayk.cw5.logic.id3
import com.snayk.cw5.system.DecisionSystem
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.stage.FileChooser
import tornadofx.*
import java.io.File

class MainView : View("ID3") {
    var systemFileTextField: TextField by singleAssign()
    var chosenFile: File? = null
    var systemText: TextArea by singleAssign()
    var decisionSystem: DecisionSystem? = null
    var generateButton: Button by singleAssign()

    override val root = vbox {
        padding = insets(10)
        spacing = 10.0

        label("File:")
        hbox {
            spacing = 5.0
            systemFileTextField = textfield {
                style {
                    prefWidth = 400.px
                }
                isEditable = false
            }
            button("Select") {
                action {
                    chosenFile = chooseFile("Choose file with system",
                            arrayOf(FileChooser.ExtensionFilter("Text file", "*.txt"),
                                    FileChooser.ExtensionFilter("All types", "*.*"))).firstOrNull()

                    if (chosenFile?.absolutePath != null) {
                        systemFileTextField.text = chosenFile?.absolutePath
                    }
                }
            }
        }
        button("Read file") {
            action {
                if (chosenFile != null) {
                    try {
                        decisionSystem = DecisionSystem(chosenFile!!.readText())
                        systemText.text = decisionSystem.toString()
                        generateButton.isVisible = true
                    } catch (e: Exception) {
                        alert(Alert.AlertType.ERROR, "Error", "Provided file is in wrong format or contains and error!")
                    }
                } else {
                    alert(Alert.AlertType.ERROR, "Error", "No file chosen!")
                }
            }
        }
        label("Database:")
        systemText = textarea {
            isEditable = false

        }
        generateButton = button("Generate") {
            isVisible = false
            action {
                val tree = Node(decisionSystem!!.columnNames.last(),
                        id3(decisionSystem!!.decisionObjects,
                                decisionSystem!!.attributes.toMutableList()))
                ResultView(tree).openWindow()
            }
        }
    }
}